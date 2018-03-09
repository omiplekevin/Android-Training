package com.android.omiplekevin.workoutheaders.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.omiplekevin.workoutheaders.BuildConfig;
import com.android.omiplekevin.workoutheaders.R;
import com.android.omiplekevin.workoutheaders.adapter.WorkoutAdapter;
import com.android.omiplekevin.workoutheaders.helpers.Constants;
import com.android.omiplekevin.workoutheaders.helpers.Utilities;
import com.android.omiplekevin.workoutheaders.model.ExercisesModel;
import com.android.omiplekevin.workoutheaders.model.timeline.TimelineModel;
import com.android.omiplekevin.workoutheaders.model.timeline.WorkoutAll;
import com.android.omiplekevin.workoutheaders.network.RequestManager;
import com.android.omiplekevin.workoutheaders.network.RequestService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<ResponseBody> {

    /**
     * FIELDS
     */
    private static final String TAG = "MainActivity";

    private final String timelineVersion = "v12";
    private final String gymAccessCode = "j6pv";
    private final String[] weekdayIndex = {"0", "2", "3", "4", "5", "6", "7", "1"};

    /**
     * VIEWS
     */
    @BindView(R.id.workoutRecyclerView)
    RecyclerView workoutRecyclerView;

    /**
     * ADAPTERS
     */
    private WorkoutAdapter workoutAdapter;

    /**
     * LAYOUT MANAGER
     */
    private RecyclerView.LayoutManager workoutLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        //initialize layout manager
        workoutLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callAPIs();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseBody) {
        if (responseBody.isSuccessful()) {
            try {
                final String response = responseBody.body().string();
                String tag = Utilities.getHeaderTagFromCall(call, RequestService.HEADER_FIELD_TAG);
                switch (tag) {
                    case Constants.REQUEST_GET_TIMELINE:
                        Constants.TIMELINE_MODEL = new Gson().fromJson(response, TimelineModel.class);
                        //check if the timeline version is correct
                        Log.d(TAG, "onResponse: FORCED: [" + timelineVersion + "," + gymAccessCode + "] setting timeline...");
                        setTimeline(response);
                        getAllWorkouts();
                        break;
                    case Constants.REQUEST_GET_EXERCISES:
                        setExercises(response);
                        break;
                    case Constants.REQUEST_GET_ALL_WORKOUT:
                        setWorkouts(response);
                        break;
                    default:
                        Log.w(TAG, "onResponse: tag is " + tag);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.w(TAG, "onResponse[failure]: " + IOUtils.toString(responseBody.errorBody().byteStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.d(TAG, "onFailure: " + call.request().url());
        call.clone().enqueue(this);
    }

    private void callAPIs() {
        getTimeline();
        getExercises();
    }

    private void getTimeline() {
        Log.d(TAG, "getTimeline");
        RequestService requestService = RequestManager.getInstance(this).getRetrofit(BuildConfig.timelineHost).create(RequestService.class);
        Call<ResponseBody> timelineRequest = requestService.getTimeline(
                timelineVersion,
                gymAccessCode,
                Constants.REQUEST_GET_TIMELINE);
        timelineRequest.enqueue(this);
    }

    private void getExercises() {
        //call: http://matrix.f45.info/v1/exercises
        RequestService requestService = RequestManager.getInstance(this).getRetrofit(BuildConfig.host).create(RequestService.class);
        Call<ResponseBody> exercisesRequest = requestService.getExercises(Constants.REQUEST_GET_EXERCISES);
        exercisesRequest.enqueue(this);
    }

    private void getAllWorkouts() {
        //call: http://matrix.f45.info/v1/workouts
        RequestService requestService = RequestManager.getInstance(this).getRetrofit(BuildConfig.host).create(RequestService.class);
        Call<ResponseBody> allWorkoutRequest = requestService.getAllWorkout(Constants.REQUEST_GET_ALL_WORKOUT);
        ;
        allWorkoutRequest.enqueue(this);
    }

    private void setTimeline(String response) {
        Constants.TIMELINE_MODEL = new Gson().fromJson(response, TimelineModel.class);
        Constants.TIMELINE_VERSION = Constants.TIMELINE_MODEL.json.franchisee.timeline_version;
        Constants.ACCESS_CODE = Constants.TIMELINE_MODEL.json.franchisee.accessCode;
        Constants.STUDIO_NAME = Constants.TIMELINE_MODEL.json.franchisee.name;
        if (Constants.PROGRAM.equals("")) {
            if (Constants.TIMELINE_MODEL.json.workout_config.workoutName.equals("Playoffs")) {
                Constants.PROGRAM = Constants.PLAYOFFS;
                Constants.TIMELINE_PROGRAM = Constants.PLAYOFFS;
            } else if (Integer.parseInt(Constants.TIMELINE_MODEL.json.franchisee.week) >= 200) {
                Constants.PROGRAM = Constants.INTROWEEKS;
                Constants.TIMELINE_PROGRAM = Constants.INTROWEEKS;
            } else {
                Constants.PROGRAM = Constants.WORKOUTS;
                Constants.TIMELINE_PROGRAM = Constants.WORKOUTS;
            }
        }
    }

    private void setExercises(String response) {
        ExercisesModel exercisesModel = new Gson().fromJson(response, ExercisesModel.class);
        if (exercisesModel.success) {
            Constants.EXERCISES_LIST = exercisesModel;
            Log.d(TAG, "setPlaybookTimeline: getExerciseRelations");
        }
    }

    private void setWorkouts(String response) {
        Constants.WORKOUT_ALL = new Gson().fromJson(response, WorkoutAll.class);
        if (Constants.WORKOUT_ALL != null) {
            try {
                Constants.INTRODUCTORY_WEEKS = new ArrayList<>();
                Constants.WORKOUTS_WEEK = new ArrayList<>();
                for (WorkoutAll.Data data : Constants.WORKOUT_ALL.data) {
                    if (!data.weekday.equals("0")) {
                        if (data.week.equals("309")) {
                            Log.d(TAG, "Playoffs");
                        } else {
                            WorkoutAll.Data item = new WorkoutAll.Data();
                            item.week = data.week;
                            item.weekday = data.weekday;
                            item.setSystemWeekday(weekdayIndex[Integer.parseInt(data.weekday)]);
                            item.workout_name = data.workout_name;
                            item.setWorkoutUrl(Utilities.getLogoPath(data.workout_name));
                            item.setDayOfWeek(this.getResources().getStringArray(R.array.day_of_the_week)[Integer.parseInt(item.getSystemWeekday()) - 1].toUpperCase());
                            data.getCalendar().set(Calendar.DAY_OF_YEAR, (7 * (Integer.parseInt(item.week) - 1) + Integer.parseInt(item.weekday)));
                            //set text to be displayed
                            if (Constants.PROGRAM.equals(Constants.WORKOUTS)) {
                                item.setFormatted_day_label(item.getDayOfWeek().substring(0, 3));
                                item.setFormatted_date_label(new SimpleDateFormat("MMM d", Locale.getDefault()).format(data.getCalendar().getTime()));
                            } else {
                                if (item.week.equals("200")) {
                                    //if week 200
                                    item.setFormatted_day_label(getString(R.string.label_workout_opening_day));
                                    item.setFormatted_date_label(data.getDayOfWeek().substring(0, 3));
                                } else {
                                    //if week 20[n], i.e 201, 202, 203, 204, etc.
                                    item.setFormatted_day_label(getString(R.string.label_week_abbrevation, String.valueOf(Integer.parseInt(data.week) % 200)));
                                    item.setFormatted_date_label(data.getDayOfWeek().substring(0, 3));
                                }
                            }
                            //---------------------------------------------
                            //add to list of workout according to its weeks
                            if (Integer.parseInt(data.week) >= 200 && Integer.parseInt(data.week) <= (200 + Integer.parseInt(Constants.TIMELINE_MODEL.json.franchisee.intro_weeks_using))) { //intro weeks
                                Constants.INTRODUCTORY_WEEKS.add(item);
                            } else if (Integer.parseInt(data.week) <= 53) { //workout weeks
                                Constants.WORKOUTS_WEEK.add(item);
                            }
                        }
                    } else {
                        Log.e(TAG, "Weekday cannot be zero.");
                    }
                }
                sortList(Constants.INTRODUCTORY_WEEKS, Constants.INTROWEEKS);
                sortList(Constants.WORKOUTS_WEEK, Constants.WORKOUTS);
                setupWorkoutHeaderListView();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            Log.w(TAG, "update: Constants.WORKOUT_ALL is null!");
        }
    }

    private void setupWorkoutHeaderListView() {
        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        switch (Constants.PROGRAM) {
            case Constants.INTROWEEKS:
                workoutAdapter = WorkoutAdapter.initialize(this, Constants.INTRODUCTORY_WEEKS);
                break;
            case Constants.WORKOUTS:
                workoutAdapter = WorkoutAdapter.initialize(this, Constants.WORKOUTS_WEEK);
                break;
        }

        if (workoutAdapter != null) {
            workoutRecyclerView.setAdapter(workoutAdapter);
        } else {
            Log.e(TAG, "setupWorkoutHeaderListView: workoutAdapter is null!");
        }
    }

    private void sortList(List<WorkoutAll.Data> list, final String type) {
        try {
            Collections.sort(list, new Comparator<WorkoutAll.Data>() {
                @Override
                public int compare(WorkoutAll.Data lhs, WorkoutAll.Data rhs) {
                    int wk = Integer.parseInt(lhs.week) - Integer.parseInt(rhs.week);
                    if (wk == 0) {
                        if (type.equals(Constants.WORKOUTS)) {
                            wk = Integer.parseInt(lhs.weekday) - Integer.parseInt(rhs.weekday);
                        } else if (type.equals(Constants.INTROWEEKS)) {
                            wk = Integer.parseInt(lhs.weekday) - Integer.parseInt(rhs.weekday);
                        }
                    }
                    return wk;
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
