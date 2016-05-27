package com.omiplekevin.android.timelinedownloadintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by OMIPLEKEVIN on May 05, 2016.
 */
public class TimelineDownloadIntentService extends IntentService {

    private static volatile TimelineDownloadIntentService _instance;

    private static final String TAG = "TDIS";

    public static TimelineDownloadIntentService getInstance(){
        Log.e(TAG, "getting instance...");
        if (_instance == null) {
            synchronized (TimelineDownloadIntentService.class) {
                if (_instance == null) {
                    _instance = new TimelineDownloadIntentService();
                }
            }
        }

        return _instance;
    }

    public TimelineDownloadIntentService() {
        super("TimelineDownloadIntentService");
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent");

    }
}
