package com.omiplekevin.android.f45testbench.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.omiplekevin.android.f45testbench.DualViewPager;
import com.omiplekevin.android.f45testbench.R;
import com.omiplekevin.android.f45testbench.dao.CustomTrackingModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by OMIPLEKEVIN on 029 Nov 29, 2016.
 */

public class WeekFragment extends Fragment {

    public static final String BUNDLE_TRACKING_ITEMS = "tracking_item";
    public static final String BUNDLE_TRACKING_WEEK_ASSIGN = "week_assign";
    private List<CustomTrackingModel> itemModels;
    public static volatile DualViewPager.WeekDayInteractionListener listener;

    public static WeekFragment getInstance(String tag, ArrayList<CustomTrackingModel> dates, DualViewPager.WeekDayInteractionListener listener) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_TRACKING_ITEMS, dates);
        bundle.putString(BUNDLE_TRACKING_WEEK_ASSIGN, tag);
        WeekFragment fragment = new WeekFragment();
        WeekFragment.listener = listener;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemModels = (ArrayList<CustomTrackingModel>) getArguments().getSerializable(BUNDLE_TRACKING_ITEMS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        LinearLayout rootView = (LinearLayout) view.findViewById(R.id.parent);
        for (final CustomTrackingModel date : itemModels) {
            View dateView = inflater.inflate(R.layout.week_day_item, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1F);
            dateView.setLayoutParams(params);
            //add to parent layout
            rootView.addView(dateView);
            rootView.invalidate();

            date.setView(dateView);

            //interpret and assign to text view
            Date d = new Date(date.timestamp);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            date.getDayButtonView().setText(String.valueOf(c.get(Calendar.DATE)));
            date.getDayButtonView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date.isSelected = true;
                    if (listener != null) {
                        listener.onWeekdaySelected(date);
                    }
                }
            });
            date.getDayLabelView().setText(getDayLabel(c));
//            updateButtonViews(date, false);
            if (date.isSelected) {
                if (date.isCurrentTrack) {
                    date.getDayButtonView().setBackgroundResource(R.drawable.bg_circle_currentdate);
                } else {
                    date.getDayButtonView().setBackgroundResource(R.drawable.bg_circle_noncurrentdate);
                }
                date.getDayButtonView().setEnabled(true);
                date.getDayButtonView().setTextColor(Color.WHITE);
                date.getDayButtonView().invalidate();
            } else {
                if (date.isInclusiveDate || date.isStartDate || date.isEndDate) {
                    date.getDayButtonView().setEnabled(true);
                    date.getDayButtonView().setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.color.transparent));
                    date.getDayButtonView().setTextColor(Color.BLACK);
                    date.getDayButtonView().invalidate();
                } else {
                    date.getDayButtonView().setEnabled(false);
                    date.getDayButtonView().setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), android.R.color.transparent));
                    date.getDayButtonView().setTextColor(Color.parseColor("#DDDDDD"));
                    date.getDayButtonView().invalidate();
                }
            }
        }

        return view;
    }

    public int getWeekAssigned() {
        try {
            return Integer.parseInt(getArguments().getString(BUNDLE_TRACKING_WEEK_ASSIGN));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getDayLabel(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY://2
                return "M";
            case Calendar.TUESDAY://3
                return "T";
            case Calendar.WEDNESDAY://4
                return "W";
            case Calendar.THURSDAY://5
                return "Th";
            case Calendar.FRIDAY://6
                return "F";
            case Calendar.SATURDAY://7
                return "Sa";
            case Calendar.SUNDAY://1
                return "Su";
        }
        return "";
    }
}
