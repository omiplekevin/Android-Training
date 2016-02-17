package com.omiplekevin.autoscrollingpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by OMIPLEKEVIN on 29/11/2015.
 */
public class UpcomingViewPagerAdapter extends FragmentPagerAdapter {

    List<UpcomingFragmentContent> listOfUpComing;

    public UpcomingViewPagerAdapter(FragmentManager fragmentManager, List<UpcomingFragmentContent> listOfUpComing) {
        super(fragmentManager);
        this.listOfUpComing = listOfUpComing;
    }

    @Override
    public int getCount() {
        return this.listOfUpComing.size();
    }

    @Override
    public Fragment getItem(int i) {
        return listOfUpComing.get(i);
    }


}
