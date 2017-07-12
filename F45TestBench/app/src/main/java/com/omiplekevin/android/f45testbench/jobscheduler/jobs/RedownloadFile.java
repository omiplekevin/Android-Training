package com.omiplekevin.android.f45testbench.jobscheduler.jobs;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.DownloadManager;

import java.io.IOException;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 27, 2017<br/>
 * IN CLASS:        com.android.project.jobschedulertest.jobs<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class RedownloadFile extends Job {

    public static final String TAG = "RedownloadFile";
    public static final String EXTRA_JOB_ID = "job_id";

    private static DownloadManager downloadManager;

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Log.d("RedownloadFile", "onRunJob");
        int taskId = params.getExtras().getInt(EXTRA_JOB_ID, -1);
        if (taskId != -1) {
            try {
                RedownloadFile.downloadManager.getDownloadManagerPro().startDownload(taskId);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.RESCHEDULE;
            }
        } else {
            return Result.RESCHEDULE;
        }
        return Result.SUCCESS;
    }

    public static void scheduleJob(DownloadManager downloadManager, JobRequest jobRequest) {
        RedownloadFile.downloadManager = downloadManager;
        jobRequest.schedule();
    }
}
