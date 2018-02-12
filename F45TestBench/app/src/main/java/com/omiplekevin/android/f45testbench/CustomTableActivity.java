package com.omiplekevin.android.f45testbench;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.omiplekevin.android.f45testbench.adapters.F45ScheduleTableManager;
import com.omiplekevin.android.f45testbench.uicomponent.F45ScheduleStrip;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CustomTableActivity extends Activity {

    private LinearLayout rootLayout;
    private F45ScheduleTableManager f45ScheduleTableManager;
    private final int MAX_TAB_COUNT = 7;

    private TimerTask task;
    private Timer timer;

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
        if (ndx < MAX_TAB_COUNT) {
            F45ScheduleStrip f45ScheduleStrip = new F45ScheduleStrip(this);
            f45ScheduleTableManager.attachSubscriber(days[ndx++], f45ScheduleStrip);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            rootLayout.addView(f45ScheduleStrip, params);
            rootLayout.invalidate();
        } else {
            Toast.makeText(this, "Max tab count!", Toast.LENGTH_SHORT).show();
        }
    }

    public void startRandom(View view) {
        view.setEnabled(false);
        findViewById(R.id.stopRand).setEnabled(true);
        task = new TimerTask() {
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

        timer = new Timer();
        timer.scheduleAtFixedRate(task, 250, 250);
    }

    public void stopRandom(View view) {
        view.setEnabled(false);
        findViewById(R.id.startRand).setEnabled(true);
        if (task != null) {
            task.cancel();
            timer.cancel();
        } else {
            System.out.print("cannot stop timer and task...");
        }
    }
}
