package com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper;

import android.app.Fragment;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 12, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public abstract class Observer extends Fragment {
    protected Subject subject;
    public abstract void downloadState();
}
