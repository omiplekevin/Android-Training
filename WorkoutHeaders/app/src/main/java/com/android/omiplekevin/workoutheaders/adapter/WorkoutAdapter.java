package com.android.omiplekevin.workoutheaders.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.omiplekevin.workoutheaders.BuildConfig;
import com.android.omiplekevin.workoutheaders.R;
import com.android.omiplekevin.workoutheaders.helpers.Constants;
import com.android.omiplekevin.workoutheaders.helpers.Utilities;
import com.android.omiplekevin.workoutheaders.model.ExercisesRelationModel;
import com.android.omiplekevin.workoutheaders.model.timeline.WorkoutAll;
import com.android.omiplekevin.workoutheaders.network.RequestManager;
import com.android.omiplekevin.workoutheaders.network.RequestService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by OMIPLEKEVIN on February 28, 2018.
 * WorkoutHeaders
 * com.android.omiplekevin.workoutheaders.adapter
 */

@RequiredArgsConstructor(staticName = "initialize")
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutViewHolder> implements Callback<ResponseBody> {

    private static final String TAG = "WorkoutAdapter";

    @Getter
    private final Context context;
    @Getter
    private final List<WorkoutAll.Data> workoutDataList;

    @Getter
    private Map<String, String> playbookTimelineWeeks = new HashMap<>();


    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View workoutItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_workout_data_item, parent, false);
        return new WorkoutViewHolder(workoutItemLayout);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        WorkoutAll.Data data = workoutDataList.get(position);
        holder.workoutDate.setText(data.getFormatted_day_label());
        holder.rawData.setText(data.getFormatted_date_label());
        Picasso.with(context).load(Uri.parse(data.getWorkoutUrl())).into(holder.workoutLogo);
        //request for playbook timeline for the week,
        // add first the week number to the hashmap
        // to avoid repeating requests on next binding
        // of RecyclerView
        if (!isRepeatingKeyOnCollection(getPlaybookTimelineWeeks(), data.week)) {
            getPlaybookTimelineWeeks().put(data.week, "");
            getPlaybookTimeline(data.week, position);
        } else {
            Log.w(TAG, "onBindViewHolder: skipping request, repeating key...");
        }
    }

    @Override
    public int getItemCount() {
        return getWorkoutDataList().size();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            try {
                final String resString = response.body().string();
                String tag = Utilities.getHeaderTagFromCall(call, RequestService.HEADER_FIELD_TAG);
                switch (tag) {
                    case Constants.REQUEST_GET_PLAYBOOK_TIMELINE:
                        setPlaybookTimeline(resString);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        call.clone().enqueue(this);
    }

    private void getPlaybookTimeline(String week, int position) {
        RequestService requestService = RequestManager.getInstance(this.context).getRetrofit(BuildConfig.host).create(RequestService.class);
        Call<ResponseBody> playbookTimelineRequest = requestService.getPlaybookTimeline(week, Constants.REQUEST_GET_PLAYBOOK_TIMELINE, position);
        Log.d(TAG, "request - getPlaybookTimeline: " + playbookTimelineRequest.request().url());
        playbookTimelineRequest.enqueue(this);
    }

    private void setPlaybookTimeline(String response) {
        JSONObject playbookWeekTimeline;
        if (response != null && !response.isEmpty()) {
            try {
                playbookWeekTimeline = new JSONObject(response);
                String playbookWeek = playbookWeekTimeline.getJSONObject("data").getString("week");
                getPlaybookTimelineWeeks().put(playbookWeek, response);
                synchronized (getWorkoutDataList()) {
                    for (int i = 0; i < getWorkoutDataList().size(); i++) {
                        WorkoutAll.Data data = getWorkoutDataList().get(i);
                        if (!data.week.equals(playbookWeek)) {
                            continue;
                        } else {
                            setWorkoutTimelineData(i, playbookWeekTimeline);
                            getProgram(i, data.workout_name);
                            getExerciseRelations(i, data.week, data.weekday);
                            Log.d(TAG, "setPlaybookTimeline: " + playbookWeekTimeline);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.w(TAG, "setPlaybookTimeline: playbookTimeline is null!");
        }
    }

    private void getProgram(final int position, String workoutName) {
        //call: http://matrix.f45.info/v1/programs/search/{workoutName}
        RequestService requestService = RequestManager.getInstance(this.context).getRetrofit(BuildConfig.host).create(RequestService.class);
        Call<ResponseBody> programsRequest = requestService.getPrograms(workoutName, Constants.REQUEST_GET_PROGRAMS);
        Log.d(TAG, "request - getPrograms: " + programsRequest.request().url());
        programsRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                synchronized (getWorkoutDataList()) {
                    if (response.isSuccessful()) {
                        try {
                            final String resString = response.body().string();
                            /*
                             * [0] - workout thumb
                             * [1] - workout video url
                             * [2] - movement image filename
                             */
                            final String[] workoutProgramInfo = Utilities.parseAndGetProgramInfo(resString);

                            //workout information
                            getWorkoutDataList().get(position).setThumb_workoutInfo(workoutProgramInfo[0]);
                            getWorkoutDataList().get(position).setUrl_workoutInfo(workoutProgramInfo[1]);

                            //movement animation
                             getWorkoutDataList().get(position).setLarge_workout_movement_gif(BuildConfig.f45playbook_s3 + workoutProgramInfo[2]);
                             getWorkoutDataList().get(position).setSmall_workout_movement_gif(BuildConfig.f45tv_s3 + workoutProgramInfo[2]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    private void getExerciseRelations(final int position, String week, String weekday) {
        //call: http://matrix.f45.info/v1/exercise-relations/get_gym_exercise/{franchiseeId}/{week}/{weekday}
        RequestService requestService = RequestManager.getInstance(this.context).getRetrofit(BuildConfig.host).create(RequestService.class);
        Call<ResponseBody> exercisesRelationRequest = null;
        exercisesRelationRequest = requestService.getExerciseRelationModel(
                Constants.TIMELINE_MODEL.json.franchisee.id,
                week,
                weekday,
                Constants.REQUEST_GET_EXERCISE_RELATIONS);
        exercisesRelationRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        final String resString = response.body().string();
                        synchronized (getWorkoutDataList()) {
                            ExercisesRelationModel exercisesRelationModel = new Gson().fromJson(resString, ExercisesRelationModel.class);
                            if (exercisesRelationModel.success) {
                                getWorkoutDataList().get(position).setExerciseRelations(exercisesRelationModel);
                                getWorkoutDataList().get(position).getExerciseRelations().setExerciseAlternativeMapping();
                            }
                            Log.d(TAG, "onResponse: " + getWorkoutDataList().get(position).toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    private boolean isRepeatingKeyOnCollection(Map<String, String> collection, String key) {
        for (Map.Entry<String, String> entry : collection.entrySet()) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private void setWorkoutTimelineData(int position, JSONObject playbookTimeline) {
        playbookTimeline = Utilities.getWeekdayConfigInformation(this.context, playbookTimeline, Integer.parseInt(getWorkoutDataList().get(position).weekday));
        if (playbookTimeline == null) {
            Log.e(TAG, "RETURN: playbookTimeline JSON return is null!");
        } else {
            //trim down to save memory space
            playbookTimeline.remove("exercise_relations");
            playbookTimeline.remove("timelines");
            playbookTimeline.remove("exercises");
        }
        try {
            getWorkoutDataList().get(position).setUrl_sessionHighlight(playbookTimeline.getString("session_vid_url"));
            if (getWorkoutDataList().get(position).getUrl_sessionHighlight().equals("[]")) {
                getWorkoutDataList().get(position).setUrl_sessionHighlight("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject sessAssetObject;
        if (playbookTimeline.has("session_vid_assets")) {
            try {
                Object checkTypeSessionResponse = playbookTimeline.get("session_vid_assets");
                if (checkTypeSessionResponse instanceof JSONObject) {
                    sessAssetObject = new JSONObject(playbookTimeline.getString("session_vid_assets"));
                    final JSONArray sessAssetArray = sessAssetObject.getJSONArray("sizes");
                    for (int i = 0; i < sessAssetArray.length(); i++) {
                        JSONObject obj = sessAssetArray.getJSONObject(i);
                        if (obj.getString("width").equals("640")) {
//                                sessionHightlightThumb = obj.getString("link");
                            getWorkoutDataList().get(position).setThumb_sessionHighlight(obj.getString("link"));
                            break;
                        }
                    }
                } else if (checkTypeSessionResponse instanceof JSONArray) {
                    Log.w(TAG, "setPlaybookTimeline: instance of Array - NO DATA");
                } else {
                    Log.e(TAG, "setPlaybookTimeline: session_vid_assets is type of " + checkTypeSessionResponse.getClass());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "setPlaybookTimeline: lacks session_vid_assets JSON key");
        }

        try {
            final JSONArray dataTimelines = playbookTimeline.getJSONArray("timeline");
            for (int j = 0; j < dataTimelines.length(); j++) {
                JSONObject tmln = dataTimelines.getJSONObject(j);
                if (getWorkoutDataList().get(position).getWorkoutDescriptionList() == null) {
                    getWorkoutDataList().get(position)
                            .setWorkoutDescriptionList(new ArrayList<String>());
                }
                if (tmln.getString("key").equals("timer")) {
                    getWorkoutDataList().get(position)
                            .getWorkoutDescriptionList()
                            .add(tmln.getString("overwrite_playbook_description"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
