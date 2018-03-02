package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OMIPLEKEVIN on 11/09/2015.
 *
 */
public class NanoResponseModel {

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private boolean result;

    @SerializedName("extras")
    private String extras;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
}
