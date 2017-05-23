package com.example.nkattavenkat.myapplication;

import android.util.Log;

/**
 * Created by NKattavenkat on 4/19/2017.
 */

public class ChildClass extends BaseClass {
    public static final String TAG = ChildClass.class.getSimpleName();

    @Override
    public void printMe() {
        super.printMe();
        Log.d(TAG, "printMe: I am ChildClass");
    }
}
