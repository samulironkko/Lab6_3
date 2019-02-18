package com.example.lab6_3;

import android.app.Application;

public class ActivityReminderApplication extends Application {

    static ActivityReminderApplication instance = null;

    ActivityReminderData activityReminderData = new ActivityReminderData();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ActivityReminderApplication getInstance() { return instance; }

    public ActivityReminderData getActivityReminderData() { return activityReminderData; }
}

