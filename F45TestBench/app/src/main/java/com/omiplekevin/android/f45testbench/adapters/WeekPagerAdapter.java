package com.omiplekevin.android.f45testbench.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.omiplekevin.android.f45testbench.DualViewPager;
import com.omiplekevin.android.f45testbench.dao.CustomTrackingModel;
import com.omiplekevin.android.f45testbench.fragment.WeekFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OMIPLEKEVIN on 029 Nov 29, 2016.
 */

public class WeekPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fragmentManager;
    private LinkedHashMap<Integer, List<CustomTrackingModel>> listOfInclusiveDates;
    private DualViewPager.WeekDayInteractionListener listener;

    public WeekPagerAdapter(FragmentManager fragmentManager, LinkedHashMap<Integer, List<CustomTrackingModel>> listOfInclusiveDates, DualViewPager.WeekDayInteractionListener listener) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.listener = listener;
        this.listOfInclusiveDates = listOfInclusiveDates;
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<CustomTrackingModel> items = new ArrayList<>();
        items.addAll(getElementByIndex(listOfInclusiveDates, position));
        return WeekFragment.getInstance(items, listener);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return listOfInclusiveDates.size();
    }

    private List<CustomTrackingModel> getElementByIndex(LinkedHashMap<Integer, List<CustomTrackingModel>> map, int position) {
        return map.get((map.keySet().toArray())[position]);
    }

    public int getActiveIndex() {
        int ndx = 0;
        for (Map.Entry<Integer, List<CustomTrackingModel>> parentItem : listOfInclusiveDates.entrySet()) {
            for (CustomTrackingModel innerItem : parentItem.getValue()) {
                if (innerItem.isCurrentTrack) {
                    return ndx;
                }
            }
            ndx++;
        }
        return 0;
    }
}
