package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by OMIPLEKEVIN on Jun 14, 2016.
 */

public class TimelineEntryModel implements Serializable {

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

}
