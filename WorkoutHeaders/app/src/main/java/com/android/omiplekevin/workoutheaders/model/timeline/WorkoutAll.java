package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fatima on 8/23/2017.
 *
 */

public class WorkoutAll implements Serializable {

    @SerializedName("operation")
    public String operation;

    @SerializedName("data")
    public ArrayList<Data> data;

    public static class Data {

        public static void Data() {}

        @SerializedName("id")
        public int id;

        @SerializedName("workout_name")
        public String workout_name;

        @SerializedName("week")
        public String week;

        @SerializedName("weekday")
        public String weekday;

        @SerializedName("timeline")
        public String timeline;

        @SerializedName("work")
        public String work;

        @SerializedName("rest")
        public String rest;

        @SerializedName("sets")
        public String sets;

        @SerializedName("rounds")
        public String rounds;

        @SerializedName("approved_by_name")
        public String approved_by_name;

        @SerializedName("workoutrounds")
        public String workoutrounds;

        @SerializedName("program_id")
        public String program_id;

        public String workoutUrl;
        public String dayOfWeek;
        public String systemWeekday;
        public Calendar calendar;
    }
}
