package com.example.nkattavenkat.myapplication;

import android.util.Log;

/**
 * Created by NKattavenkat on 4/19/2017.
 */

public class BaseClass {
    public MainActivity activity;
    public static final String TAG = BaseClass.class.getSimpleName();

    public void printMe(){
        Log.d(TAG, "printMe: I am Base Class");
    }

    private final SomeReceiver someReceiver = new SomeReceiver() {
        @Override
        public void onReceive() {
            printMe();
        }
    };

    public SomeReceiver getReceiver(){
        return someReceiver;
    }

}
