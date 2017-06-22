package com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 12, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class Subject {

    private List<Observer> downloadObservers = new ArrayList<>();

    public void attach(Observer observer) {
        if (downloadObservers == null) {
            downloadObservers = new ArrayList<>();
        }

        if (observer == null) {
            Log.d("Subject", "attach: observer is null");
            return;
        }

        downloadObservers.add(observer);
    }

    public boolean detach(Observer observer) {
        //lazy IFs
        if (downloadObservers == null) {
            return false;
        }

        if (observer == null) {
            return false;
        }
        return downloadObservers.remove(observer);
    }

    public void updateObservers(/* insert states here*/) {
        for (Observer observer : downloadObservers) {
            observer.downloadState();
        }
    }
}
