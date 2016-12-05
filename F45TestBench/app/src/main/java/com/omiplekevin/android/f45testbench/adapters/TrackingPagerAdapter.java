package com.omiplekevin.android.f45testbench.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.omiplekevin.android.f45testbench.dao.CustomTrackingModel;
import com.omiplekevin.android.f45testbench.fragment.TrackingWeekFragment;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by OMIPLEKEVIN on 005 Dec 05, 2016.
 */

public class TrackingPagerAdapter extends FragmentStatePagerAdapter {

    private List<CustomTrackingModel> trackingList;

    public TrackingPagerAdapter(FragmentManager fragmentManager, List<CustomTrackingModel> trackingList){
        super(fragmentManager);
        this.trackingList = trackingList;
    }

    @Override
    public Fragment getItem(int position) {
        CustomTrackingModel item = this.trackingList.get(position);
        return TrackingWeekFragment.getInstance(item);
    }

    @Override
    public int getCount() {
        return trackingList.size();
    }
}
