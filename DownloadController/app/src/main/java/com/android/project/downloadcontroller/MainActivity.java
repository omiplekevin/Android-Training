package com.android.project.downloadcontroller;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.project.downloadcontroller_lib.controller.DownloadController;
import com.android.project.downloadcontroller_lib.dao.DownloadRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private DownloadController downloadController;

    //views
    private TextView downloadBucketText;
    private TextView mapDownloadBucket;
    private TextView getDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        downloadBucketText = (TextView) findViewById(R.id.downloadBucket);
        mapDownloadBucket = (TextView) findViewById(R.id.mapDownloads);
        getDownload = (TextView) findViewById(R.id.getState);
        downloadController = ((App) getApplicationContext()).getDownloadController();
        downloadController.setDownloadQueueLimit(5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadController.startListening();
        List<DownloadRequest> requests = new ArrayList<>();
        List<String> urls = new ArrayList<String>() {{
            //add here all urls that you want to download using DownloadController
            add("http://f45tv.s3.amazonaws.com/challenge_ads/F45_Challenge_Blue_TV_July_Landscape_vB.jpg");
            add("http://f45tv.s3.amazonaws.com/challenge_ads/F45_Challenge_Blue_TV_July_Portrait.jpg");
            add("http://f45tv.s3.amazonaws.com/class-logos/angrybirdies.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/greatjob.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/warm-up.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_22.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_3peat.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_athletica.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_brooklyn.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_docklands.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_firestorm.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_foxtrot_new.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_gravity.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_hollywood.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_panthers.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_pipeline.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_quarterbacks.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_renegades.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_romans.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_templars.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_wingman.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/bears.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/bears2.png");
            add("https://f45tv.s3.amazonaws.com/slideshow/logo_angrybird_v2.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/22_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/22_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/3peat_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/3peat_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/allstar_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/allstar_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/angrybird_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/angrybird_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/athletica_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/athletica_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/bears_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/bears_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/brooklyn_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/brooklyn_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/docklands_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/docklands_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/firestorm_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/firestorm_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/foxtrot_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/foxtrot_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/gravity_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/gravity_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/hollywood_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/hollywood_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/loyals_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/loyals_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/miami_nights_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/miami_nights_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/mvp_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/mvp_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/panthers_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/panthers_schedules.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/pegasus_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/pegasus_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/pipeline_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/pipeline_350x350.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/playoffs.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/quarterbacks_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/quarterbacks_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/renegade_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/renegade_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/romans_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/romans_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/t10_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/t10_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/templars_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/templars_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/trailblazer_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/trailblazer_350x350.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/varsity_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/varsity_schedules.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/wingman_300x300.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/wingman_350x350.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/22_100x100.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/22_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_3peat.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/3peat_500x500.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/small/small_allstar.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/allstar_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_angrybirdies.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/angrybird_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_athletica.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/athletica_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_bears.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/bears_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_brooklyn.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/brooklyn_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_docklands.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/docklands_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_firestorm.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/firestorm_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_foxtrot.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/foxtrot_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_gravity.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/gravity_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_hollywood.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/hollywood_500x500_new.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_loyals.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/loyals_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_miami_nights.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/miami_nights_500x500.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/small/small_mvp_correct.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/mvp_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_panthers.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/panthers_timer.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_pegasus.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/pegasus_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_pipeline.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/pipeline_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_playoffs.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_quarterbacks.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/quarterbacks_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_renegade.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/renegade_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_romans.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/romans_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_t10.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/t10_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_templars.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/templars_500x500.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/small/small_trailblazer.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/trailblazer_500x500.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_varsity.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/varsity_timer.png");
            add("http://f45tv.s3.amazonaws.com/class-logos/small/small_wingman.png");
            add("https://f45tv.s3.amazonaws.com/class-logos/wingman_500x500.png");

        }};

        for (String url : urls) {
            requests.add(new DownloadRequest(
                    -1,
                    url,
                    DownloadController.getSavePath(url),
                    Uri.parse(url).getLastPathSegment(),
                    DownloadController.getFileExtension(url))
            );
        }

        downloadController.addToQueue(requests);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        downloadController.stopListening();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadEvent(HashMap<String, DownloadRequest> requests) {
        Log.d(TAG, "onDownloadEvent via EventBus");
        final StringBuilder queueStringBuilder = new StringBuilder();
        final StringBuilder waitingStringBuilder = new StringBuilder();
        for (Map.Entry<String, DownloadRequest> request : requests.entrySet()) {
            if (request.getValue().getState() == DownloadManager.STATUS_RUNNING) {
                Log.d(TAG, "onDownloadEvent updating queueStringBuilder");
                queueStringBuilder.append(request.getValue().getSaveName() + " - " + request.getValue().getPercentage() + "\n");
            } else {
                Log.d(TAG, "onDownloadEvent updating waitingStringBuilder");
                waitingStringBuilder.append(request.getValue().getSaveName() + " - " + request.getValue().getPercentage() + "\n");
            }
        }
        downloadBucketText.setText(queueStringBuilder.toString());
        mapDownloadBucket.setText(waitingStringBuilder.toString());

        DownloadRequest request = downloadController.getDownloadState("https://s3-eu-west-1.amazonaws.com/f45fm/mixes/dX1ynpjHRFK3rpCBZ6Wa_Jetstream%2045_DJ%20Chris%20Dominick_July%2011%202017.mp3");
        getDownload.setText(request.getSaveName() + " - " + request.getPercentage() + "\n");
    }
}
