package com.omiplekevin.android.f45testbench.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omiplekevin.android.f45testbench.F45PowerNewSkin;
import com.omiplekevin.android.f45testbench.R;
import com.omiplekevin.android.f45testbench.interfaces.Observer;

/**
 * Created by OMIPLEKEVIN on 028 Mar 28, 2017.
 */

public class ControllerFragment extends Observer{

    private View view;

    public ControllerFragment() {}

    public ControllerFragment(F45PowerNewSkin.Updates updates) {
        this.updates = updates;
        this.updates.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_controller, container, false);
        }
        return view;
    }

    @Override
    public void update(String string) {
        Log.d(getClass().getSimpleName(), string);
    }
}
