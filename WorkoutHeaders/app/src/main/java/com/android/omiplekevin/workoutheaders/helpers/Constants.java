package com.android.omiplekevin.workoutheaders.helpers;

import android.os.Environment;

import com.android.omiplekevin.workoutheaders.model.ComplianceIssuesModel;
import com.android.omiplekevin.workoutheaders.model.ExercisesModel;
import com.android.omiplekevin.workoutheaders.model.ExercisesRelationModel;
import com.android.omiplekevin.workoutheaders.model.UserAccountModel;
import com.android.omiplekevin.workoutheaders.model.WeekDayConfigModel;
import com.android.omiplekevin.workoutheaders.model.timeline.AudioData;
import com.android.omiplekevin.workoutheaders.model.timeline.TimelineModel;
import com.android.omiplekevin.workoutheaders.model.timeline.WorkoutAll;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by OMIPLEKEVIN on 12/11/2015.
 *
 */
public class Constants {

    ///////////////////////////////////////////////////////////////////////////
    // ARRAYS
    ///////////////////////////////////////////////////////////////////////////

    public static List<String> EXERCISE_EQUIPMENT_FILTER;

    public static List<String> PLAYBOOK_DESCRIPTION;

    ///////////////////////////////////////////////////////////////////////////
    // INTEGER
    ///////////////////////////////////////////////////////////////////////////

    public static final int NANOHTTPD_PORT = 12345;

    public static final int TIMER_STATE_NO_ACTIVITY = 0;

    public static final int TIMER_STATE_PLAY = 1;

    public static final int TIMER_STATE_PAUSE = 2;

    public static final int TIMER_STATE_COUNTDOWN = 3;

    public static final int TIMER_STATE_FORWARD = 4;

    public static int TIMER_STATE = 0;

    public static int VOLUME = 0;

    public static int SCREEN_PLAYER = 0;

    public static int MAX_WEEKS;

    public static int INTRO_WEEKS = 4;

    public static int ALT_EXERCISE_NDX = 0;

    ///////////////////////////////////////////////////////////////////////////
    // STRING
    ///////////////////////////////////////////////////////////////////////////
    public static String HASH = "";

    public static String ACCESS_CODE = "";

    public static String STUDIO_NAME = "";

    public static String TIMER_IP = "";

    public static String CACHE_NOTES = null;

    public static String NEW_EXERCISE_ID = "";

    public static String TIMELINE_VERSION = "13";

    public static String SET_OFFLINE_WEEK_DAY = "";

    public static String PROGRAM = "";

    public static String TIMELINE_PROGRAM = "";

    public static final String WORKOUTS = "Workouts";

    public static final String INTROWEEKS = "Introductory Weeks";

    public static final String PLAYOFFS = "Playoffs";

    public static final String _TIMELOG = "http://f45timeline.herokuapp.com/v3/gym_logs/save";

    public static final String _VIDEO_STREAM = "http://cdn.f45.info/";

    public static final String _VIDEO_STREAM_NEW = "https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES_2017/";

    public static final String API_USER_TIMELINE = "timelines/get/";

    public static final String API_WEEK_DAY_TIMELINE = "timelines/week_day_timeline";

    public static final String API_EDIT_TIMELINE = "timelines/edit";

    public static final String API_GET_WORKOUTS = "workouts/get_workout";

    public static final String API_GET_FRANCHISEES = "franchisees";

    public static final String API_GET_EXERCISES = "exercises";

    public static final String API_GET_FRANCHISE_EXERCISES = "exercise-relations/get_gym_exercise/";

    public static final String API_SET_ALT_EXERCISE = "exercise-relations/set_as_alternative/";

    public static final String API_SAVE_FRANCHISE_EXERCISES = "exercise-relations/save_gym_exercises";

    public static final String API_REORDER_FRANCHISE_EXERCISE = "exercise-relations/reorder";

    public static final String API_POST_LOGIN = "franchisees/login";

    public static final String DIR_MAIN = Environment.getExternalStorageDirectory() + "/F45Power";

    public static final String DIR_VIDEO = DIR_MAIN + "/videos";

    public static final String DIR_TIMELINE = DIR_MAIN + "/timelines";

    public static final String DIR_HR = DIR_MAIN + "/hr";

    public static final String DIR_IMAGE = DIR_MAIN + "/images";

    public static final String DIR_MUSIC = DIR_MAIN + "/music";

    public static final String DIR_EXTRAS = DIR_MAIN + "/extras";

    public static final String DIR_TEMP = DIR_MAIN + "/.temp";

    public static final String DIR_APK = DIR_MAIN + "/apk";

    public static final String DIR_LOGS = DIR_MAIN + "/log";

    public static final String DIR_MOVEMENT = DIR_MAIN + "/movement";

    public static final String DIR_FFMPEG_RENDERS = DIR_MAIN + "/renders";

    public static final String FILENAME_TIMELINE = "timeline.json";

    public static final String FILENAME_MAIN_DIRECTORY = "F45Power";

    public static final String FILENAME_TEMP_DIRECTORY = ".temp";

