package com.example.omiplekevin.f45timerrevamp;

/**
 * Created by OMIPLEKEVIN on 14/09/2015.
 *
 */
public interface NanoHttpdCallback {

    /**
     * passes message to implementing class
     * @param message message received by the {@link com.android.f45tv.controller.service.nanohttpd.NanoHttpdManager}
     */
    void onMessageReceived(String message);

}
