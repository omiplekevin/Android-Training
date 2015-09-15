package com.android.samplevideoplayback;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

/**
 * Created by OMIPLEKEVIN on 09/09/2015.
 */
public class SampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);

        TextView textView = (TextView)view.findViewById(R.id.sampleText);
        textView.setText("OK?");
        textView.setTextSize(30F);

        return view;
    }
}
