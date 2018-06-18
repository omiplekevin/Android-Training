package com.android.omiplekevin.daggerdemo.modules;

import com.android.omiplekevin.daggerdemo.BatteryPack;
import com.android.omiplekevin.daggerdemo.CameraPackage;
import com.android.omiplekevin.daggerdemo.FrontCameraSet;
import com.android.omiplekevin.daggerdemo.RearCameraSet;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = BatteryModule.class)
public class CameraModule {

    boolean frontWide;
    boolean frontStandard;
    boolean rearWide;
    boolean rearStandard;

    @Singleton
    public CameraModule(boolean frontWide, boolean frontStandard, boolean rearWide, boolean rearStandard) {
        this.frontWide = frontWide;
        this.frontStandard = frontStandard;
        this.rearWide = rearWide;
        this.rearStandard = rearStandard;

    }

    /**
     * Provides CameraPackage instance to methods that require this object as parameter within {@link CameraModule}.
     * @return instance of {@link CameraPackage}
     */
    @Provides
    @Named ("frontSide")
    @SuppressWarnings("unused")
    CameraPackage providesFrontCameraPackage() {
        return new CameraPackage(this.frontWide,
                this.frontStandard);
    }

    /**
     * Provides CameraPackage instance to methods that require this object as parameter within {@link CameraModule}.
     * @return instance of {@link CameraPackage}
     */
    @Provides
    @Named ("rearSide")
    @SuppressWarnings("unused")
    CameraPackage providesRearCameraPackage() {
        return new CameraPackage(this.rearWide,
                this.rearStandard);
    }

    /**
     * Provides instance of required objects by the constructor of {@link FrontCameraSet}. In this case,
     * the {@link CameraPackage} in the parameters is provided by
     * {@link CameraModule#providesCameraPackageToFront(CameraPackage)}.
     * See constructor injection in {@link FrontCameraSet}.
     * @return instance of {@link FrontCameraSet}
     */
    @Provides
    @SuppressWarnings("unused")
    FrontCameraSet providesCameraPackageToFront(@Named("frontSide") CameraPackage cameraPackage) {
        return new FrontCameraSet(cameraPackage);
    }

    /**
     * Provides instance of required objects by the constructor of {@link RearCameraSet}. In this case,
     * the {@link CameraPackage} in the parameters is provided by
     * {@link CameraModule#providesCameraPackageToRear(CameraPackage)}.
     * See constructor injection in {@link RearCameraSet}.
     * @return instance of {@link RearCameraSet}
     */
    @Provides
    @SuppressWarnings("unused")
    RearCameraSet providesCameraPackageToRear(@Named("rearSide") CameraPackage cameraPackage) {
        return new RearCameraSet(cameraPackage);
    }
}
