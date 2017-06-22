package com.omiplekevin.android.f45testbench.jobscheduler;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.DownloadManagerPro;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums.QueueSort;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.report.listener.DownloadManagerListener;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.DownloadEvent;

import java.io.IOException;
import java.util.List;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 19, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.downloadmanagerpro.demo<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class DownloadFileJob extends Job implements DownloadManagerListener{

    public static final String TAG = "DownloadFileJob";
    private DownloadManagerPro downloadManagerPro;
    private int queuePrioritization = QueueSort.HighToLowPriority;
    private List<DownloadEvent> downloadEvents;
    private int currentJobID = -1;
    int taskId = -1;
    int startExecutionWindow = -1;
    int endExecutionWindow = -1;

    public DownloadFileJob() {
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        if (downloadEvents != null) {
            Log.d("ADMDemo", "onClick invoke start download");
            if (this.taskId != -1) {
                try {
                    downloadManagerPro.startDownload(currentJobID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.w(TAG, "onClick:  downloadEventList is null!");
        }
        return Result.SUCCESS;
    }

    @Override
    protected void onReschedule(int newJobId) {
        Log.w(TAG, "onReschedule: new JobID from: " + currentJobID + " to " + newJobId);
        super.onReschedule(newJobId);
    }

    @Override
    public void OnDownloadStarted(long taskId) {
        Log.d("DownloadFileJob", "JobScheduler OnDownloadStarted " + taskId);
    }

    @Override
    public void OnDownloadPaused(long taskId) {
        Log.d("DownloadFileJob", "JobScheduler OnDownloadPaused " + taskId);
    }

    @Override
    public void onDownloadProcess(long taskId, double percent, long downloadedLength) {
        Log.d("DownloadFileJob", "JobScheduler onDownloadProcess " + taskId);
    }

    @Override
    public void OnDownloadFinished(long taskId) {
        Log.d("DownloadFileJob", "JobScheduler OnDownloadFinished " + taskId);
    }

    @Override
    public void OnDownloadRebuildStart(long taskId) {
        Log.d("DownloadFileJob", "JobScheduler OnDownloadRebuildStart " + taskId);
    }

    @Override
    public void OnDownloadRebuildFinished(long taskId) {
        Log.d("DownloadFileJob", "JobScheduler OnDownloadRebuildFinished " + taskId);
    }

    @Override
    public void OnDownloadCompleted(long taskId) {
        Log.d("DownloadFileJob", "JobScheduler OnDownloadCompleted " + taskId);
    }

    @Override
    public void connectionLost(long taskId) {
        Log.d("DownloadFileJob", "JobScheduler connectionLost " + taskId);
    }

    public int scheduleDownloadJob(DownloadManagerPro downloadManagerPro, int queuePrioritization, int taskId, int startExecutionWindow, int endExecutionWindow) {
        this.downloadManagerPro = downloadManagerPro;
        this.queuePrioritization = queuePrioritization;
        this.taskId = taskId;
        this.startExecutionWindow = startExecutionWindow;
        this.endExecutionWindow = endExecutionWindow;
        int jobID = new JobRequest.Builder(DownloadFileJob.TAG)
                .setExecutionWindow(startExecutionWindow, endExecutionWindow)
                .setBackoffCriteria(3000, JobRequest.BackoffPolicy.EXPONENTIAL)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
        Log.d("DownloadFileJob", "scheduleDownloadJob jobID: " + jobID);
        currentJobID = jobID;
        return jobID;
    }
}