    public static final String SHARED_PREFS_TABLE = "F45POWER";

    public static final String SHARED_PREFS_LOGGED_IN = "LOGGED_IN";

    public static final String SHARED_PREFS_OFFLINE_MODE = "OFFLINE_MODE_STATE";

    public static final String SHARED_PREFS_OFFLINE_TIMER_IP = "OFFLINE_TIMER_IP";

    public static final String SHARED_PREFS_OFFLINE_SELECTED_WEEK_DAY = "OFFLINE_WEEK_DAY";

    public static final String SHARED_PREFS_RAW_INFO = "FRANCHISEE_RAW_INFO";

    public static final String SHARED_PREFS_SAVED_TIMELINE_CONFIG = "CACHED_TIMELINE_CONFIG";

    public static final String SHARED_PREFS_SAVED_NOTES = "CACHED_NOTES";

    public static final String SHARED_PREFS_SAVED_TIMELINE_SUMMARY = "TIMELINE_SUMMARY";

    public static final String SHARED_PREFS_SAVED_TIMELINE_VERSION = "USE_TIMELINE_VERSION";

//    public static final String SHARED_PREFS_SAVED_EXERCISES = "CACHED_EXERCISES";

//    public static final String SHARED_PREFS_SAVED_EXERCISE_RELATIONS = "CACHED_EXERCISE_RELATIONS";

    public static final String SHARED_PREFS_LAST_USER_EMAIL = "LAST_USER_EMAIL";

    public static final String SHARED_PREFS_LAST_VOLUME = "LAST_VOLUME_SET";

    //for Timeline Summary Keys
    public static final String SUMMARY_KEY_VIDEOS = "videos";
    //for Timeline Summary Keys
    public static final String SUMMARY_KEY_WORKOUTS = "workouts";
    //for Timeline Summary Keys
    public static final String SUMMARY_KEY_IMAGE_COUNTDOWN = "image_countdown";
    //for Timeline Summary Keys
    public static final String SUMMARY_KEY_FULL_COUNTDOWN = "full_countdown";
    //for Timeline Summary Keys
    public static final String SUMMARY_KEY_TIMER = "timer";
    //for Timeline Summary Keys
    public static final String SUMMARY_KEY_IMAGE = "image";
    //for Timeline Summary Keys
    public static final String SUMMARY_KEY_KEYFRAME = "keyframe";

    ///////////////////////////////////////////////////////////////////////////
    // OBJECTS
    ///////////////////////////////////////////////////////////////////////////

    public static UserAccountModel USER_ACCOUNT_MODEL;

    public static TimelineModel TIMELINE_MODEL;

    public static WeekDayConfigModel WEEK_DAY_CONFIG;

    public static ExercisesModel EXERCISES_LIST;

    public static ExercisesRelationModel EXERCISE_RELATIONS;

    public static ComplianceIssuesModel COMPLIANCE_ISSUES;

    public static List<WorkoutAll.Data> INTRODUCTORY_WEEKS;

    public static List<WorkoutAll.Data> WORKOUTS_WEEK;

    public static WorkoutAll WORKOUT_ALL;

    public static LinkedHashMap<String,ArrayList<AudioData>> fmdatas;

    ///////////////////////////////////////////////////////////////////////////
    // REQUEST TYPES
    ///////////////////////////////////////////////////////////////////////////
    public static final String REQUEST_GET_TIMELINE = "timeline";

    public static final String REQUEST_GET_TIMELINE_SUMMARY = "timelineSummary";

    public static final String REQUEST_GET_ALL_WORKOUT = "allWorkout";

    public static final String REQUEST_GET_WEEK_DAY_CONFIG = "weekDayConfig";

    public static final String REQUEST_GET_COMPLIANCE = "compliance";

    public static final String REQUEST_GET_PLAYBOOK_TIMELINE = "playbookTimeline";

    public static final String REQUEST_GET_PROGRAMS = "programs";

    public static final String REQUEST_GET_EXERCISES = "exercises";

    public static final String REQUEST_GET_EXERCISE_RELATIONS = "exerciseRelations";

    public static final String REQUEST_GET_RESET_WORKOUT_EXERCISES = "resetWorkoutExercises";

    ///////////////////////////////////////////////////////////////////////////
    // BOOLEAN
    ///////////////////////////////////////////////////////////////////////////

    public static boolean ALTERNATE_BTN = false;

    public static boolean NEW_STATION = false;

    public static boolean UPDATING = false;

    public static boolean DOWNLOAD_TIMELINE = false;

    public static boolean DOWNLOADING = false;

    public static boolean SET_OFFLINE_MODE = false;

    public static boolean REFRESH_REQUIRED = false;

    public static boolean PERMISSION_STORAGE_GRANTED = true;

    /////////////////////////////////////////////////////////////////////////
    // HOTLINE.IO
    /////////////////////////////////////////////////////////////////////////
    public static String HOTLINE_APP_ID="d8e89bbf-4966-4f5c-b18c-d5bf1b8cde1c";
    public static String HOTLINE_APP_KEY="0adaab9e-4a7b-4dbf-81d7-9c72b3523f25";
}