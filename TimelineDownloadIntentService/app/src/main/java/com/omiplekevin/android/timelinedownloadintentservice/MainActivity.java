package com.omiplekevin.android.timelinedownloadintentservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    TimelineDownloadIntentService timelineDownloadIntentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timelineDownloadIntentService = TimelineDownloadIntentService.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent
    }
}
