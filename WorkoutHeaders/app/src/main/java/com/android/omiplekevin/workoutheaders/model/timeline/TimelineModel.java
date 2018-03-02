package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OMIPLEKEVIN on Jun 14, 2016.
 *
 */

public class TimelineModel implements Serializable {

    @SerializedName("allowplayoffs")
    public Integer allowplayoffsGlobally;

    @SerializedName("hashid")
    public String hashid;

    @SerializedName("apk_version")
    public Integer apk_version;

    @SerializedName("apk_path")
    public String apk_path;

    @SerializedName("apk_name")
    public String apk_name;

    @SerializedName("currenttimestamp")
    public long currenttimestamp;

    @SerializedName("version_hash")
    public String version_hash;

    @SerializedName("update")
    public int update;

    @SerializedName("ip")
    public ScreenTimestampModel[] ip;

    @SerializedName("json")
    public Json json;

    @SerializedName("from_local")
    public boolean from_local;

    @SerializedName("hash")
    public String hash;

    @SerializedName("name")
    public String name;

    @SerializedName("version")
    public String version;

    @SerializedName("apkurl")
    public String apkurl;

    public class Json{

        @SerializedName("week")
        public String week;

        @SerializedName("weekday")
        public String weekday;

        @SerializedName("franchisee")
        public FranchiseeModel franchisee;

        @SerializedName("users")
        public Users[] users;

        @SerializedName("workout_config")
        public WorkoutConfig workout_config;

        @SerializedName("totalStations")
        public Integer totalStations;

        @SerializedName("workout_descriptions")
        public Map<String, String> workoutDescription;

        @SerializedName("workout_info")
        public WorkOutInfo[] workout_info;

        @SerializedName("timeline")
        public Map<Integer, TimelineEntryModel> timeline;

        @SerializedName("operation")
        public String operation;

        @SerializedName("data")
        public Data data;

        @SerializedName("success")
        public boolean success;

        @SerializedName("errors")
        public String errors;

        @SerializedName("files")
        public String[] files;

        @SerializedName("downloads")
        public String[] downloads;

        @SerializedName("f45fm")
        public LinkedHashMap<String, AudioData[]> audioData;

        @SerializedName("preload")
        public List<String> preloadFiles;

        @SerializedName("tv6")
        public Slideshow[] tv6;

        @SerializedName("tv7")
        public Slideshow[] tv7;

        @SerializedName("tv8")
        public Slideshow[] tv8;

        @SerializedName("tv9")
        public Slideshow[] tv9;

        @SerializedName("videos")
        public Videos[] videos;

        @SerializedName("schedules")
        public LinkedHashMap<Long, String> schedules;

        public HashMap<String, String> variables;

        public HashMap<String, String> keyframes;

        public class WorkOutInfo{
            public String name;
            public Logo logo;
            public String movement_type = "pods";
            public int number_pods = 3;
            public Complete movement_complete;
            public Screen move;
            public Screen rest;
            public Screen work;
            public Screen hydrate_movement_animation;
            public String description;

            public WorkOutInfo(){

            }
        }

        public class Screen{
            @SerializedName("screen_1")
            public String screen1;

            @SerializedName("screen_2")
            public String screen2;

            @SerializedName("screen_3")
            public String screen3;
        }

        public class Complete{
            public String move;
            public String rest;
            public String work;
            public String hydrate;
        }

        public class Logo{
            public String small;
            public String large;
        }
    }
}
