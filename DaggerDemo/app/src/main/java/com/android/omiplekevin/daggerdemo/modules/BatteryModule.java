package com.android.omiplekevin.daggerdemo.modules;

import com.android.omiplekevin.daggerdemo.BatteryPack;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BatteryModule {

    private int mWh;
    private int batteryCells;

    @Singleton
    public BatteryModule(int mWh, int batteryCells) {
        this.mWh = mWh;
        this.batteryCells = batteryCells;
    }

    @Provides
    @SuppressWarnings("unused")
    BatteryPack providesBatteryPackage() {
        return new BatteryPack(this.mWh, this.batteryCells);
    }
}
