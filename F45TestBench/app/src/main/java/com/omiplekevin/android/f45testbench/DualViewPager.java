package com.omiplekevin.android.f45testbench;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.omiplekevin.android.f45testbench.adapters.TrackingPagerAdapter;
import com.omiplekevin.android.f45testbench.adapters.WeekPagerAdapter;
import com.omiplekevin.android.f45testbench.dao.CustomTrackingModel;
import com.omiplekevin.android.f45testbench.fragment.WeekFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by OMIPLEKEVIN on 029 Nov 29, 2016.
 */

public class DualViewPager extends FragmentActivity {

    private final String TAG = "DualViewPager";
    private ViewPager weekPager;
    private ViewPager trackingPager;
    private LinkedHashMap<Integer, List<CustomTrackingModel>> listOfInclusiveDates;
    private List<CustomTrackingModel> trackingModelList;
    private Calendar sysCal = Calendar.getInstance(Locale.getDefault());
    private WeekPagerAdapter weekPagerAdapter;
    private TrackingPagerAdapter trackingPagerAdapter;

    private static WeekDayInteractionListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dualviewpager);

        weekPager = (ViewPager) findViewById(R.id.weekPager);
        trackingPager = (ViewPager) findViewById(R.id.trackingPager);

        //set your start date here...
        Date startDate = new Date();
        startDate.setTime(1479225601000L);

        //set your end date here...
        Date endDate = new Date();
        endDate.setTime(1485792001000L);

        //set system referred time
        sysCal.setTime(new Date(System.currentTimeMillis()));

        //STEP - 1: create the span of dates
        listOfInclusiveDates = createWeekList(startDate, endDate);
        //STEP - 2: fill in the lacking dates to pad a week with incomplete days
        fillInDatePadding();
        //STEP - 3: setDefaults for every week to select MONDAY
        setupDefaults();

        //deep listener from WeekFragment date items
        listener = new WeekDayInteractionListener() {
            @Override
            public void onWeekdaySelected(CustomTrackingModel item) {
                Log.d("ViewPagerChange", "onWeekdaySelected");

                Calendar sCal = Calendar.getInstance();
                sCal.setTime(new Date(item.timestamp));
                updateInclusiveDates(item);
                weekPagerAdapter.notifyDataSetChanged();
                trackingPager.setCurrentItem(getActiveTrackingFromWeekPager(), true);
            }
        };

        //setup the week pager
        setUpWeekPager(listener);
        //setup the tracking pager
        setUpTrackingPager();

    }

    public interface WeekDayInteractionListener {
        void onWeekdaySelected(CustomTrackingModel item);
    }

    private void setUpWeekPager(WeekDayInteractionListener listener) {
        Log.d("ViewPagerChange", "setUpWeekPager");
        weekPagerAdapter = new WeekPagerAdapter(getSupportFragmentManager(), listOfInclusiveDates, listener);

        if (weekPager != null) {
            if (weekPagerAdapter != null) {
                weekPager.setAdapter(weekPagerAdapter);
                weekPager.setCurrentItem(weekPagerAdapter.getActiveIndex());
            } else {
                Log.e(TAG, "weekPagerAdapter is null");
            }
            weekPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    trackingPager.setCurrentItem(getActiveTrackingFromWeekPager(), true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            Log.e(TAG, "weekPager is null");
        }
    }

    private void setUpTrackingPager() {
        Log.d("ViewPagerChange", "setUpTrackingPager");
        trackingPagerAdapter = new TrackingPagerAdapter(getSupportFragmentManager(), trackingModelList);
        if (trackingPager != null) {
            trackingPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    updateInclusiveDates(trackingModelList.get(position));
                    weekPagerAdapter.notifyDataSetChanged();
                    if (((WeekFragment) weekPagerAdapter.getItem(weekPager.getCurrentItem())).getWeekAssigned() != trackingModelList.get(position).weekOfYear) {
                        List<Object> weeks = new ArrayList<>();
                        Collections.addAll(weeks, listOfInclusiveDates.keySet().toArray());
                        weekPager.setCurrentItem(weeks.indexOf(trackingModelList.get(position).weekOfYear));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (trackingPagerAdapter != null) {
                trackingPager.setAdapter(trackingPagerAdapter);
            } else {
                Log.e(TAG, "trackingPagerAdapter is null");
            }
            trackingPager.setAdapter(trackingPagerAdapter);
            trackingPager.setCurrentItem(getActiveTrackingFromWeekPager());
        } else {
            Log.e(TAG, "trackingPager is null");
        }
    }

    private LinkedHashMap<Integer, List<CustomTrackingModel>> createWeekList(Date startDate, Date endDate) {
        LinkedHashMap<Integer, List<CustomTrackingModel>> mapping = new LinkedHashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        //loop until end date
        while (calendar.getTime().before(endDate)) {
            //get the CALENDAR Calendar.WEEK_OF_YEAR
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

            //get the list of Dates on that Calendar.WEEK_OF_YEAR
            List<CustomTrackingModel> dates = mapping.get(weekOfYear);
            //check if the date list is null
            if (dates == null) {
                //initialize if null/empty
                dates = new ArrayList<>();
            }
            //get the current incremental date
            Date result = calendar.getTime();
            boolean isStartDate = (calendar.getTime().getTime() == startDate.getTime());
            boolean isEndDate = (calendar.getTime().getTime() == endDate.getTime());
            boolean isInclusiveDate = (result.after(startDate) && result.before(endDate)) || isStartDate;

            //get current SYSTEM DATE and compare if current loop date is equal to SYSTEM DATE
            String systemDate = String.valueOf(sysCal.get(Calendar.YEAR)) +
                    String.valueOf(sysCal.get(Calendar.MONTH)) +
                    (sysCal.get(Calendar.DATE) < 10 ? ("0" + String.valueOf(sysCal.get(Calendar.DATE))) : String.valueOf(sysCal.get(Calendar.DATE)));
            String loopDate = String.valueOf(calendar.get(Calendar.YEAR)) +
                    String.valueOf(calendar.get(Calendar.MONTH)) +
                    (calendar.get(Calendar.DATE) < 10 ? ("0" + String.valueOf(calendar.get(Calendar.DATE))) : String.valueOf(calendar.get(Calendar.DATE)));
            boolean isCurrentTracking = loopDate.equals(systemDate);

            //create CustomTrackingModel Serializable to add to the list
            CustomTrackingModel model = new CustomTrackingModel(
                    result.getTime(),
                    weekOfYear,
                    "content: WoY " + weekOfYear + ", " + result.getTime(),
                    isStartDate,
                    isEndDate,
                    isInclusiveDate || isStartDate || isEndDate,
                    isCurrentTracking);
            model.isSelected = isCurrentTracking;


            if (trackingModelList == null) {
                trackingModelList = new ArrayList<>();
            }

            //add to independent tracking list
            trackingModelList.add(model);
            //add it to the list of dates for WEEK_OF_YEAR
            dates.add(model);
            //add it to the mapping
            mapping.put(weekOfYear, dates);
            //increment CALENDAR
            calendar.add(Calendar.DATE, 1);
        }

        return mapping;
    }

    private void fillInDatePadding() {
        for (Map.Entry<Integer, List<CustomTrackingModel>> date : listOfInclusiveDates.entrySet()) {
            List<CustomTrackingModel> datesOfWeek = date.getValue();
            if (datesOfWeek.size() < 7) {
                //get your current head of the week entries, set as reference point
                CustomTrackingModel refModel = datesOfWeek.get(0);
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(refModel.timestamp));
                //get the references' index - this will tell you where to map the head and its trailing entries
                int refCurrentHeadNdx = cal.get(Calendar.DAY_OF_WEEK) - 1; //[0,1,2...] |n-1|
                //define a fix-sized array[7] S,M,T,W,Th,F,Sa
                CustomTrackingModel[] a = new CustomTrackingModel[7];
                //duplicate to fix-sized array so we can get locations that needs padding
                for (CustomTrackingModel model : datesOfWeek) {
                    cal.setTime(new Date(model.timestamp));
                    //adjust indexing by 2 places to ROTATE VALUES of Calendar API
                    //refer to https://developer.android.com/reference/java/util/Calendar.html#DAY_OF_WEEK
                    int assignedIndex = cal.get(Calendar.DAY_OF_WEEK) - 2;
                    if (assignedIndex < 0) {
                        assignedIndex = assignedIndex + 7;
                    }
                    a[assignedIndex] = model; //[0,1,2...] |n-1|
                }
                //fill in paddings
                for (int i = 0; i < 7; i++) {
                    if (a[i] == null) {
                        //reset reference
                        cal.setTime(new Date(refModel.timestamp));
                        //targetIndex - referenceIndex = additional offset to reference date
                        cal.add(Calendar.DATE, (i - refCurrentHeadNdx) + 1);
                        a[i] = new CustomTrackingModel(
                                cal.getTime().getTime(),
                                cal.get(Calendar.WEEK_OF_YEAR),
                                "padding...",
                                false,
                                false,
                                false,
                                false);
                    }
                }
                //primitive to List<T>
                List<CustomTrackingModel> newList = new ArrayList<>();
                newList.addAll(Arrays.asList(a));
                //set new padded Date List
                date.setValue(newList);
            }
        }
    }

    private void updateInclusiveDates(CustomTrackingModel refItem) {
        for (Map.Entry<Integer, List<CustomTrackingModel>> parentItem : listOfInclusiveDates.entrySet()) {
            for (CustomTrackingModel innerItem : parentItem.getValue()) {
                if (refItem.weekOfYear == innerItem.weekOfYear) {
                    if (refItem.timestamp == innerItem.timestamp) {
                        innerItem.isSelected = true;
                    } else {
                        innerItem.isSelected = false;
                    }
                }
            }
        }
    }

    private void setupDefaults() {
        for (Map.Entry<Integer, List<CustomTrackingModel>> entry : listOfInclusiveDates.entrySet()) {
            boolean hasCurrentTrack = false;
            //overhead lookup - check if current week has the current tracking date
            for (CustomTrackingModel item : entry.getValue()) {
                if (item.isCurrentTrack) {
                    hasCurrentTrack = true;
                }
            }

            if (!hasCurrentTrack) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    CustomTrackingModel item = entry.getValue().get(i);
                    if (item.isInclusiveDate && (i == 0 || item.isStartDate)) {
                        item.isSelected = true;
                    }
                }
            }
        }
    }

    private int getActiveTrackingFromWeekPager() {
        List<CustomTrackingModel> item = listOfInclusiveDates.get(listOfInclusiveDates.keySet().toArray()[weekPager.getCurrentItem()]);
        for (CustomTrackingModel m : item) {
            if (m != null) {
                if (m.isSelected) {
                    return trackingModelList.indexOf(m);
                }
            } else {
                Log.e("ViewPagerChange", "m variable is null!");
            }
        }

        return 0;
    }
}
