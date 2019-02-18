package com.example.lab6_3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String SHARED_PREF_FILE = "MyApp";
    static final String SHARED_PREF_EDITOR_TEXT_KEY = "textInTheEditor";

    EditText editText;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ActivityReminderApplication application = ActivityReminderApplication.getInstance();
        ActivityReminderData activityReminderData = application.getActivityReminderData();

        activityReminderData.setTime(Integer.parseInt(editText.getText().toString()));

        SharedPreferences sharedPreferences = getPref();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREF_EDITOR_TEXT_KEY, activityReminderData.getTime());
        editor.commit();

        int time = activityReminderData.getTime() * 60;
        startBroadcast(time);

        finish();
    }

    protected SharedPreferences getPref() {
        return getSharedPreferences(SHARED_PREF_FILE, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityReminderApplication application = ActivityReminderApplication.getInstance();
        ActivityReminderData activityReminderData = application.getActivityReminderData();

        SharedPreferences sharedPreferences = getPref();
        int time = sharedPreferences.getInt(SHARED_PREF_EDITOR_TEXT_KEY, 0);

        activityReminderData.setTime(time);

        editText.setText(String.valueOf(time));
    }

    protected void startBroadcast(int time) {
        AlarmManager alarmManager;
        PendingIntent alarmIntent;

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ActivityReminderBroadcastReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + time * 1000,
                time * 1000, alarmIntent);
    }
}
