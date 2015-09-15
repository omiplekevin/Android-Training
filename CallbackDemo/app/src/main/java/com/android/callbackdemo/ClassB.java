package com.android.callbackdemo;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by OMIPLEKEVIN on 15/09/2015.
 */
public class ClassB {

    public CommonInterface commonInterface;

    public ClassB(final Context context){
        commonInterface = new CommonInterface() {
            @Override
            public void onMessageReceived(String message) {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }
        };
    }

}
