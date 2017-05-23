package com.example.nkattavenkat.myapplication;

import android.util.Log;

/**
 * Created by NKattavenkat on 4/19/2017.
 */

public abstract class SomeReceiver{
    public void whoAmI(){
        Log.d("TAG", "whoAmI: ");
    }
    public abstract void onReceive();
}
