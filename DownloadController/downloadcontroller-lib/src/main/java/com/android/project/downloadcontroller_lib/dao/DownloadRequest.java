package com.android.project.downloadcontroller_lib.dao;

import android.app.DownloadManager;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   July 07, 2017<br/>
 * IN CLASS:        com.android.project.downloadcontroller_lib.dao<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class DownloadRequest {

    public long downloadID = -1;

    public String url = "";

    public String savePath = "";

    public String saveName = "";

    public String fileExtension = "";

    public long fileSize = 0L;

    public double percentage = 0.0d;

    public int state = DownloadManager.STATUS_PENDING;

    public String reason = "";

    public DownloadRequest(long downloadID, String url, String savePath, String saveName, String fileExtension) {
        this.downloadID = downloadID;
        this.url = url;
        this.savePath = savePath;
        this.saveName = saveName;
        this.fileExtension = fileExtension;
    }

    public DownloadRequest(long downloadID, String url, String savePath, String saveName, String fileExtension, int state, String reason) {
        this.downloadID = downloadID;
        this.url = url;
        this.savePath = savePath;
        this.saveName = saveName;
        this.fileExtension = fileExtension;
        this.state = state;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "DownloadRequest: " + downloadID + " | " + url + " | " + savePath + " | " + saveName + " | " + fileExtension;
    }

    public void setDownloadPercentage(double percentage) {
        this.percentage = percentage;
    }


    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getDownloadID() {
        return downloadID;
    }

    public void setDownloadID(long downloadID) {
        this.downloadID = downloadID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

}
