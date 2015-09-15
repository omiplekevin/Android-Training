package com.android.callbackdemo;

import android.content.Context;

/**
 * Created by OMIPLEKEVIN on 15/09/2015.
 */
public class ClassA {

    Context context;
    public ClassA(Context context){
        this.context = context;
    }

    public void sendThisMessage(String message){
        //back to implementing class
        ClassB classB = new ClassB(context);
        classB.commonInterface.onMessageReceived(message);
    }
}
