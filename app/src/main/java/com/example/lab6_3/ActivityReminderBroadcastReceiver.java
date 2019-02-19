package com.example.lab6_3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class ActivityReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(context, notification);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "Hey! It's time to move!", Toast.LENGTH_LONG).show();
    }
}
