package com.android.omiplekevin.daggerdemo;

import android.util.Log;

public class FrontCameraSet {

    private static final String TAG = "FrontCameraSet";

    private final CameraPackage cameraPackage;

    public FrontCameraSet(CameraPackage cameraPackage) {
        this.cameraPackage = cameraPackage;
    }

    public boolean shotWithWideAngleLens() {
        if (cameraPackage.wideAvailable()) {
            Log.d(TAG, "shotWithWideAngleLens");
            this.cameraPackage.shotWithWideAngle();
            return true;
        } else {
            Log.d(TAG, "shotWithWideAngleLens: no available wide angle lens");
            return false;
        }
    }

    public boolean shotWithStandardLens() {
        if (cameraPackage.standardAvailable()) {
            Log.d(TAG, "shotWithStandardLens");
            this.cameraPackage.shotWithWideAngle();
            return true;
        } else {
            Log.d(TAG, "shotWithStandardLens: no available wide angle lens");
            return false;
        }
    }
}
