package com.android.omiplekevin.daggerdemo;

import android.util.Log;

public class CameraPackage {

    private static final String TAG = "CameraPackage";

    private boolean wide;
    private boolean standard;

    public CameraPackage(boolean wide, boolean standard) {
        this.wide = wide;
        this.standard = standard;
    }

    public void shotWithWideAngle() {
        if (this.wide) {
            Log.d(TAG, "shot with wide angle camera");
        } else {
            Log.d(TAG, "no available wide angle camera");
        }
    }

    public void shotWithStandardLens() {
        if (this.standard) {
            Log.d(TAG, "shot with standard angle camera");
        } else {
            Log.d(TAG, "no available standard angle camera");
        }
    }

    public boolean wideAvailable() {
        return this.wide;
    }

    public boolean standardAvailable() {
        return this.standard;
    }
}
