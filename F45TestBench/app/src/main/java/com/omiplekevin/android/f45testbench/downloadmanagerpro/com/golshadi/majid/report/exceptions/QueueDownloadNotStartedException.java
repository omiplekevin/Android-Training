package com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.report.exceptions;

/**
 * Created by Majid Golshadi on 4/23/2014.
 */
public class QueueDownloadNotStartedException extends IllegalStateException {

    public QueueDownloadNotStartedException(){
        super("Queue Download not started yet");
    }
}
