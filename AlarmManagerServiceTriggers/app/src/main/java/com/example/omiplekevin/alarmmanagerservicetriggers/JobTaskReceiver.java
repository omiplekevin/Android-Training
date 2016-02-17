package com.example.omiplekevin.alarmmanagerservicetriggers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by OMIPLEKEVIN on 18/10/2015.
 */
public class JobTaskReceiver extends BroadcastReceiver {

    public static final String RUN_JOB = "RUN_JOB";
    public static final String RUN_DATE = "RUN_DATE";
    public static final String JOB_TASK_RCV_ACTION = "JobTaskReceiverAction";
    public static JobTriggerCallback jobTriggerCallback;

    public JobTaskReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Long jobRunID = intent.getExtras().getLong(JobTaskReceiver.RUN_JOB);
        String dateTrigger = intent.getExtras().getString(JobTaskReceiver.RUN_DATE);
        if (jobTriggerCallback != null) {
            jobTriggerCallback.onJobRequest(jobRunID, "I've been called to this " + jobRunID + " task @ " + dateTrigger + "...");
        }
    }

    public static void setJobTriggerCallback(JobTriggerCallback jobTriggerCallback){
        JobTaskReceiver.jobTriggerCallback = jobTriggerCallback;
    }
}
