package com.omiplekevin.android.f45testbench.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omiplekevin.android.f45testbench.R;
import com.omiplekevin.android.f45testbench.dao.CustomTrackingModel;

/**
 * Created by OMIPLEKEVIN on 005 Dec 05, 2016.
 */

public class TrackingWeekFragment extends Fragment {

    private static final String BUNDLE_TRACKING_ITEMS = "tracking_items";
    private CustomTrackingModel trackingItem;

    public static TrackingWeekFragment getInstance(CustomTrackingModel item){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_TRACKING_ITEMS, item);
        TrackingWeekFragment fragment = new TrackingWeekFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.trackingItem = (CustomTrackingModel) getArguments().getSerializable(BUNDLE_TRACKING_ITEMS);
        Log.d("TrackingWeekFragment", trackingItem.content);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking_item, container, false);
        TextView text1 = (TextView) view.findViewById(R.id.field1);
        TextView text2 = (TextView) view.findViewById(R.id.field2);

        text1.setText(this.trackingItem.content);
        text2.setText(String.valueOf(this.trackingItem.weekOfYear));
        return view;
    }
}
