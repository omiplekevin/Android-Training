package com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums;

/**
 * Created by Majid Golshadi on 4/15/2014.
 */
public class TaskStates {

    public static final int INIT        = 0;
    public static final int READY       = 1;
    public static final int DOWNLOADING = 2;
    public static final int PAUSED      = 3;
    public static final int DOWNLOAD_FINISHED = 4;
    public static final int END         = 5;

    public static String getTaskState(int state) {
        switch (state) {
            case INIT:
                return "INIT";
            case READY:
                return "READY";
            case DOWNLOADING:
                return "DOWNLOADING";
            case PAUSED:
                return "PAUSED";
            case DOWNLOAD_FINISHED:
                return "DOWNLOAD_FINISHED";
            case END:
                return "END";
            default:
                return "UNKNOWN";
        }
    }

}
