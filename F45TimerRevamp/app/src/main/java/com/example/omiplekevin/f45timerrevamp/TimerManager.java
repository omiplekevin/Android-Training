package com.example.omiplekevin.f45timerrevamp;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;


public class TimerManager {
    // finite rule
    // start -> pause
    // start -> stop
    // pause -> resume
    // resume -> pause
    // resume -> stop
    // stop -> start

    private final int ST8_START = 1;
    private final int ST8_PAUSE = 2;
    private final int ST8_RESUME = 4;
    private final int ST8_STOP = 8;
    private static final String TAG = "TimerManager";

    private int currentState = ST8_STOP;
    private int delay = 0 * 1000; // by milli
    private int interval = 1000; // by milli
    private long baseMilli = 0L;
    private long leapMilli = 0L;
    private TimerManagerCallback callBack = null;

    private Handler customHandler = new Handler();

    public TimerManager(int delay, int interval, TimerManagerCallback callBack) {
        setDelay(delay);
        setInterval(interval);
        setCallBack(callBack);
    }

    // same as start state but only forces references to timeStmp
    public void startTimerSyncServerEpoch(long timeStmp, long tuneUp) {
        Log.d("TimeManager", "startTimerSyncServerEpoch");
        if (currentState == ST8_STOP) {
            currentState = ST8_START;
            baseMilli = SystemClock.uptimeMillis();
            final long tmpEpoch = (System.currentTimeMillis() - timeStmp);
            final long tmpDelay = delay - (tmpEpoch / 1000);
            Log.e(TAG, "time delay: " + tmpDelay);
            customHandler.postDelayed(updateTimerThread, (tmpDelay + tuneUp));

        }
    }

    public void pauseTimer(long pauseTime, long tuneUp) {
        Log.d("TimeManager", "pauseTimer");
        if (((currentState & ST8_START) | (currentState & ST8_RESUME)) > 0) {
            currentState = ST8_PAUSE;
            customHandler.removeCallbacks(updateTimerThread);
            leapMilli = pauseTime + tuneUp;
            getCallBack().pause();
        }
    }

    public void resumeTimer(long timeStmp, long tuneUp) {
        Log.d("TimeManager", "startTimerSyncServerEpoch");
        if (currentState == ST8_PAUSE) {
            currentState = ST8_RESUME;
            customHandler.postDelayed(updateTimerThread, interval);
            baseMilli = baseMilli + ((timeStmp + tuneUp) - leapMilli);
            Log.e(TAG, "resume timer");
            getCallBack().resume();

        }
    }

    public void stopTimer() {
        Log.d("TimeManager", "stopTimer");
        if (((currentState & ST8_START) | (currentState & ST8_RESUME) | (currentState & ST8_PAUSE)) > 0) {
            currentState = ST8_STOP;
            baseMilli = 0L;
            customHandler.removeCallbacks(updateTimerThread);
            getCallBack().stop();
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        @Override
        public void run() {
            long tmpUptimeMillis = SystemClock.uptimeMillis();

            customHandler.postAtTime(this, tmpUptimeMillis + interval);
            getCallBack().tick(baseMilli, tmpUptimeMillis);
        }
    };

    public boolean isRunning() {
        boolean retValue = true;
        if (currentState == ST8_STOP)
            retValue = false;
        return retValue;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public TimerManagerCallback getCallBack() {
        return callBack;
    }

    public void setCallBack(TimerManagerCallback callBack) {
        this.callBack = callBack;
    }

}
