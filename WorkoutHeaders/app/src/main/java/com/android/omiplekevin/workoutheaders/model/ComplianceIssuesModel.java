package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.ToString;

/**
 * Created by OMIPLEKEVIN on 013 Feb 13, 2017.
 */

@ToString
public class ComplianceIssuesModel implements Serializable {

    @SerializedName("data")
    public ComplianceItemModel[] data;

    @SerializedName("success")
    public boolean success;

    @SerializedName("operation")
    public String operation;

    @SerializedName("errors")
    public String[] errors;

    @SerializedName("userData")
    public Object userData;
}
