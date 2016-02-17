package com.example.omiplekevin.f45timerrevamp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *
 * Created by OMIPLEKEVIN on 14/09/2015.
 */
public class NanoHttpdBroadcastReceiver extends BroadcastReceiver {

    private static volatile NanoHttpdBroadcastReceiver _instance;

    private static NanoHttpdCallback nanoHttpdCallback;

    public static final String _NANO_SERVICE_BROADCAST_RECEIVER = "NanoHttpdBroadcastReceiver";

    public static final String _MESSAGE_FIELD = "MESSAGE";

    /**
     * Singleton constructor of class {@link NanoHttpdBroadcastReceiver}
     *
     * @return instance of the class
     */
    public static NanoHttpdBroadcastReceiver getInstance() {
        if (_instance == null) {
            synchronized (NanoHttpdBroadcastReceiver.class) {
                if (_instance == null) {
                    _instance = new NanoHttpdBroadcastReceiver();
                }
            }
        }

        return _instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NanoHttpdBroadcastReceiver.nanoHttpdCallback.onMessageReceived(intent.getExtras().getString(_MESSAGE_FIELD));
    }

    public void setCallback(NanoHttpdCallback callback){
        NanoHttpdBroadcastReceiver.nanoHttpdCallback = callback;
    }
}

