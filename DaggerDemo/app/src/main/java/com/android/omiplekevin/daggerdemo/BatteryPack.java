package com.android.omiplekevin.daggerdemo;

import java.util.Random;

public class BatteryPack {

    public static final int LOW_BATTERY_LEVEL = 15;

    private static final String TAG = "BatteryPack";

    private int batteryLevel = 0;

    private int battery_mWh;

    private int batteryCells;

    public BatteryPack(int battery_mWh, int batteryCells) {
        this.battery_mWh = battery_mWh;
        this.batteryCells = batteryCells;
    }

    public int getCurrentBatteryLevel() {
        return batteryLevel;
    }

    public int generateNewBatteryLevel() {
        batteryLevel = new Random().nextInt(100);
        return batteryLevel;
    }

    public int getBattery_mWh(){
        return this.battery_mWh;
    }

    public int getBatteryCells() {
        return this.batteryCells;
    }
}
