package com.omiplekevin.android.f45testbench.uicomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omiplekevin.android.f45testbench.R;
import com.omiplekevin.android.f45testbench.interfaces.OnScheduleUpdateListener;

public class F45ScheduleStrip extends LinearLayout implements OnScheduleUpdateListener {

    LinearLayout scheduleGroupMorning, scheduleGroupAfternoon, scheduleGroupEvening;
    Context context;
    public int morningDataCount = 0;
    public int afternoonDataCount = 0;
    public int eveningDataCount = 0;


    public F45ScheduleStrip(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_schedule_strip_entry, this, true);

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        this.context = context;

        initViews();
    }

    public F45ScheduleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void initViews() {
        scheduleGroupMorning = (LinearLayout) findViewById(R.id.scheduleGroupMorning);
        scheduleGroupAfternoon = (LinearLayout) findViewById(R.id.scheduleGroupAfternoon);
        scheduleGroupEvening = (LinearLayout) findViewById(R.id.scheduleGroupEvening);
    }

    @Override
    public void onMorningListSizeChange(int newSize) {
        if (scheduleGroupMorning != null) {
            Log.e("Strip", "child count:" + scheduleGroupMorning.getChildCount());
            if (scheduleGroupMorning.getChildCount() > 0) {
                Log.e("Strip", "removing " + scheduleGroupMorning.getChildCount() + " views");
                scheduleGroupMorning.removeAllViews();
            }
            Log.e("Strip", "adding " + newSize + " views");
            for (int i = 0; i < newSize; i++) {
                TextView textView = new TextView(context);
                textView.setText("COUNTING " + newSize);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                scheduleGroupMorning.addView(textView, params);
                scheduleGroupMorning.invalidate();
            }
            morningDataCount = newSize;
        }
    }

    @Override
    public void onAfternoonListSizeChange(int newSize) {
        if (scheduleGroupAfternoon != null) {
            Log.e("Strip", "child count:" + scheduleGroupAfternoon.getChildCount());
            if (scheduleGroupAfternoon.getChildCount() > 0) {
                Log.e("Strip", "removing " + scheduleGroupAfternoon.getChildCount() + " views");
                scheduleGroupAfternoon.removeAllViews();
            }
            Log.e("Strip", "adding " + newSize + " views");
            for (int i = 0; i < newSize; i++) {
                TextView textView = new TextView(context);
                textView.setText("COUNTING " + newSize);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                scheduleGroupAfternoon.addView(textView, params);
                scheduleGroupAfternoon.invalidate();
            }
            afternoonDataCount = newSize;
        }
    }

    @Override
    public void onEveningListSizeChange(int newSize) {
        if (scheduleGroupEvening != null) {
            Log.e("Strip", "child count:" + scheduleGroupEvening.getChildCount());
            if (scheduleGroupEvening.getChildCount() > 0) {
                Log.e("Strip", "removing " + scheduleGroupEvening.getChildCount() + " views");
                scheduleGroupEvening.removeAllViews();
            }
            Log.e("Strip", "adding " + newSize + " views");
            for (int i = 0; i < newSize; i++) {
                TextView textView = new TextView(context);
                textView.setText("COUNTING " + newSize);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                scheduleGroupEvening.addView(textView, params);
                scheduleGroupEvening.invalidate();
            }
            eveningDataCount = newSize;
        }
    }

    @Override
    public int getMorningEntryCount() {
        return morningDataCount;
    }

    @Override
    public int getAfternoonEntryCount() {
        return afternoonDataCount;
    }

    @Override
    public int getEveningEntryCount() {
        return eveningDataCount;
    }

    @Override
    public void doGlobalTableBalance(int maxMorning, int maxAfternoon, int maxEvening) {
        //mornings
        if (scheduleGroupMorning != null) {
            padTables(scheduleGroupMorning, maxMorning);
        }

        //afternoon
        if (scheduleGroupAfternoon != null) {
            padTables(scheduleGroupAfternoon, maxAfternoon);
        }

        //evening
        if (scheduleGroupEvening != null) {
            padTables(scheduleGroupEvening, maxEvening);
        }

    }

    private void padTables(LinearLayout view, int maxValue) {
        Log.e("Strip", "padding consideration value: " + maxValue + " / " + view.getChildCount());
        //lets pad it!
        int neededPads = (maxValue - view.getChildCount());
        Log.e("Strip", "adding " + neededPads + " pads");
        if (neededPads > 0) {
            for (int i = 0; i < neededPads; i++) {
                TextView text = new TextView(context);
                text.setText("");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                view.addView(text, params);
                view.invalidate();
            }
        } else if (neededPads < 0) {
            for (int i = 0; i < Math.abs(neededPads); i++) {
                Log.e("Strip", "removing " + view.getChildCount());
                view.removeViewAt(view.getChildCount()-1);
            }
        }
    }
}
