package com.omiplekevin.android.ffmpegnativelib;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.netcompss.ffmpeg4android.GeneralUtils;
import com.netcompss.loader.LoadJNI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.startBtn) Button startBtn;
    @BindView(R.id.stopBtn) Button stopBtn;
    @BindView(R.id.video) VideoView videoView;

    NativeRenderAsync renderTask;
    LoadJNI vk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRender();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRender();
            }
        });
    }

    private void startRender() {
        Log.i("RENDER", "startRender");
        if (renderTask != null) {
            renderTask.cancel(true);
            renderTask = null;
        }

        renderTask = new NativeRenderAsync();
        renderTask.execute();
    }

    private void stopRender() {
        Log.i("RENDER", "stopRender");
        if (renderTask != null) {
            renderTask.cancel(true);

            if (renderTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
                Log.e("STOP-RENDER", "FINISHED");
            } else if (renderTask.getStatus().equals(AsyncTask.Status.PENDING)) {
                Log.e("STOP-RENDER", "PENDING");
            } else if (renderTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                Log.e("STOP-RENDER", "RUNNING");
            }
        }

        if (vk != null) {
            vk.fExit(this);
        }
    }

    private class NativeRenderAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            Log.i("test", "onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            GeneralUtils.checkForPermissionsMAndAbove(MainActivity.this, true);
            vk = new LoadJNI();
            try {
                String workFolder = getApplicationContext().getFilesDir().getAbsolutePath();
                /*String[] complexCommand = {"ffmpeg", "-y", "-i", "/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4",
                        "-i", "/mnt/internal_sd/F45/videos/F45-Kb-Squat-Upright-Row-3276-688.mp4",
                        "-i", "/mnt/internal_sd/F45/videos/F45-Lateral-Box-Jumps-3292-698.mp4",
                        "-filter_complex",
                        "color=0x1f2142:size=720x994 [base];[0:v] setpts=PTS-STARTPTS, scale=511x316 [v1];[1:v] setpts=PTS-STARTPTS, scale=511x316 [v2];[2:v] setpts=PTS-STARTPTS, scale=511x316 [v3];[base][v1] overlay=shortest=1:x=189:y=22 [t1];[t1][v2] overlay=shortest=1:x=189:y=340 [t2];[t2][v3] overlay=shortest=1:x=189:y=658 [video]",
                        "-b:v", "65536k",
                        "-minrate", "65536k",
                        "-map", "[video]",
                        "/mnt/internal_sd/F45/.temp/0.mp4"};*/

                String[] complexCommand = {"ffmpeg","-y","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-i","/mnt/internal_sd/F45/videos/F45-Rb-Jump-Squat-And-Mb-Rotation-3866-785.mp4","-filter_complex","nullsrc=size=720x1280 [base];[0:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v1];[1:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v2];[2:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v3];[3:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v4];[4:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v5];[5:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v6];[6:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v7];[7:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v8];[8:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v9];[base][v1] overlay=shortest=1:x=20:y=244 [t1];[t1][v2] overlay=shortest=1:x=360:y=244 [t2];[t2][v3] overlay=shortest=1:x=20:y=435 [t3];[t3][v4] overlay=shortest=1:x=360:y=435 [t4];[t4][v5] overlay=shortest=1:x=20:y=626 [t5];[t5][v6] overlay=shortest=1:x=360:y=626 [t6];[t6][v7] overlay=shortest=1:x=20:y=817 [t7];[t7][v8] overlay=shortest=1:x=360:y=817 [t8];[t8][v9] overlay=shortest=1:x=20:y=1008 [video]","-map","[video]","/mnt/internal_sd/F45/output.mp4"};
                vk.run(complexCommand, workFolder, getApplicationContext());
                Log.i("test", "ffmpeg4android finished successfully");
            } catch (Throwable e) {
                Log.e("test", "vk run exception.", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("test", "onPostExecute");
            super.onPostExecute(aVoid);
        }
    }
}
