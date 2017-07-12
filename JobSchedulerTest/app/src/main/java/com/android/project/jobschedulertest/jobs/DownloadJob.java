package com.android.project.jobschedulertest.jobs;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 27, 2017<br/>
 * IN CLASS:        com.android.project.jobschedulertest.jobs<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class DownloadJob extends Job {

    public static final String TAG = "DownloadJob";
    private static int jobID = -1;

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Log.d("DownloadJob", "onRunJob");
        int count = 0;
        while (true) {
            try {
                Thread.sleep(500);
                Log.d("DownloadJob", "onRunJob count: " + count++);
                if (count == 10) {
                    break;
                }
                double rand = Math.random();
                if (rand < 0.3) {
                    // at 30% probability, invoke the Result.RESCHEDULE - this will re-run the Job again
                    Log.d("DownloadJob", "onRunJob " + rand);
                    return Result.RESCHEDULE;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Result.SUCCESS;
    }

    @Override
    protected void onReschedule(int newJobId) {
        super.onReschedule(newJobId);
        Log.d("DownloadJob", "onReschedule new job id: " + JobManager.instance().getJob(jobID).toString());
    }

    public static int scheduleJob() {
        jobID = new JobRequest.Builder(DownloadJob.TAG)
                .setExact(3000)
                .setUpdateCurrent(true)
                .setPersisted(true)
                .setRequiresDeviceIdle(false)
                .setBackoffCriteria(3000, JobRequest.BackoffPolicy.EXPONENTIAL)
                .build()
                .schedule();

        return jobID;
    }
}
