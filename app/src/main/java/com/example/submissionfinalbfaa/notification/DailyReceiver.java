package com.example.submissionfinalbfaa.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.view.MainActivity;

public class DailyReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID = "channel_02";
    public static CharSequence CHANNEL_NAME = "dicoding channel 02";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setContentTitle("Morning Notification")
                .setContentText("Morning Reminder")
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
