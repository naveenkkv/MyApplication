package com.example.nkattavenkat.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "NAVEENLOG";
    private static final String SETTINGS_ACTION = "android.settings.SETTINGS";
    private static final String TAG1 = "NAVEENLOG1";
    private static final int CLOSE_BUTTON_ACTION_REQUEST_CODE = 10;
    private SomeReceiver mSomeReceiver;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button settingsAction = (Button)findViewById(R.id.settings_action);
        settingsAction.setOnClickListener(this);
        Button settingsClass = (Button)findViewById(R.id.settings_class);
        settingsClass.setOnClickListener(this);
        Button settingsNewTask = (Button)findViewById(R.id.flagnewtask);
        settingsNewTask.setOnClickListener(this);
        Button triggerLog = (Button)findViewById(R.id.trigger_log);
        triggerLog.setOnClickListener(this);
        Button crashMe = (Button)findViewById(R.id.crash_me);
        crashMe.setOnClickListener(this);
        Button triggerNotification = (Button)findViewById(R.id.trigger_notification);
        triggerNotification.setOnClickListener(this);
        Log.d(TAG, "TaskROOT MainActivity: " + isTaskRoot());
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                MainActivity.this.startActivity(intent);
                //GMClass gmClass = new GMClass();
                //gmClass.activity = MainActivity.this;
                //gmClass.getReceiver().onReceive();
                //startActivity(new Intent(MainActivity.this, NextActivity.class));
                /*Snackbar.make(view, "Started next activity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    public void registerReceiver(SomeReceiver receiver){
        mSomeReceiver = receiver;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent settingsIntent = new Intent();
        switch (v.getId()){
            case R.id.settings_class:
                settingsIntent.setClassName("com.gm.settings", ".SettingsMainActivity");
                startActivity(settingsIntent);
                break;
            case R.id.settings_action:
                settingsIntent.setAction(SETTINGS_ACTION);
                startActivity(settingsIntent);
                break;
            case R.id.flagnewtask:
                settingsIntent.setAction(SETTINGS_ACTION);
                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(settingsIntent);
                break;
            case R.id.trigger_log:
                Log.v(TAG, "Triggering a verbose log");
                Log.d(TAG, "Triggering a debug log: ");
                Log.i(TAG, "Triggering an info log");
                Log.w(TAG, "Triggering a warning log");
                Log.wtf(TAG, "Triggering WTF log");
                Log.v(TAG1, "Triggering a verbose log");
                Log.d(TAG1, "Triggering a debug log: ");
                Log.i(TAG1, "Triggering an info log");
                Log.w(TAG1, "Triggering a warning log");
                Log.wtf(TAG1, "Triggering WTF log");
                break;
            case R.id.crash_me:
                Log.d(TAG, "Naveen: Crashing the app now");
                throw new NullPointerException();
            case R.id.trigger_notification:
                triggerNotification();
            default:
                break;
        }
        Log.d(TAG, "onClick: NAVEEN: " + settingsIntent.toString());
    }

    private void triggerNotification(){
        int priority, notificationId;
        int[] priorityValues;
        Spinner prioritySpinner = (Spinner)findViewById(R.id.priority);

        EditText notificationIdField = (EditText)findViewById(R.id.notif_id);
        String idText = notificationIdField.getText().toString();
        if(idText.isEmpty()) {
            Toast.makeText(this, "Enter proper notification id", Toast.LENGTH_SHORT).show();
        }
        priorityValues = getResources().getIntArray(R.array.priority_values);
        priority = priorityValues[ prioritySpinner.getSelectedItemPosition() ];
        notificationId = Integer.parseInt(idText);
        showGMAlert(notificationId, priority);
    }

    public void showGMAlert(int notificationId, int priority) {

        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.media_index_limit_reached);
        String notifTitle = "Default Priority ";
        switch (priority){
            case 2:
                notifTitle = "Max Priority ";
                break;
            case 1:
                notifTitle = "High Priority ";
                break;
            case 0:
                notifTitle = "Default Priority ";
                break;
            case -1:
                notifTitle = "Low Priority ";
                break;
            case -2:
                notifTitle = "Min Priority ";
                break;
        }
        notifTitle += Integer.toString(priority);
        remoteView.setTextViewText(R.id.title, notifTitle);
        Intent intent = new Intent(this, NextActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                CLOSE_BUTTON_ACTION_REQUEST_CODE, intent, 0);
        remoteView.setOnClickPendingIntent(R.id.media_index_limit_reached_pop_up_close, pendingIntent);
        Notification.Builder builder = getNotificationBuilder(priority);
        builder.setContent(remoteView);
        Notification notification = builder.build();
        mNotificationManager.notify(notificationId, notification);
    }

    private Notification.Builder getNotificationBuilder(int priority) {
        final Notification.Builder builder = new Notification.Builder(this);
        // User can cancel it
        builder.setOngoing(false);
        // this is a must even though we don't need it, otherwise InCallUI
        // process will crash
        builder.setSmallIcon(R.drawable.audio_ball);
        // Make the notification prioritized as highest notification so it shows
        // as an alert.
        builder.setPriority(priority);
        Log.d(TAG, "getNotificationBuilder: Priority" + priority);

        return builder;
    }
}
