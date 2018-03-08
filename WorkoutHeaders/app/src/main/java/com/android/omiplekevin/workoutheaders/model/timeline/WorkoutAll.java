package com.android.omiplekevin.workoutheaders.model.timeline;

import com.android.omiplekevin.workoutheaders.model.ExercisesRelationModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Fatima on 8/23/2017.
 *
 */

public class WorkoutAll implements Serializable {

    @SerializedName("operation")
    public String operation;

    @SerializedName("data")
    public ArrayList<Data> data;

    @ToString(exclude = {"id", "timeline", "work", "rest",
                        "sets", "rounds", "approved_by_name",
                        "workoutrounds", "program_id"})
    public static class Data {

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

        @Getter @Setter
        private String workoutUrl;

        @Getter @Setter
        private String dayOfWeek;

        @Getter @Setter
        private String systemWeekday;

        @Getter @Setter
        private Calendar calendar = Calendar.getInstance();

        //playbook value fields
        @Getter @Setter
        private String url_sessionHighlight = "";

        @Getter @Setter
        private String thumb_sessionHighlight = "";

        @Getter @Setter
        private String url_workoutInfo = "";

        @Getter @Setter
        private String thumb_workoutInfo = "";

        @Getter @Setter
        private String small_workout_movement_gif = "";

        @Getter @Setter
        private String large_workout_movement_gif = "";


        //formatted label fields
        @Getter @Setter
        private String formatted_day_label = "";
        @Getter @Setter
        private String formatted_date_label = "";

        @Getter @Setter
        private List<String> workoutDescriptionList = new ArrayList<>();

        @Getter @Setter
        private ExercisesRelationModel exerciseRelations;
    }
}
