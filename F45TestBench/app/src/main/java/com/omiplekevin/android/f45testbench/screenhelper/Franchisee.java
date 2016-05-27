package com.omiplekevin.android.f45testbench.screenhelper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Holds franchisee details obtained from requestConfiguration
 */

public class Franchisee implements Serializable {
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("test_studio")
    public Boolean test_studio;

    @SerializedName("f45tv_demo_mode")
    public Boolean demo;

    @SerializedName("location")
    public String location;

    @SerializedName("week")
    public String week;

    @SerializedName("weekday")
    public String weekday;

    @SerializedName("average_review_score")
    public String average_review_score;

    @SerializedName("leaderboard_position")
    public String leaderboard_position;

    @SerializedName("status")
    public String status;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("weekday_updated_on")
    public String weekday_updated_on;

    @SerializedName("deployed")
    public String deployed;

    @SerializedName("local_ip")
    public String local_ip;

    @SerializedName("email")
    public String email;

    @SerializedName("facebookurl")
    public String facebookurl;

    @SerializedName("phone")
    public String phone;

    @SerializedName("instagramname")
    public String instagramname;

    @SerializedName("expected_opening_on")
    public String expected_opening_on;

    @SerializedName("hq_checklist")
    public String hq_checklist;

    @SerializedName("status_checklist")
    public String status_checklist;

    @SerializedName("timezone")
    public String timezone;

}