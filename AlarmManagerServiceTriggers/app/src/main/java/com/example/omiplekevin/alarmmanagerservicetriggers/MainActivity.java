package com.example.omiplekevin.alarmmanagerservicetriggers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    JobTaskReceiver jobTaskReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ArrayList<PendingIntent> pendingIntents = new ArrayList<>();

        for (int i=1;i<=3;i++){
            String dateInMills = "";
            DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a", Locale.getDefault());
            try {
                Date d = formatter.parse("10/18/2015 05:00:55 pm");
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                dateInMills = cal.getTimeInMillis() + "";
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Random rand = new Random();
            int runID = rand.nextInt(10) * rand.nextInt(20);
            Log.e(TAG, "run ID:" + runID);
            Intent intent = new Intent(this, JobIntentService.class);
            intent.putExtra(JobIntentService.EXTRAS_TRIGGER_TIME, (long)(runID*1000));
            intent.putExtra(JobIntentService.EXTRAS_TRIGGER_DATE, dateInMills);
            PendingIntent pendingIntent = PendingIntent.getService(this, runID, intent, 0);

            //lazy switch
            switch (i){
                case 1:
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);
                    break;
                case 2:
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 6000, pendingIntent);
                    break;
                case 3:
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 9000, pendingIntent);
                    break;
            }

            pendingIntents.add(pendingIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(jobTaskReceiver);
        super.onDestroy();
    }

    private void registerReceiver(){
        jobTaskReceiver = new JobTaskReceiver();
        IntentFilter jobTaskReceiverFilter = new IntentFilter();
        jobTaskReceiverFilter.addAction(JobTaskReceiver.JOB_TASK_RCV_ACTION);
        JobTaskReceiver.setJobTriggerCallback(new JobTriggerCallback() {
            @Override
            public void onJobRequest(Long runID, String message) {
                Log.e(TAG, message);
            }
        });

        registerReceiver(jobTaskReceiver, jobTaskReceiverFilter);
    }
}
