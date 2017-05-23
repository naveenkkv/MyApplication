package com.example.nkattavenkat.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NextActivity extends AppCompatActivity {

    private static final String TAG = NextActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        Log.d(TAG, "TaskROOT MainActivity: " + isTaskRoot());

    }
}
