package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by OMIPLEKEVIN on 23/11/2015.
 */
public class ConfigModel implements Serializable {

    @SerializedName("hashid")
    public String hashid;

    @SerializedName("ip")
    public Map<Integer, String> ip;

    @SerializedName("week")
    public int week;

    @SerializedName("weekday")
    public int weekday;

    @SerializedName("notes")
    public String notes;

    @SerializedName("success")
    public boolean success;

    @SerializedName("operation")
    public String operation;

    @SerializedName("data")
    public String data;

    @SerializedName("errors")
    public String errors;

    @SerializedName("userData")
    public boolean userData;

}
