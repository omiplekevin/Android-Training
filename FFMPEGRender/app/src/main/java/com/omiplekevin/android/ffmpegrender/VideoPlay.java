package com.omiplekevin.android.ffmpegrender;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class VideoPlay extends Activity {

    public static final String VID_PATH = "VID_PATH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        VideoView vv = (VideoView) findViewById(R.id.videoEncoded);
        vv.setVideoPath(getIntent().getExtras().getString(VideoPlay.VID_PATH));
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.pause();
                mp.seekTo(0);
                mp.start();
            }
        });
        vv.start();
    }
}
