package com.omiplekevin.android.f45testbench.screenhelper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Holds the workout config
 * This object is Gson serialized
 */
public class WorkoutConfig implements Serializable {
    @SerializedName("workout_name")
    public String workoutName;

    @SerializedName("work")
    public int work;

    @SerializedName("rest")
    public int rest;

    @SerializedName("sets")
    public int repeats;

    @SerializedName("rounds")
    public int sets;

    @SerializedName("workoutrounds")
    public int rounds;

    public int runtimeSequence;

    public String task;
}
