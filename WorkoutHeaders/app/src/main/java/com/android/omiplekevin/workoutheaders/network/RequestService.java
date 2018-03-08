package com.android.omiplekevin.workoutheaders.network;


import com.android.omiplekevin.workoutheaders.BuildConfig;
import com.android.omiplekevin.workoutheaders.helpers.Constants;

import java.util.Map;

import lombok.Getter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by OMIPLEKEVIN on January 03, 2018.
 * F45PowerAndroid
 * com.f45tv.f45powerandroid.helper.services
 */

public interface RequestService {

    @Getter
    public static final String HEADER_FIELD_TAG = "tag";

    @Getter
    public static final String HEADER_FIELD_LIST_POSITION = "position";

    @GET("{timelineVersion}/timeline/{accessCode}")
    Call<ResponseBody> getTimeline(@Path("timelineVersion") String timelineVersion, @Path("accessCode") String accessCode, @Header(HEADER_FIELD_TAG) String requestTag);

    @GET(BuildConfig.timelines + "{accessCode}")
    Call<ResponseBody> getTimelineSummary(@Path("accessCode") String accessCode, @Header(HEADER_FIELD_TAG) String requestTag);

    @GET("workouts")
    Call<ResponseBody> getAllWorkout(@Header(HEADER_FIELD_TAG) String requestTag);

    @GET(Constants.API_WEEK_DAY_TIMELINE)
    Call<ResponseBody> getWeekDayConfig(@Header(HEADER_FIELD_TAG) String requestTag);

    @GET("{franchiseeId}")
    Call<ResponseBody> getCompliance(@Path("franchiseeId") String franchiseeId, @Header(HEADER_FIELD_TAG) String requestTag);

    @GET(BuildConfig.weeklyTimeline + "{week}")
    Call<ResponseBody> getPlaybookTimeline(@Path("week") String week, @Header(HEADER_FIELD_TAG) String tag, @Header(HEADER_FIELD_LIST_POSITION) int position);

    @GET(BuildConfig.programs + "/{workoutName}")
    Call<ResponseBody> getPrograms(@Path("workoutName") String workoutName, @Header(HEADER_FIELD_TAG) String tag);

    @GET("exercises")
    Call<ResponseBody> getExercises(@Header(HEADER_FIELD_TAG) String tag);

    @GET("exercise-relations/get_gym_exercise/{franchiseeId}/{week}/{weekday}")
    Call<ResponseBody> getExerciseRelationModel(@Path("franchiseeId") String franchiseeId, @Path("week") String week, @Path("weekday") String weekday, @Header(HEADER_FIELD_TAG) String tag);

    @Headers("Content-Type: application/json")
    @POST("franchisees/{gymid}/update")
    Call<ResponseBody> setWorkoutWeekAndDay(@Path("gymid") String gymId, @Body Map<String, String> body);

    @GET("exercise-relations/delete/{franchiseeId}/{workoutId}")
    Call<ResponseBody> resetWorkoutExercises(@Path("franchiseeId") String franchiseeId, @Path("workoutId") String workoutId, @Header(HEADER_FIELD_TAG) String tag);
}
