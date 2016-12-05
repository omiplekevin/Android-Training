package com.omiplekevin.android.f45segmentprogressbar;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fatima on 2/17/2016.
 *
 */
public class Slideshow implements Serializable {

    @SerializedName("key")
    public String key;

    @SerializedName("duration")
    public int duration;

    @SerializedName("landscape_image")
    public String image;

}
