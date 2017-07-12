package com.android.project.downloadcontroller;

import android.app.Application;

import com.android.project.downloadcontroller_lib.controller.DownloadController;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   July 07, 2017<br/>
 * IN CLASS:        com.android.project.downloadcontroller<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class App extends Application {

    private DownloadController downloadController;

    @Override
    public void onCreate() {
        super.onCreate();
        downloadController = DownloadController.getInstance(this);
    }

    public DownloadController getDownloadController() {
        return downloadController;
    }
}
