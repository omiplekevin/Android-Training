package com.android.project.jobschedulertest.jobcreator;

import com.android.project.jobschedulertest.jobs.DownloadJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 27, 2017<br/>
 * IN CLASS:        com.android.project.jobschedulertest.jobcreator<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class TestJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        switch (tag) {
            case DownloadJob.TAG:
                return new DownloadJob();
            default:
                return null;
        }
    }
}
