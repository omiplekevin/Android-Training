package com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper;

import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums.TaskStates;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.report.ReportStructure;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 13, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class DownloadEvent {

    public long taskId = -1;
    public long receivedBytes = 0L;
    public double percentage = 0F;
    public double fileSize = 0F;
    public int state = 0;
    public boolean requiresView;
    public String source;
    public String filename;
    public String fileExtension;
    public String savePath;
    public boolean highpriority;
    public boolean overwrite;
    public boolean retryState = false;
    public int countdown;

    public DownloadEvent() {

    }

    public DownloadEvent(long taskId, int state, double percentage, long receivedBytes) {
        this.taskId = taskId;
        this.state = state;
        this.percentage = percentage;
        this.receivedBytes = receivedBytes;
    }

    public DownloadEvent(long taskId, int state, boolean requiresView) {
        this.taskId = taskId;
        this.state = state;
        if (state == TaskStates.DOWNLOAD_FINISHED) {
            this.percentage = 100F;
        }
    }

    public DownloadEvent(String source, boolean overwrite, boolean highpriority) {
        this.source = source;
        this.overwrite = overwrite;
        this.highpriority = highpriority;
    }

    public DownloadEvent(long taskId, int state, int countdown) {
        this.taskId = taskId;
        this.state = state;
        this.countdown = countdown;
    }

    public DownloadEvent(ReportStructure structure) {
        this.taskId = structure.id;
        this.filename = structure.name;
        this.state = structure.state;
        this.source = structure.url;
        this.fileSize = structure.fileSize;
        this.fileExtension = structure.type;
        this.percentage = structure.percent;
        this.fileSize = structure.downloadLength;
        this.savePath = structure.saveAddress;
        this.highpriority = structure.priority;
    }

    @Override
    public String toString() {
        return "taskId: " + taskId + " \n" +
                "receivedBytes: " + receivedBytes + " \n" +
                "percentage: " + percentage + " \n" +
                "state: " + state + " \n" +
                "requiresView: " + requiresView + "\n" +
                "source: " + source + "\n" +
                "filename: " + filename + "\n" +
                "fileExtension: " + fileExtension + "\n" +
                "savePath: " + savePath + "\n" +
                "highpriority: " + highpriority + "\n" +
                "overwrite: " + overwrite;
    }
}
