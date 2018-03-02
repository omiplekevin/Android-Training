package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMIPLEKEVIN on Jun 14, 2016.
 */

public class WeekDayConfigModel implements Serializable {


    @SerializedName("data")
    public List<WeekDayModel> data = new ArrayList<>();

    @SerializedName("success")
    public Boolean success;

    @SerializedName("operation")
    public Object operation;

    @SerializedName("errors")
    public Object errors;

    @SerializedName("userData")
    public Boolean userData;

    public class WeekDayModel implements Serializable {

        @SerializedName("week")
        public String week;

        @SerializedName("weekday")
        public String weekday;

        @SerializedName("workoutUrl")
        public String workoutName;

        @SerializedName("timeline")
        public String timeline;

        @Override
        public String toString() {
            return week + " " + weekday + " " + workoutName + " " + timeline;
        }

    }
}
