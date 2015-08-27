package com.omiplekevin.jmdnssample.service;

import android.content.Context;

/**
 * Created by OMIPLEKEVIN on 16/08/2015.
 */
public class JmDnsManager {

    private volatile static JmDnsManager _instance;
    private static Context mContext;

    /**
     * Get new Singleton instance of JmDnsManager with double checked locking
     * @return
     */
    public static JmDnsManager getInstance(Context context){

        if(_instance == null){
            synchronized (JmDnsManager.class){
                if(_instance == null){
                    _instance = new JmDnsManager();
                    mContext = context;
                }
            }
        }
        return _instance;
    }
}
