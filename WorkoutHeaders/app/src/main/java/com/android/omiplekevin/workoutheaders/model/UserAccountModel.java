package com.android.omiplekevin.workoutheaders.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.ToString;

/**
 * Created by OMIPLEKEVIN on 17/11/2015.
 */
@ToString
public class UserAccountModel implements Serializable {

    @SerializedName("division_number")
    public String divisionNumber;

    @SerializedName("studio_name")
    public String studioName;

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

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
    public String description;

    @SerializedName("franchisee_id")
    public String franchiseeId;

    @SerializedName("status")
    public String status;

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
    public String isApprovedWebPub;

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

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("token")
    public String token;

    @SerializedName("gym_access_code")
    public String gymAccessCode;

    /**
     * If checked, the user is allowed to manage the workouts
     */
    @SerializedName("admin_access")
    public String adminAccess;

    /**
     * If checked, the user is allowed to set any desired week and weekday
     */
    @SerializedName("is_admin")
    public int isAdmin;
}
