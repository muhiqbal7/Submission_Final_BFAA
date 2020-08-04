package com.example.submissionfinalbfaa.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.submissionfinalbfaa.view.SettingReminderActivity;

public class AlarmBootReceiver extends BroadcastReceiver {
    NotificationHelper notificationHelper = new NotificationHelper();
    ReleaseReceiver releaseReceiver = new ReleaseReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            NotificationHelper.scheduleDailyRepeatingElapsedNotif(context);
            notificationHelper.scheduleReleaseRepeatingElapsedNotif(context, releaseReceiver.setMovieRelease());
        }
    }
}
