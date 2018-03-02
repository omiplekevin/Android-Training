package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

/**
 * Created by OMIPLEKEVIN on 07/12/2015.
 */
@ToString
public class ExercisesModel {

    @SerializedName("operation")
    @Expose
    public String operation;
    @SerializedName("data")
    @Expose
    public List<ExerciseModel> data = new ArrayList<ExerciseModel>();
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("errors")
    @Expose
    public Object errors;
    @SerializedName("userData")
    @Expose
    public Boolean userData;

    @ToString
    public class ExerciseModel{

        @SerializedName("id")
        @Expose
        public Integer id;

        @SerializedName("exercise")
        @Expose
        public String exercise;

        @SerializedName("equipment")
        @Expose
        public String equipment;

        @SerializedName("alternative_exercise")
        @Expose
        public Integer alternative_exercise;

        @SerializedName("muscle_group")
        @Expose
        public String muscleGroup;

        @SerializedName("explaination_time")
        @Expose
        public Integer explanation_time;

        @SerializedName("notes")
        @Expose
        public String notes;

        @SerializedName("filename")
        @Expose
        public String filename;

        @SerializedName("newvideo")
        @Expose
        public String newvideo;

        @SerializedName("video_status")
        @Expose
        public String videoStatus;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("timestamp")
        @Expose
        public String timestamp;

        ///////////////////////////////////////////////////////////////////////////
        // FOR ADDING STATIONS
        ///////////////////////////////////////////////////////////////////////////
        @SerializedName("exercise_relations_id")
        @Expose
        public String exercise_relations_id;

        @SerializedName("alternative_exercise_id")
        @Expose
        public String alternative_exercise_id;

        @SerializedName("alternative_exercise_index")
        @Expose
        public String alternative_exercise_index;
    }

}
