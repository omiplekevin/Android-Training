package com.omiplekevin.android.f45testbench.timelinehelper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fatima Ledesma on 9/8/2015.
 *
 */
public class Timeline implements Serializable {

    @SerializedName("key")
    public String key;

    @SerializedName("value")
    public String value;

    @SerializedName("text")
    public String text;

    @SerializedName("countdown")
    public int countdown;

    @SerializedName("portrait_image")
    public String portrait_image;

    @SerializedName("landscape_image")
    public String landscape_image;

    @SerializedName("portrait_video")
    public String portrait_video;

    @SerializedName("landscape_video")
    public String landscape_video;

    @SerializedName("number_of_exercises")
    public int number_of_exercises;

    @SerializedName("video")
    public String video;

    @SerializedName("title")
    public String title;

    @SerializedName("color")
    public String timelineColor;

    @SerializedName("set")
    public int set;

    @SerializedName("mode")
    public String mode;

    @SerializedName("set_repeat")
    public int set_repeat;

    @SerializedName("duration")
    public int duration;

    @SerializedName("section_duration")
    public int sectionDuration;

    @SerializedName("section_remaining")
    public int sectionCompleted;

    @SerializedName("videos")
    public Video[] videos;

    @SerializedName("content_id")
    public int contentId;

    @SerializedName("progress")
    public int progress;

    @SerializedName("countdown_duration")
    public int countdown_duration;

    @SerializedName("exercise_id")
    public String exercise_id;

    @SerializedName("name")
    public String name;

    public class KEY{
        public static final String _TIMER = "timer";
        public static final String _IMAGE_COUNTDOWN = "image_countdown";
        public static final String _FULL_COUNTDOWN= "full_countdown";
        public static final String _VIDEOS = "videos";
        public static final String _WORKOUTS = "workouts";
        public static final String _VIDEO = "video";
        public static final String _IMAGE = "image";
        public static final String _TEXT = "text";
    }

}
