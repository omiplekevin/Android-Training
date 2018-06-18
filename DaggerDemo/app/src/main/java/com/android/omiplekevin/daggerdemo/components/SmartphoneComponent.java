package com.android.omiplekevin.daggerdemo.components;

import com.android.omiplekevin.daggerdemo.MainActivity;
import com.android.omiplekevin.daggerdemo.modules.BatteryModule;
import com.android.omiplekevin.daggerdemo.modules.CameraModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {CameraModule.class, BatteryModule.class})
@Singleton
public interface SmartphoneComponent {

    /**
     * This abstract method will inject dependencies annotated with @Inject that
     * implements {@link CameraModule} class.
     * @param mainActivity target class to inject
     */
    void inject(MainActivity mainActivity);

}
