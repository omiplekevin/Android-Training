package com.omiplekevin.android.f45testbench.jobscheduler;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 21, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.jobscheduler<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class TestBenchJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        switch (tag) {
            case DownloadFileJob.TAG:
                return new DownloadFileJob();
            default:
                return null;
        }
    }
}
