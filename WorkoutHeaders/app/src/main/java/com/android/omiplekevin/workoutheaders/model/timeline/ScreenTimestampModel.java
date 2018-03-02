package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatima on 2/1/2016.
 *
 */
public class ScreenTimestampModel {

    @SerializedName("gym_access_code")
    public String gym_access_code;

    @SerializedName("screen")
    public String screen_id;

    @SerializedName("ip")
    public String screen_ip;

    @SerializedName("timestamp")
    public long timestamp;

}
