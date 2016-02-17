package com.example.omiplekevin.f45timerrevamp;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by OMIPLEKEVIN on 15/10/2015.
 */
public class TimerActivity extends Activity {

    private static int CURRENT_TICK = 0;
    private NanoHttpdBroadcastReceiver mNanoHttpdBroadcastReceiver;
    private static final String TAG = "TIMER";
    private TimerManager mTimerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeReceivers();
        initializeTimerManagerCallback();

        // TODO: 15/10/2015 do 60sec interval check for timeline
        // TODO: 15/10/2015 command receiver from the controller

        //temporary, for services


    }

    // TODO: 15/10/2015 copy
    public static int getTime() {
        synchronized (MainActivity.class) {
            return CURRENT_TICK;
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    private void grabConfig(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initializeReceivers() {
        //broadcast receiver for nanohttpd service
        mNanoHttpdBroadcastReceiver = NanoHttpdBroadcastReceiver.getInstance();
        IntentFilter nanoServiceBroadcastFilter = new IntentFilter();
        nanoServiceBroadcastFilter.addAction(NanoHttpdBroadcastReceiver._NANO_SERVICE_BROADCAST_RECEIVER);
        mNanoHttpdBroadcastReceiver.setCallback(new NanoHttpdCallback() {
            @Override
            public void onMessageReceived(String message) {
//                showHideSuperSeries(false, true);
                Log.e(TAG, message);
                if (message.equalsIgnoreCase("reboot")) {
//                    Utilities.reRunActivityIntent(getBaseContext(), LoginActivity.class);
                } else {
                    timerUpdate(message);
                }
            }
        });
        registerReceiver(mNanoHttpdBroadcastReceiver, nanoServiceBroadcastFilter);
    }

    // TODO: 15/10/2015 copy
    private void timerUpdate(final String commandParams){
        switch (commandParams){
            case "start"://======================================================================================================= START TIMER
                Log.e(TAG, "startTimer");
                Constants._SERVER_TIME = System.currentTimeMillis();
                Constants._DEVICE_TIME = Long.valueOf(Calendar.getInstance(TimeZone.getTimeZone("GMT"))
                        .getTimeInMillis() + "");

                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (mTimerManager == null) {
                            Log.e(TAG, "handler");
                            initializeTimerManagerCallback();
                        }
                        mTimerManager.startTimerSyncServerEpoch(Constants._SERVER_TIME, 0);
                        return false;
                    }
                });

                Constants._TIMER_STATE = Constants._TIMER_RUN;
                final Timer timerStarter = new Timer();
                TimerTask waitingTime = new TimerTask() {
                    @Override
                    public void run() {
                        if (Constants._SERVER_TIME >= System.currentTimeMillis() ||
                                Constants._SERVER_TIME < System.currentTimeMillis()) {
                            timerStarter.cancel();
                            handler.obtainMessage().sendToTarget();
                        }
                    }
                };
                timerStarter.schedule(waitingTime, 5, 10);
                break;
            case "pause"://======================================================================================================= PAUSE TIMER
                Constants._PAUSE_TIME = System.currentTimeMillis();
                Constants._TIMER_STATE = Constants._TIMER_PAUSE;

                if(mTimerManager != null) {
                    mTimerManager.pauseTimer(Constants._PAUSE_TIME, 0);
                }
                break;
            case "resume"://======================================================================================================= RESUME TIMER
                Constants._TIMER_STATE = Constants._TIMER_RUN;
                Constants._RESUME_TIME = System.currentTimeMillis();

                if(mTimerManager != null) {
                    mTimerManager.resumeTimer(Constants._RESUME_TIME, 0);
                }

                break;
            case "stop"://======================================================================================================= STOP TIMER
                if ( mTimerManager != null && mTimerManager.isRunning()) {
                    mTimerManager.stopTimer();
                }

                Constants._TIMER_STATE = Constants._TIMER_NO_ACT;
                CURRENT_TICK = 0;
                break;
        }
    }

    /**
     * initialize timer manager callback for managing activities on given of the timer
     */
    // TODO: 15/10/2015 copy
    public void initializeTimerManagerCallback() {
        mTimerManager = new TimerManager(0, 1000, new TimerManagerCallback() {

            int lastIndex = -1;
            int lastDisplaySec = -1;
            int index = -1;

            AtomicBoolean preBeepWorkout = new AtomicBoolean(Boolean.FALSE);
            AtomicBoolean workStartBeep = new AtomicBoolean(Boolean.TRUE);
            AtomicBoolean restBeep = new AtomicBoolean(Boolean.TRUE);

            @Override
            public void tick(long baseMilli, long elapsedMilli) {
                index = ((int) (((elapsedMilli - baseMilli))) / 1000);
                CURRENT_TICK = index;
                if (index >= 0) {
                    Log.e(TAG, index + "");
//                    Log.e(TAG, Constants._NEW_TIMELINE[index+1].key + " " + Constants._NEW_TIMELINE[index+1].value);
                    MatrixScreenConnectionService.matrixScreenRequestCallback.onBroadcast(index+"|"+Constants._TIMER_STATE);

                } else {
                    Log.e(TAG, "Index is below zero : " + index);
                }

                Handler h = new Handler();
                h.obtainMessage().sendToTarget();
            }

            @Override
            public void stop() {
                index = -1;
                // TODO: 15/10/2015 change status here
                Constants._TIMER_STATE = Constants._TIMER_NO_ACT;
            }

            @Override
            public void pause() {
                if (index >= 0) {
                    // TODO: 15/10/2015 change status here
                    Constants._TIMER_STATE = Constants._TIMER_PAUSE;
                }
            }

            @Override
            public void resume() {
                // TODO: 15/10/2015 change status here
                Constants._TIMER_STATE = Constants._TIMER_PAUSE;
            }
        });
    }
}
