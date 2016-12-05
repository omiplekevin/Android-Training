package com.omiplekevin.android.glesvideorender;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;

public class MainActivity extends Activity {

    LinearLayout parentLayout1;
    LinearLayout parentLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLayout1 = (LinearLayout) findViewById(R.id.activity_main1);
        parentLayout2 = (LinearLayout) findViewById(R.id.activity_main2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(720, ViewGroup.LayoutParams.MATCH_PARENT);
            CustomVideoView cvv = new CustomVideoView(this, new File(Environment.getExternalStorageDirectory() + "/F45/videos/F45LogoFinal.mp4"));
            parentLayout1.addView(cvv, params);
        }

        for (int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(720, ViewGroup.LayoutParams.MATCH_PARENT);
            CustomVideoView cvv = new CustomVideoView(this, new File(Environment.getExternalStorageDirectory() + "/F45/videos/F45LogoFinal.mp4"));
            parentLayout1.addView(cvv, params);
        }
        /*for (int i = 0; i < 4; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(320, 210);
            CustomVideoView cvv = new CustomVideoView(this, new File(Environment.getExternalStorageDirectory() + "/F45/videos/F45LogoFinal.mp4"));
            parentLayout2.addView(cvv, params);
        }*/
    }
}
