package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OMIPLEKEVIN on Apr 12, 2016.
 *
 */
public class AudioData {

    @SerializedName("duration")
    public String musicDuration;

    @SerializedName("workout")
    public String workout;

    @SerializedName("music")
    public String music;

    @SerializedName("link")
    public String link;

    @SerializedName("cover")
    public String cover;
}
