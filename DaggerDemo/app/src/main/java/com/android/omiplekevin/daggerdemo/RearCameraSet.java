package com.android.omiplekevin.daggerdemo;

import android.util.Log;

public class RearCameraSet {

    private static final String TAG = "RearCameraSet";

    private CameraPackage cameraPackage;

    public RearCameraSet(CameraPackage cameraPackage) {
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
//        Log.d(TAG, "shotWithStandardLens: calling camera package to use standard lens");
        if (cameraPackage.standardAvailable()) {
            Log.d(TAG, "shotWithStandardLens");
            this.cameraPackage.shotWithStandardLens();
            return true;
        } else {
            Log.d(TAG, "shotWithStandardLens: no available standard angle lens");
            return false;
        }
    }
}
