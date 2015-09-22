package com.android.listusingcustommodel;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

/**
 * Created by OMIPLEKEVIN on 17/09/2015.
 */
public class VideoPlayer extends Activity {

    VideoView videoView;

    @Override
    protected void onResume() {
        videoView = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse(getIntent().getExtras().getString("VIDEO_PATH"));
        videoView.setVideoURI(uri);
        videoView.start();

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
    }
}
