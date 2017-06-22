package com.omiplekevin.android.f45testbench.interfaces;

import android.support.v4.app.Fragment;

import com.omiplekevin.android.f45testbench.F45PowerNewSkin;

/**
 * Created by OMIPLEKEVIN on 028 Mar 28, 2017.
 */

public abstract class Observer extends Fragment{
    protected F45PowerNewSkin.Updates updates;
    public abstract void update(String string);
}
