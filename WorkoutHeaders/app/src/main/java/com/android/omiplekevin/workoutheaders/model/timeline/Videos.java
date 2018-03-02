package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 10/23/2015.
 */
public class Videos {
    @SerializedName("name")
    public String videoName;

    @SerializedName("video")
    public String video;

    @SerializedName("displaynumber")
    public String displaynumber;

    @SerializedName("displaytext")
    public String displaytext;
}
