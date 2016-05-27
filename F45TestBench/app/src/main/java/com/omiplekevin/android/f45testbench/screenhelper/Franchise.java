package com.omiplekevin.android.f45testbench.screenhelper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Declare:
 * Franchise franchise = new Gson().fromJson(response, Franchise.class);
 * to parse the returned json data from requestConfiguration.
 */

public class Franchise implements Serializable {
    @SerializedName("franchisee_id")
    public String franchiseeId;

    @SerializedName("franchisee")
    public Franchisee franchisee;

    @SerializedName("workout_config")
    public WorkoutConfig workoutConfig;

    @SerializedName("totalStations")
    public int totalStations;

    @SerializedName("videos")
    public TreeMap<Integer, String> videoSourceUrls;

    @SerializedName("success")
    public String success;

    @SerializedName("operation")
    public String operation;

    @SerializedName("data")
    public String data;

    @SerializedName("errors")
    public String errors;

}
