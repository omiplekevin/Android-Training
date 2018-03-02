package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by OMIPLEKEVIN on 013 Feb 13, 2017.
 */

public class ComplianceItemModel implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("franchisee_id")
    public String franchisee_id;

    @SerializedName("number_of_issues")
    public String number_of_issues;

    @SerializedName("resolve_date")
    public String resolve_date;

    @SerializedName("timestamp")
    public String timestamp;
}