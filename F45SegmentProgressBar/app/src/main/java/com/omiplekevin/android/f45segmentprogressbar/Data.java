package com.omiplekevin.android.f45segmentprogressbar;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Fatima Ledesma on 9/8/2015.
 */
public class Data implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("timeline")
    public Map<Integer, Timeline> timeline;

}
