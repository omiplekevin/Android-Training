package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fatima on 11/13/2015.
 *
 */
public class StationModel implements Serializable {

    @SerializedName("stationNumber")
    public String stationNumber;

    @SerializedName("exerciseName")
    public String exerciseName;

}
