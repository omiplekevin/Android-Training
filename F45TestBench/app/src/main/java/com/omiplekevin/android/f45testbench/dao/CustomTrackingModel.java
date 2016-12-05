package com.omiplekevin.android.f45testbench.dao;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.omiplekevin.android.f45testbench.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by OMIPLEKEVIN on 029 Nov 29, 2016.
 */

public class CustomTrackingModel implements Serializable {
    public long timestamp;
    public int weekOfYear;
    public String content;
    public boolean isStartDate;
    public boolean isEndDate;
    public boolean isInclusiveDate;
    public boolean isSelected;
    public boolean isCurrentTrack;
    private View view;
    private ViewHolder viewHolder;

    // Constructor
    public CustomTrackingModel(long timestamp, int weekOfYear, String content, boolean isStartDate, boolean isEndDate, boolean isInclusiveDate, boolean isCurrentTrack) {
        this.timestamp = timestamp;
        this.weekOfYear = weekOfYear;
        this.content = content;
        this.isStartDate = isStartDate;
        this.isEndDate = isEndDate;
        this.isInclusiveDate = isInclusiveDate;
        this.isCurrentTrack = isCurrentTrack;
    }

    public int getMonth(){
        if (this.timestamp != 0L) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(this.timestamp));
            return cal.get(Calendar.MONTH);
        } else {
            return -1;
        }
    }

    public void setView(View view) {
        this.view = view;
        this.viewHolder = new ViewHolder();
        this.viewHolder.dateBtn = (Button) this.view.findViewById(R.id.dayValue);
        this.viewHolder.dateLabel = (TextView) this.view.findViewById(R.id.dayLabel);
    }

    public Button getDayButtonView(){
        if (this.viewHolder != null) {
            return this.viewHolder.dateBtn;
        } else {
            return null;
        }
    }

    public TextView getDayLabelView(){
        if (this.viewHolder != null) {
            return this.viewHolder.dateLabel;
        } else {
            return null;
        }
    }

    public View getView(){
        return this.view;
    }

    private class ViewHolder {
        TextView dateLabel;
        Button dateBtn;
    }
}