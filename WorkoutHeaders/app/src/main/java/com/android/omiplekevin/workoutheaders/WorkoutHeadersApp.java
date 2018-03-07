package com.android.omiplekevin.workoutheaders;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;

import static android.content.ContentValues.TAG;

/**
 * Created by OMIPLEKEVIN on March 05, 2018.
 * WorkoutHeaders
 * com.android.omiplekevin.workoutheaders
 */

public class WorkoutHeadersApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeStetho();
    }

    private void initializeStetho() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "initialize Stetho");
            Stetho.initializeWithDefaults(this);
            /*Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build();*/
        } else {
            Log.d(TAG, "Stetho debugging disabled");
        }
    }

}
