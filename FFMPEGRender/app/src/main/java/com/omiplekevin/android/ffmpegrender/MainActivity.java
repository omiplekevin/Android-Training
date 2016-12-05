package com.omiplekevin.android.ffmpegrender;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements F45FFMpegEncoder.F45FFMpegEncoderListener{

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.setMessage("creating...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        Intent i = new Intent(this, F45FFMpegEncoder.getInstance(this).getClass());
        startService(i);
    }

    @Override
    public void onEncodeInitStart() {
        Log.e(getClass().getName(), "onEncodeInitStart");
    }

    @Override
    public void onEncodeInitFinish(String message) {
        Log.e(getClass().getName(), "onEncodeInitFinish");
        dialog.setMessage(message);
    }

    @Override
    public void onEncodeInitFailure(String message) {
        Log.e(getClass().getName(), "onEncodeInitFailure");
        dialog.setMessage(message);
    }

    @Override
    public void onEncodeFinish(String message) {
        Log.e(getClass().getName(), "onEncodeFinish");
        dialog.setMessage(message);
        dialog.dismiss();

        Intent videoPlay = new Intent(this, VideoPlay.class);
        videoPlay.putExtra(VideoPlay.VID_PATH, Environment.getExternalStorageDirectory() + "/F45/videos/output3.mp4");
        startActivity(videoPlay);
    }

    @Override
    public void onEncodeFailure(String message) {
        Log.e(getClass().getName(), "onEncodeFailure");
        dialog.setMessage(message);
    }

    @Override
    public void onEncodeStart() {
        Log.e(getClass().getName(), "onEncodeStart");
        dialog.setMessage("starting...");
    }

    @Override
    public void onEncodeSuccess(String message) {
        Log.e(getClass().getName(), "onEncodeSuccess");
        dialog.setMessage("success...");
    }

    @Override
    public void onEncodeProgress(String message) {
        Log.e(getClass().getName(), "onEncodeProgress");
        dialog.setMessage(message);
    }
}
