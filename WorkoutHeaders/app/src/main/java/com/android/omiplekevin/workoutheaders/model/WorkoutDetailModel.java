package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by OMIPLEKEVIN on 22/11/2015.
 */
public class WorkoutDetailModel implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("workoutUrl")
    public String workout_name;

    @SerializedName("week")
    public String week;

    @SerializedName("weekday")
    public String weekday;

    @SerializedName("work")
    public String work;

    @SerializedName("rest")
    public String rest;

    @SerializedName("sets")
    public String sets;

    @SerializedName("rounds")
    public String rounds;

    @SerializedName("notes")
    public String notes;

    @SerializedName("private_notes")
    public String private_notes;

    @SerializedName("circuit_movement")
    public String circuit_movement;

    @SerializedName("approved_by_name")
    public String approved_by_name;

    @SerializedName("status")
    public String status;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("workoutrounds")
    public String workoutrounds;

}
