package com.omiplekevin.android.f45testbench;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.DownloadManager;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "F45TestBench";

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private ListView demoComponentsListView;
    private DemoComponentListAdapter adapter;
    private boolean mVisible;

    public static final String CustomTableActivity = "CUSTOMTABLEACTIVITY";
    public static final String DualViewPager = "DUALVIEWPAGER";
    public static final String F45Animations = "F45ANIMATIONS";
    public static final String FlipAnimationActivity = "FLIPANIMATIONACTIVITY";
    public static final String EventBusActivity = "EVENTBUSACTIVITY";
    public static final String F45PowerNewSkin = "F45POWERNEWSKIN";
    public static final String DownloadManagers = "DOWNLOADMANAGERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        demoComponentsListView = (ListView) findViewById(R.id.demoComponentsListView);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        //setup listview adapter for component list
        List<String> custom = new ArrayList<>();
        custom.add(MainActivity.CustomTableActivity);
        custom.add(MainActivity.DualViewPager);
        custom.add(MainActivity.F45Animations);
        custom.add(MainActivity.FlipAnimationActivity);
        custom.add(MainActivity.EventBusActivity);
        custom.add(MainActivity.F45PowerNewSkin);
        custom.add(MainActivity.DownloadManagers);

        adapter = new DemoComponentListAdapter(this, custom);
        demoComponentsListView.setAdapter(adapter);
        demoComponentsListView.setOnItemClickListener(this);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.demoComponentsListView).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

//        doRequests();
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (adapter.getItem(position)) {
            case MainActivity.CustomTableActivity:
                startActivity(new Intent(this, CustomTableActivity.class));
                break;
            case MainActivity.DualViewPager:
                startActivity(new Intent(this, DualViewPager.class));
                break;
            case MainActivity.F45Animations:
                startActivity(new Intent(this, F45Animations.class));
                break;
            case MainActivity.FlipAnimationActivity:
                startActivity(new Intent(this, FlipAnimationActivity.class));
                break;
            case MainActivity.EventBusActivity:
                startActivity(new Intent(this, EventBusActivity.class));
                break;
            case MainActivity.F45PowerNewSkin:
                startActivity(new Intent(this, F45PowerNewSkin.class));
                break;
            case MainActivity.DownloadManagers:
                startActivity(new Intent(this, ADMDemo.class));
                break;
        }
    }

    /*private void doRequests(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String requestUrl = "http://f45timeline.herokuapp.com/v3/timeline/gnwp?cache=0";
                String response = Utilities.sendHttpRequest(requestUrl, false, null, "get", null);
                TimelineHelper timelineHelper = new Gson().fromJson(response, TimelineHelper.class);

                for (Map.Entry<Integer, Timeline> timelineEntry : timelineHelper.json.timeline.entrySet()) {
                    Timeline timeline = timelineEntry.getValue();
                    if (timeline.key.equalsIgnoreCase("timer") || timeline.key.equalsIgnoreCase("image_countdown")) {
                        if (timeline.countdown_duration == timeline.countdown) {
                            Log.e(TAG, "=====================");
                            Log.e(TAG, "key: " + timeline.key + " - "
                                    + timeline.value + " - "
                                    + timeline.mode + " - "
                                    + timeline.landscape_image + " - "
                                    + timeline.countdown_duration + " - "
                                    + timeline.countdown);
                            Log.e(TAG, "=====================");
                        }
                    }
                }
            }
        }).start();
    }*/

    /*public void customCell(View view) {
        Intent intent = new Intent(this, CustomTableActivity.class);
        startActivity(intent);
    }

    public void dualViewPager(View view) {
        Intent intent = new Intent(this, DualViewPager.class);
        startActivity(intent);
    }

    public void f45Animations(View view) {
        Intent intent = new Intent(this, F45Animations.class);
        startActivity(intent);
    }

    public void flippingAnimation(View view) {
        Intent intent = new Intent(this, FlipAnimationActivity.class);
        startActivity(intent);
    }*/

    private class DemoComponentListAdapter extends BaseAdapter{

        Context context;
        List<String> customUIActivityList;

        public DemoComponentListAdapter(Context context, List<String> customUIActivityList) {
            this.context = context;
            this.customUIActivityList = customUIActivityList;
        }

        @Override
        public int getCount() {
            return customUIActivityList.size();
        }

        @Override
        public String getItem(int position) {
            return customUIActivityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.custom_ui_list_item, null);

            TextView textView = (TextView) convertView.findViewById(R.id.itemName);
            textView.setText(customUIActivityList.get(position));

            return convertView;
        }
    }
}
