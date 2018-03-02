package com.android.omiplekevin.workoutheaders.model.timeline;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by OMIPLEKEVIN on 005 Jan 05, 2017.
 */

public class WorkoutInfo implements Serializable {

    @SerializedName("logo")
    public Logo logo;

    @SerializedName("movement_complete")
    public MovementComplete movement_complete;

    @SerializedName("move")
    public ScreenMovement move;

    @SerializedName("rest")
    public ScreenMovement rest;

    @SerializedName("work")
    public ScreenMovement work;

    @SerializedName("hydrate_movement_animation")
    public ScreenMovement hydrate_movement_animation;

    @SerializedName("name")
    public String name;



    public class Logo {
        @SerializedName("small")
        public String small;

        @SerializedName("large")
        public String large;
    }

    public class MovementComplete {
        @SerializedName("move")
        public String move_complete;
        @SerializedName("rest")
        public String rest_complete;
        @SerializedName("work")
        public String work_complete;
        @SerializedName("hydrate")
        public String hydrate;
    }

    public class ScreenMovement {

        @SerializedName("screen_1")
        public String screen_1;
        @SerializedName("screen_2")
        public String screen_2;
        @SerializedName("screen_3")
        public String screen_3;
    }

}
