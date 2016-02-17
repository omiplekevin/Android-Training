package com.example.omiplekevin.alarmmanagerservicetriggers;

/**
 * Created by OMIPLEKEVIN on 18/10/2015.
 */
public interface JobTriggerCallback {

    void onJobRequest(Long runID, String message);
}
