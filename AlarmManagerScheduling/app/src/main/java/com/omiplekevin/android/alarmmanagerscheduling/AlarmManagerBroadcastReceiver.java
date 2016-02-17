package com.omiplekevin.android.alarmmanagerscheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by OMIPLEKEVIN on Feb 09, 2016.
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    private String MESSAGE_TAG = "ALARM_MESSAGE";

    private static AlarmReceiverCallback alarmReceiverCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ALARMMANAGER", "onReceive");
        //You can do the processing here.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();
        String messageTagExtra = extras.getString(MESSAGE_TAG, "");
        if (!messageTagExtra.isEmpty()) {
            msgStr.append("One time Timer : ");
        }
        msgStr.append(messageTagExtra);
        Log.e("ALARMMANAGER", msgStr.toString());

        setAlarm(context, 5000);

        alarmReceiverCallback.onReceiveAlarmTask(msgStr.toString());
    }

    public void setAlarm(Context context, int nextSchedule) {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(MESSAGE_TAG, nextSchedule + " Start Timeline NOW!");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }

    public void setAlarmReceiverCallback(AlarmReceiverCallback callback) {
        alarmReceiverCallback = callback;
    }

    public interface AlarmReceiverCallback{
        void onReceiveAlarmTask(String message);
    }
}
