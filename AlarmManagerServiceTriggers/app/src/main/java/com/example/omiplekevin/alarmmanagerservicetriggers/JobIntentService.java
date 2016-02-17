package com.example.omiplekevin.alarmmanagerservicetriggers;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by OMIPLEKEVIN on 18/10/2015.
 */
public class JobIntentService extends IntentService {

    public static final String TAG = "JobIntentService";
    public static final String EXTRAS_TRIGGER_TIME = "TRIGGER_TIME";
    public static final String EXTRAS_TRIGGER_DATE = "TRIGGER_DATE";

    public JobIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long scheduledTime = 0;
        String runDate = "";
        try {
            Log.e(TAG, "started service...");
            scheduledTime = intent.getExtras().getLong(JobIntentService.EXTRAS_TRIGGER_TIME);
            runDate = intent.getExtras().getString(JobIntentService.EXTRAS_TRIGGER_DATE);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(JobTaskReceiver.JOB_TASK_RCV_ACTION);
            broadcastIntent.putExtra(JobTaskReceiver.RUN_JOB, scheduledTime);
            broadcastIntent.putExtra(JobTaskReceiver.RUN_DATE, runDate);
            sendBroadcast(broadcastIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
