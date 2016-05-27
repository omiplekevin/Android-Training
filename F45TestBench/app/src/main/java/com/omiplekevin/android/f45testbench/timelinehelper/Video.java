package com.omiplekevin.android.f45testbench.timelinehelper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fatima Ledesma on 9/9/2015.
 */
public class Video implements Serializable {

  @SerializedName("video")
  public String video;

  @SerializedName("duration")
  public String duration;

  @SerializedName("name")
  public String name;

}