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
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private static final String _DIR_MAIN = "/F45";
    private static final String _DIR_VIDEO = _DIR_MAIN + "/videos";
    private static final String _DIR_IMAGE = _DIR_MAIN + "/images";
    private static final String _DIR_MUSIC = _DIR_MAIN + "/music";
    private static final String _DIR_EXTRAS = _DIR_MAIN + "/extras";
    private static final String _DIR_TEMP = _DIR_MAIN + "/.temp";
    private static final String _DIR_APK = _DIR_MAIN + "/apk";
    private static final String _DIR_LOGS = _DIR_MAIN + "/log";
    private static final String _DIR_MOVEMENT = _DIR_MAIN + "/movement";

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadController.startListening();
        List<DownloadRequest> requests = new ArrayList<>();
        List<String> urls = new ArrayList<String>() {{
            add("https://s3-eu-west-1.amazonaws.com/f45fm/mixes/dvSoH9kfRMqSlCsp0iVx_Bondi%20Sessions_DJ%20Chris%20Dominick_July%207%202017.mp3");
            add("https://s3-eu-west-1.amazonaws.com/f45fm/mixes/dX1ynpjHRFK3rpCBZ6Wa_Jetstream%2045_DJ%20Chris%20Dominick_July%2011%202017.mp3");
            add("http://cdn.f45.info/F45-Db-Bent-Over-Row-3227-594.mp4");
            add("http://cdn.f45.info/F45-Incline-Dumbell-Chest-Press-3124-669.mp4");
            add("http://cdn.f45.info/F45-Kb-Alt-Forward-And-Reverse-Lunges-3915-676.mp4");
            add("http://cdn.f45.info/Bb-soft%20Boxes-les%20Mills%20On%202%20Boxes%20Underneath%20Rows%20Under%20Hand-2546.mp4");
            add("http://cdn.f45.info/F45-Barbell-Dealifts-3069-496.mp4");
            add("http://cdn.f45.info/F45-Sb-Lying-Leg-Curl-3962-883.mp4");
            add("http://cdn.f45.info/805%20Sandbag%20Box%20Step%20Ups%200057.mp4");
            add("http://cdn.f45.info/Db%20db%20Front%20Squat%20Pause%207696%2067.mp4");
            add("http://cdn.f45.info/F45-Sled-Rope-Pull-And-Push-Away-3977-907.mp4");
            add("http://cdn.f45.info/Db%20Box%20Jump%20Front%20Raise%20Holds-2235.mp4");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_22.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_romans.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_wingman.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_brooklyn.png");
            /*add("http://f45tv.s3.amazonaws.com/slideshow/logo_panthers.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_pipeline.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_firestorm.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_3peat.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_renegades.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_docklands.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_templars.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_gravity.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_foxtrot_new.png");
            add("http://f45tv.s3.amazonaws.com/slideshow/logo_quarterbacks.png");
            add("https://f45tv.s3.amazonaws.com/slideshow/logo_angrybird_v2.png");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/brooklyn_stay_movement_complete.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_hydrate_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_move_complete_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_complete_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_move_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_complete_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_left_move_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_mid_move_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_move_complete_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_right_move_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_stay_complete_movement.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_2.gif");
            add("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_complete.gif");*/
        }};

        for (String url : urls) {
            requests.add(new DownloadRequest(
                    -1,
                    url,
                    getSavePath(url),
                    Uri.parse(url).getLastPathSegment(),
                    getFileExtension(url))
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

    public static String getFileExtension(String url) {
        if (url.contains(".")) {
            String subUrl = url.substring(url.lastIndexOf('.') + 1);
            Log.d("DownloadManager", "getFileExtension: " + subUrl);
            if (subUrl.length() == 0) {
                return "";
            }

            if (subUrl.contains("?")) {
                return subUrl.substring(0, subUrl.indexOf('?'));
            }

            return subUrl;
        } else {
            //option to follow link of get the redirect link from the headers
        }

        return "";
    }

    public static String getSavePath(String sourceUrl) {
        switch (classifier(sourceUrl)) {
            case 1:
                return _DIR_VIDEO;
            case 2:
                return _DIR_IMAGE;
            case 3:
                return _DIR_MUSIC;
            case 4:
                return _DIR_MOVEMENT;
            default:
                return _DIR_EXTRAS;
        }
    }

    public static int classifier(String sourceUrl) {
        String fileExtension = getFileExtension(sourceUrl);
        fileExtension = fileExtension.toUpperCase(Locale.getDefault());
        switch (fileExtension) {
            case "MP4":
            case "3GP":
            case "MOV":
            case "M4A":
                //video file classification
                return 1;
            case "JPG":
            case "JPEG":
            case "PNG":
            case "BMP":
                //image file classification
                return 2;
            case "MP3":
            case "OGG":
            case "WMV":
                //music file classification
                return 3;
            case "GIF":
                //gif file classification
                return 4;
            default:
                //unknown / unlisted classification
                return 0;
        }
    }
}
