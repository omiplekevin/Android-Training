package com.omiplekevin.android.f45testbench.jobscheduler.jobcreator;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.omiplekevin.android.f45testbench.jobscheduler.jobs.RedownloadFile;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 27, 2017<br/>
 * IN CLASS:        com.android.project.jobschedulertest.jobcreator<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class RedownloadJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        switch (tag) {
            case RedownloadFile.TAG:
                return new RedownloadFile();
            default:
                return null;
        }
    }
}
