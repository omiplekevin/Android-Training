package com.omiplekevin.android.f45segmentprogressbar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.omiplekevin.android.f45segmentprogressbar.ui_component.ProgressBarSegment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final int FACTOR_DURATION = 100;

    ProgressBarSegment currentWorkSegment;
    LinearLayout parentLayout;
    LinearLayout overlayDiviers;

    //Dummy Timeline List
    List<ProgressBarSegment> progressBarSegments = new ArrayList<>();
    TreeMap<Integer, Timeline> workoutSegments = new TreeMap<>();
    TimelineHelper timelineHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLayout = (LinearLayout) findViewById(R.id.rootView);
        overlayDiviers = (LinearLayout) findViewById(R.id.dividers);
    }

    @Override
    protected void onResume() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return sendHttpRequest("http://f45timeline.herokuapp.com/v2/timeline/gnwp?cache=0", false, null, "GET", null);
            }

            @Override
            protected void onPostExecute(String timelineResponse) {
                Log.e(TAG, timelineResponse);
                timelineHelper = new Gson()
                        .fromJson(timelineResponse,
                                TimelineHelper.class);
                Map<Integer, Timeline> timelines = timelineHelper.json.timeline;
                workoutSegments = prepareWorkoutSegments(timelineHelper);
                /*ProgressBarSegment progressBarSegment;
                progressBarSegment = new ProgressBarSegment(MainActivity.this);
                progressBarSegment.setMaxProgress(84000);
//                progressBarSegment.setProgress(42000);
                progressBarSegment.setDuration(84000);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parentLayout.addView(progressBarSegment, params);*/

//                for (int i = 0; i < workoutSegments.size(); i++) {
//                int ndx = 0;
//                for (Map.Entry<Integer, Timeline> entry : workoutSegments.entrySet()) {
//                    ImageView imageView = new ImageView(MainActivity.this);
//                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1F);
//                    BitmapDrawable bitmapDrawable;
//                    if (entry.getValue().key.equalsIgnoreCase("timer")) {
//                        bitmapDrawable = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.divider_mid));
//                        bitmapDrawable.setAntiAlias(false);
//                        bitmapDrawable.setDither(false);
//                        bitmapDrawable.setFilterBitmap(false);
//                    } else {
//                        bitmapDrawable = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.hydrate_icon));
//                        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                        imageView.setBackgroundColor(Color.parseColor("#373c74"));
//                        imageView.setPadding(1, 1, 1, 1);
//                        params1.gravity = Gravity.CENTER_VERTICAL;
//                    }
//                    imageView.setImageDrawable(bitmapDrawable);
//                    imageView.setAdjustViewBounds(true);
//                    overlayDiviers.addView(imageView, params1);
//                    ndx++;
//                }
                makeSegmentLayouts(workoutSegments);
//                setupSelectedProgressBar(getSegmentProgressBar(0), timelineHelper.json.timeline.get(0));
                performTimerTick(timelines);
            }
        }.execute();
        //add preprogressbar
        super.onResume();
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

    public void start() {
        if (currentWorkSegment != null) {
            currentWorkSegment.startAnimation();
        }
    }

    public void pause() {
        if (currentWorkSegment != null) {
            currentWorkSegment.pauseAnimation();
        }
    }

    public void resume() {
        if (currentWorkSegment != null) {
            currentWorkSegment.resumeAnimation();
        }
    }

    public void stop() {
        if (currentWorkSegment != null) {
            currentWorkSegment.stopAnimation();
        }
    }

    public String sendHttpRequest(String sourceUrl, boolean enableCacheConfig, String fileName, String method, TreeMap<String, String> map) {
        Log.e(TAG, "-\nSource:" + sourceUrl + "\nEnableCacheConfig:" + enableCacheConfig + "\nFilename:" + fileName + "\nMethod:" + method + "\nMap:" + map);
        StringBuilder responseString = new StringBuilder();
        try {
            //throws MalformedURLException
            URL urlSource = new URL(sourceUrl);
            //throws IOException
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlSource.openConnection();
            httpUrlConnection.setRequestMethod(method.toUpperCase(Locale.getDefault()));
            httpUrlConnection.setConnectTimeout(30 * 1000);

            if (method.equals("POST")) {
                StringBuilder params = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setInstanceFollowRedirects(false);
                httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpUrlConnection.setRequestProperty("charset", "utf-8");
                OutputStreamWriter writer = new OutputStreamWriter(httpUrlConnection.getOutputStream());
                writer.write(params.substring(0, params.length() - 1));
                writer.flush();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                responseString.append(line);
            }

            httpUrlConnection.getInputStream().close();

            if (enableCacheConfig && fileName != null) {
//                writeToCacheFile(responseString.toString(), fileName, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return responseString.toString();
    }

    private synchronized TreeMap<Integer, Timeline> prepareWorkoutSegments(TimelineHelper _timelineHelper) {
        TreeMap<Integer, Timeline> workSegments = new TreeMap<>();
        if (_timelineHelper != null) {
            try {
                Timeline entryModel;
                int workSegment = 0, hydrateSegment = 0;
                for (Map.Entry<Integer, Timeline> entryTimeline : _timelineHelper.json.timeline.entrySet()) {
                    entryModel = entryTimeline.getValue();
                    if (entryModel.key.equals("timer")) {
//                        Log.e(TAG, "ENTRY " + entryModel.mode.toUpperCase(Locale.getDefault()) + " ==============================> ");
                        if (entryModel.mode.equals("work") && entryModel.countdown == 1) {
//                            Log.e(TAG, "SEGMENT WORK ==============================> ");
                            workSegment++;
                            workSegments.put(entryTimeline.getKey(), entryTimeline.getValue());
                            Log.e(TAG, "Entry: " + entryTimeline.getKey() + " " + entryModel.mode);
                        }
                    } else if (entryModel.key.equals("image_countdown")) {
                        if (entryModel.landscape_image != null && !entryModel.landscape_image.isEmpty()) {
                            String filename = entryModel.landscape_image
                                    .substring(entryModel.landscape_image.lastIndexOf("/") + 1, entryModel.landscape_image.length()
                                    );
                            if (filename.contains("hydrate") && entryModel.progress == 0) {
//                                Log.e(TAG, "SEGMENT HYDRATE ==============================> ");
                                hydrateSegment++;
                                workSegments.put(entryTimeline.getKey(), entryTimeline.getValue());
                                Log.e(TAG, "Entry: " + entryTimeline.getKey() + " " + entryModel.mode);
                            }
                        }
                    }
                }
                Log.e(TAG, "SEGMENT MODELS: " + workSegments.size() + ", " + workSegment + " WORKS, " + hydrateSegment + " DRINKS");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "SEGMENT MODEL ERROR: " + e.getMessage());
                workSegments.clear();
            }
        }
        return workSegments;
    }

    public void makeSegmentLayouts(Map<Integer, Timeline> workoutSegmentTimelines) {
        ProgressBarSegment progressBarSegment;
        for (Map.Entry<Integer, Timeline> timelineEntry : workoutSegmentTimelines.entrySet()) {
            Timeline timelineModel = timelineEntry.getValue();
            if (timelineModel.key.equalsIgnoreCase("timer")) {
                //progress bar icon
                progressBarSegment = new ProgressBarSegment(MainActivity.this);
                progressBarSegment.setMaxProgress(timelineModel.countdown_duration * FACTOR_DURATION);
                progressBarSegment.setProgress(0);
                progressBarSegment.setDuration(timelineModel.countdown_duration * FACTOR_DURATION);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = 1F;
                params.setMargins(0, 0, 7, 0);
                parentLayout.addView(progressBarSegment, params);
                if (progressBarSegments != null) {
                    progressBarSegments.add(progressBarSegment);
                }
            } else if (timelineModel.key.equalsIgnoreCase("image_countdown")) {
                //image icon
                String filename = timelineModel.landscape_image
                        .substring(timelineModel.landscape_image.lastIndexOf("/") + 1, timelineModel.landscape_image.length()
                        );
                if (filename.contains("hydrate")) {
                    //just a dummy progressbar hold for hydrate
                    progressBarSegment = new ProgressBarSegment(MainActivity.this);
                    progressBarSegment.setMaxProgress(timelineModel.countdown_duration * FACTOR_DURATION);
                    progressBarSegment.setProgress(0);
                    progressBarSegment.setDuration(timelineModel.countdown_duration * FACTOR_DURATION);
                    if (progressBarSegments != null) {
                        progressBarSegments.add(progressBarSegment);
                    }


                    ImageView imageView = new ImageView(MainActivity.this);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.hydrate_icon));
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setAdjustViewBounds(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2F);
                    params.setMargins(0, 0, 7, 0);
                    parentLayout.addView(imageView, params);
                }
            }
        }
    }

    private void performTimerTick(final Map<Integer, Timeline> timelines) {
        Log.e(TAG, "performTimerTick");
        ScheduledExecutorService performTick = Executors.newSingleThreadScheduledExecutor();
        performTick.scheduleAtFixedRate(new Runnable() {
            int timelineNdx = 1;
            String lastMode = "";
            @Override
            public void run() {
                try {
                    Timeline timelineEntry = timelines.get(timelineNdx);
                    if(timelineEntry.mode != null && !lastMode.equalsIgnoreCase(timelineEntry.mode)){
                        lastMode = timelineEntry.mode;
                        Log.e(TAG, "Current State: " + lastMode);
                        if (lastMode.equalsIgnoreCase("work")) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setupSelectedProgressBar(getSegmentProgressBar(timelineNdx), timelineHelper.json.timeline.get(timelineNdx));
                                    start();
                                }
                            });
                        }
                    }

                    /*if (timelineNdx == 10) {
                        timelineNdx = 700;
                        Log.e(TAG, "dummy reset to " + timelineNdx);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                setupSelectedProgressBar(getSegmentProgressBar(timelineNdx), timelineHelper.json.timeline.get(timelineNdx));
                                start();
                            }
                        });
                    }

                    if (timelineNdx == 800) {
                        timelineNdx = 466;
                        Log.e(TAG, "dummy reset to " + timelineNdx);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                setupSelectedProgressBar(getSegmentProgressBar(timelineNdx), timelineHelper.json.timeline.get(timelineNdx));
                                start();
                            }
                        });
                    }

                    if (timelineNdx == 530) {
                        timelineNdx = 2210;
                        Log.e(TAG, "dummy reset to " + timelineNdx);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                setupSelectedProgressBar(getSegmentProgressBar(timelineNdx), timelineHelper.json.timeline.get(timelineNdx));
                                start();
                            }
                        });
                    }

                    if (timelineNdx == 2227) {
                        timelineNdx = 1256;
                        Log.e(TAG, "dummy reset to " + timelineNdx);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stop();
                                setupSelectedProgressBar(getSegmentProgressBar(timelineNdx), timelineHelper.json.timeline.get(timelineNdx));
                                start();
                            }
                        });
                    }*/

                    Log.e(TAG, "TIMELINE: " + timelineNdx + "-" + timelineEntry.key + "-" + timelineEntry.mode + "-" + timelineEntry.countdown + "/" + timelineEntry.countdown_duration);
                    timelineNdx++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, FACTOR_DURATION, FACTOR_DURATION, TimeUnit.MILLISECONDS);
    }


    private ProgressBarSegment getSegmentProgressBar(int key) {
        int progressSegmentBarIndex = 0;
        clearAllProgressSegments();
        for (Map.Entry<Integer, Timeline> timeline : workoutSegments.entrySet()) {
            Log.e(TAG, "Key:" + timeline.getKey());
            if (timeline.getKey() >= key) {
                break;
            }
            fillProgressSegment(progressSegmentBarIndex);
            progressSegmentBarIndex++;
        }
        Log.e(TAG, "returning progressSegmentBar #" + (progressSegmentBarIndex + 1));
        currentWorkSegment = progressBarSegments.get(progressSegmentBarIndex);
        return currentWorkSegment;
    }

    private void clearAllProgressSegments() {
        for (ProgressBarSegment segment : progressBarSegments) {
            segment.setProgress(0);
            segment.invalidate();
        }
    }

    private void fillProgressSegment(int progressSegmentBarIndex) {
        ProgressBarSegment pbs = progressBarSegments.get(progressSegmentBarIndex);
        pbs.setProgress(pbs.getMax());
        pbs.invalidate();
    }

    private void setupSelectedProgressBar(ProgressBarSegment progressBarSegment, Timeline timeline) {
        Log.e(TAG, "setting progress: " + (timeline.countdown_duration - timeline.countdown) * FACTOR_DURATION + "/" + progressBarSegment.getMax());
        progressBarSegment.setProgress((timeline.countdown_duration - timeline.countdown) * FACTOR_DURATION);
        progressBarSegment.invalidate();
    }
}
