package com.omiplekevin.android.f45testbench.timelinehelper;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Fatima on 5/16/2016.
 */
public class Trainer implements Serializable {

    @SerializedName("data")
    public Map<String, TrainerData> trainerData;

    public class TrainerData {
        @SerializedName("franchisee_name")
        public String franchiseeName;

        @SerializedName("id")
        public int tainerId;

        @SerializedName("name")
        public String trainerName;

        @SerializedName("email")
        public String trainerEmail;

        @SerializedName("contact_num")
        public String contactNum;

        @SerializedName("phone_code")
        public String phoneCode;

        @SerializedName("user_photo")
        public String userPhoto;

        @SerializedName("is_show_trainer")
        public String isShowTrainer;

        @SerializedName("role")
        public String role;

        @SerializedName("description")
        public String trainerDescription;

        @SerializedName("franchisee_id")
        public String franchiseeId;

        @SerializedName("status")
        public String trainerStatus;

        @SerializedName("user_level")
        public String userLevel;

        @SerializedName("academy")
        public String academy;

        @SerializedName("participating_sessions")
        public String participatingSessions;

        @SerializedName("shadowed_sessions")
        public String shadowedSessions;

        @SerializedName("conducted_sessions")
        public String conductedSessions;

        @SerializedName("approved_trainer")
        public String approvedTrainer;

        @SerializedName("is_team_captain")
        public String isTeamCaptain;

        @SerializedName("is_trainer")
        public String isTrainer;

        @SerializedName("is_state_manager")
        public String isStateManager;

        @SerializedName("is_approved_web_pub")
        public String isApprocedWebPub;

        @SerializedName("is_show_billboard")
        public String isShowBillboard;

        @SerializedName("large_image")
        public String largeImage;

        @SerializedName("quote")
        public String quote;

        @SerializedName("qualification")
        public String qualification;

        @SerializedName("playoffs_score")
        public String playoffsScore;

    }
}
