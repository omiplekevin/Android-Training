package com.android.omiplekevin.daggerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.omiplekevin.daggerdemo.components.DaggerSmartphoneComponent;
import com.android.omiplekevin.daggerdemo.modules.BatteryModule;
import com.android.omiplekevin.daggerdemo.modules.CameraModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Inject
    public FrontCameraSet frontCameraSet;
    @Inject
    public RearCameraSet rearCameraSet;
    @Inject
    public BatteryPack batteryPack;

    @BindView(R.id.batteryLevelText)
    TextView batteryLevelText;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * do injection here
         */
        DaggerSmartphoneComponent
                .builder()
                .cameraModule(new CameraModule(true, false, false, true))
                .batteryModule(new BatteryModule(3300, 6))
                .build()
                .inject(this);
    }

    @OnClick(R.id.getBatteryLevelBtn)
    void showBatteryLevel() {
        final int batteryLevel = batteryPack.generateNewBatteryLevel();
        batteryLevelText.setText(String.valueOf(batteryLevel));
        batteryLevelText.invalidate();
    }

    @OnClick(R.id.shotWithRearWide)
    void shotWithRearWide() {
        if (batteryPack.getCurrentBatteryLevel() <= BatteryPack.LOW_BATTERY_LEVEL) {
            toast.setText("Battery low! Camera is restricted.");
        } else if (rearCameraSet.shotWithWideAngleLens()) {
            toast.setText("Captured @ rear wide " + System.currentTimeMillis());
        } else {
            toast.setText("Unable to capture, see logs.");
        }
        toast.show();
    }

    @OnClick(R.id.shotWithRearStandard)
    void shotWithRearStandard() {
        if (batteryPack.getCurrentBatteryLevel() <= BatteryPack.LOW_BATTERY_LEVEL) {
            toast.setText("Battery low! Camera is restricted.");
        } else if (rearCameraSet.shotWithStandardLens()) {
            toast.setText("Captured @ rear standard " + System.currentTimeMillis());
        } else {
            toast.setText("Unable to capture, see logs.");
        }
        toast.show();
    }

    @OnClick(R.id.shotWithFrontWide)
    void shotWithFrontWide() {
        if (batteryPack.getCurrentBatteryLevel() <= BatteryPack.LOW_BATTERY_LEVEL) {
            toast.setText("Battery low! Camera is restricted.");
        } else if (frontCameraSet.shotWithWideAngleLens()) {
            toast.setText("Captured @ front wide " + System.currentTimeMillis());
        } else {
            toast.setText("Unable to capture, see logs.");
        }
        toast.show();
    }

    @OnClick(R.id.shotWithFrontStandard)
    void shotWithFrontStandard() {
        if (batteryPack.getCurrentBatteryLevel() <= BatteryPack.LOW_BATTERY_LEVEL) {
            toast.setText("Battery low! Camera is restricted.");
        } else if (frontCameraSet.shotWithStandardLens()) {
            toast.setText("Captured @ front standard " + System.currentTimeMillis());
        } else {
            toast.setText("Unable to capture, see logs.");
        }
        toast.show();
    }
}
