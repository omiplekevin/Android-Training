package com.omiplekevin.android.f45testbench;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.omiplekevin.android.f45testbench.adapters.F45ScheduleTableManager;
import com.omiplekevin.android.f45testbench.uicomponent.F45ScheduleStrip;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTableActivity extends Activity {

    LinearLayout rootLayout;
    F45ScheduleTableManager f45ScheduleTableManager;

    //dummy
    int ndx = 0;
    String[] days = new String[]{"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cell);

        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        f45ScheduleTableManager = new F45ScheduleTableManager();
    }

    public void addStrip(View view) {
        F45ScheduleStrip f45ScheduleStrip = new F45ScheduleStrip(this);
        f45ScheduleTableManager.attachSubscriber(days[ndx++], f45ScheduleStrip);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        rootLayout.addView(f45ScheduleStrip, params);
        rootLayout.invalidate();
    }

    public void addMornings(View view) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        f45ScheduleTableManager.notifySubscribers(days[new Random().nextInt(7)], "morning");
                        f45ScheduleTableManager.notifySubscribers(days[new Random().nextInt(7)], "afternoon");
                        f45ScheduleTableManager.notifySubscribers(days[new Random().nextInt(7)], "evening");
                        f45ScheduleTableManager.balanceTables();
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 250, 250);
    }
}
