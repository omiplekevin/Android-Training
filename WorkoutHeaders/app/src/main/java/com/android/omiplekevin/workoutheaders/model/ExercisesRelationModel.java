package com.android.omiplekevin.workoutheaders.model;

/**
 * Created by OMIPLEKEVIN on 08/12/2015.
 *
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExercisesRelationModel {

    @SerializedName("operation")
    public String operation;

    @SerializedName("workout_id")
    public String workout_id;

    @SerializedName("data")
    public List<ExerciseRelation> data = new ArrayList<>();

    @SerializedName("success")
    public Boolean success;

    @SerializedName("errors")
    public Object errors;

    public class ExerciseRelation {

//        public HashMap<Integer, Integer[][]> alternativeExerciseMapping;

        public String[] alternateExercises;

        @SerializedName("use_alternative_exercises")
        public String use_alternative_exercises;

        @SerializedName("id")
        public String id;

        @SerializedName("order")
        public String order;

        @SerializedName("franchisee_id")
        public String franchisee_id;

        @SerializedName("exercise_id")
        public String exercise_id;

        @SerializedName("alternative_exercise_id")
        public String alternative_exercise_id;

        @SerializedName("use_alternative_exercise")
        public String use_alternative_exercise;

        @SerializedName("alternative_exercises")
        public Exercise[] alternate_exercises;

        public class Exercise {

            Exercise() {
                exerciseId = "0";
                alternative_exercise = "-";
            }

            @SerializedName("exercise_id")
            public String exerciseId;

            @SerializedName("exercise_name")
            public String alternative_exercise;
        }

        @Override
        public String toString() {
            return use_alternative_exercises + " "
                    + order + " "
                    + franchisee_id + " "
                    + use_alternative_exercise + " "
                    + exercise_id + " "
                    + alternative_exercise_id + " "
                    + id;
        }
    }

    public void setExerciseAlternativeMapping(){
        if (data != null) {
            for (ExerciseRelation entry : data) {
                if (entry.alternate_exercises.length > 0) {
                    entry.alternateExercises = new String[entry.alternate_exercises.length + 1];
                    entry.alternateExercises[0] = entry.exercise_id;
                    for (int i = 1; i < entry.alternate_exercises.length + 1; i++) {
                        entry.alternateExercises[i] = entry.alternate_exercises[i - 1].exerciseId;
                    }
                }
            }
        }
    }
}