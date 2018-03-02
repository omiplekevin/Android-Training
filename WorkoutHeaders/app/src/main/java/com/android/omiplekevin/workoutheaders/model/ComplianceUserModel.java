package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by OMIPLEKEVIN on 013 Feb 13, 2017.
 */

public class ComplianceUserModel implements Serializable {


    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("username")
    public String username;

    @SerializedName("type")
    public String type;

    @SerializedName("email")
    public String email;
}
