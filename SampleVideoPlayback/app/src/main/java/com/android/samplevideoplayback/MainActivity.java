package com.android.samplevideoplayback;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends FragmentActivity {

    private VideoView mVideoView;
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind view to object
        mVideoView = (VideoView)findViewById(R.id.videoEntry);

        //initialize MediaController, if you don't want to use MediaControls, ignore this line
        mMediaController = new MediaController(this);
        //Anchor MediaController to VideoView, if you don't want to use MediaControls, ignore this line
        mMediaController.setAnchorView(mVideoView);

        //since you'll be reading from internal/external directory
        //use Environment.getExternalStorageDirectory() + path to your video
        //Environment.getExternalStorageDirectory() covers internal and external storage
        File file = new File(Environment.getExternalStorageDirectory() + "/amp");
        mVideoView.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory() + "/amp/sample.mp4"));

        //set media controller for Video View, if you don't want to use MediaControls, ignore this line
        mVideoView.setMediaController(mMediaController);

        //start video playback
        mVideoView.start();

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
}
