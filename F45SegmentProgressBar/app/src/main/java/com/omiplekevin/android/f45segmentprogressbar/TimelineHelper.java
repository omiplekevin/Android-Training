package com.omiplekevin.android.f45segmentprogressbar;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Fatima Ledesma on 9/8/2015.
 */
public class TimelineHelper implements Serializable {

    @SerializedName("hashid")
    public String hashid;

    @SerializedName("hash")
    public String hash;

    @SerializedName("apk_version")
    public Integer apk_version;

    @SerializedName("apk_path")
    public String apk_path;

    @SerializedName("currenttimestamp")
    public long currenttimestamp;

    /*@SerializedName("screen_timestamp")
    public ScreenTimestamp[] screenTimestamp;*/

    @SerializedName("version_hash")
    public String version_hash;

    @SerializedName("update")
    public int update;

    @SerializedName("ip")
    public Map<Integer, String> ip;

    @SerializedName("json")
    public Json json;

    @SerializedName("name")
    String name;

    @SerializedName("version")
    String version;

    @SerializedName("apkurl")
    String apkurl;

    public class Json{

        @SerializedName("week")
        public Integer week;

        @SerializedName("weekday")
        public Integer weekday;

        @SerializedName("franchisee")
        public Franchisee franchisee;

        @SerializedName("workout_config")
        public WorkoutConfig workout_config;

        @SerializedName("totalStations")
        public Integer totalStations;

        @SerializedName("timeline")
        public Map<Integer, Timeline> timeline;

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
        public Map<Long, String> schedules;
    }
}
