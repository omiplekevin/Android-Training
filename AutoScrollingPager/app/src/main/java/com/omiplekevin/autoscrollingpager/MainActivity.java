package com.omiplekevin.autoscrollingpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements UpcomingFragmentContent.OnFragmentInteractionListener{

    private UpcomingViewPagerAdapter adapter;
    private ViewPager upcomingPager;
    private ScheduledExecutorService executorService;
    private Future future;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<UpcomingFragmentContent> contents = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ContentDetail contentDetail = new ContentDetail();
            contentDetail.content = "Content content content " + (i+1);
            contentDetail.date = "date " + (i+1);
            UpcomingFragmentContent contentFragment = UpcomingFragmentContent.newInstance(contentDetail);
            contents.add(contentFragment);
        }
        upcomingPager = (ViewPager) findViewById(R.id.activities);
        adapter = new UpcomingViewPagerAdapter(getSupportFragmentManager(), contents);
        upcomingPager.setAdapter(adapter);

        executorService = Executors.newSingleThreadScheduledExecutor();
        future = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e("MAIN", "change!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (upcomingPager.getCurrentItem() == 4) {
                            upcomingPager.setCurrentItem(0, true);
                        } else {
                            upcomingPager.setCurrentItem(upcomingPager.getCurrentItem() + 1, true);
                        }
                    }
                });
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String uri) {

    }

    public class ContentDetail implements Serializable {
        String date;
        String content;
    }
}
