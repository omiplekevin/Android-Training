package com.omiplekevin.android.f45testbench;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.omiplekevin.android.f45testbench.jobscheduler.TestBenchJobCreator;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 21, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class TestBench extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new TestBenchJobCreator());
    }
}
