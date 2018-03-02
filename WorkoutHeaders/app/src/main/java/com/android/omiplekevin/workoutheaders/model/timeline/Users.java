package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fatima on 5/24/2016.
 */
public class Users {

    @SerializedName("id")
    public int userId;

    @SerializedName("name")
    public String username;

    @SerializedName("description")
    public String userDesc;

    @SerializedName("large_image")
    public String largeImage;

    @SerializedName("quote")
    public String quote;

    @SerializedName("qualification")
    public String qualification;

    @SerializedName("is_show_billboard")
    public int isShowBillboard;

    @SerializedName("playoffs_score")
    public String playoffsScore;

}
