package com.android.project.jobschedulertest;

import android.app.Application;

import com.android.project.jobschedulertest.jobcreator.TestJobCreator;
import com.evernote.android.job.JobManager;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 27, 2017<br/>
 * IN CLASS:        com.android.project.jobschedulertest<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new TestJobCreator());
    }
}
