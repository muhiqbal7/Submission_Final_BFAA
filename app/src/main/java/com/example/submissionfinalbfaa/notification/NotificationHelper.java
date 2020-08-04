package com.example.submissionfinalbfaa.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;

import java.util.Calendar;
import java.util.List;

public class NotificationHelper {
    private static AlarmManager alarmManagerRTCDaily, alarmManagerElapsedDaily,
            alarmManagerRTCRelease, alarmManagerElapsedRelease;
    private static PendingIntent pendingIntentRTCDaily, pendingIntentElapsedDaily,
            pendingIntentRTCRelease, pendingIntentElapsedRelease;

    public static int ALARM_TYPE_RTC = 100;
    public static int ALARM_TYPE_ELAPSED = 101;
    public static final String ID = "id";
    public static final String TITLE = "title";
    private int id;

    public static void scheduleDailyRepeatingRTCNotif(Context context, String hour, String min) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.set(Calendar.HOUR_OF_DAY, 7);
        mCalendar.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(context, DailyReceiver.class);

        pendingIntentRTCDaily = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerRTCDaily = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManagerRTCDaily.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis() + 200, AlarmManager.INTERVAL_DAY, pendingIntentRTCDaily);
    }

    public static void scheduleDailyRepeatingElapsedNotif(Context context) {
        Intent intent = new Intent(context, DailyReceiver.class);

        pendingIntentElapsedDaily = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManagerElapsedDaily = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManagerElapsedDaily.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntentElapsedDaily);
    }

    public void scheduleReleaseRepeatingRTCNotif(Context context, List<ResultsItemMovie> listMovies) {
        for (ResultsItemMovie itemMovie : listMovies) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.HOUR_OF_DAY, 8);
            mCalendar.set(Calendar.MINUTE, 0);
            mCalendar.set(Calendar.SECOND, 0);

            Log.d("Example", itemMovie.getTitle());

            Intent intent = new Intent(context, ReleaseReceiver.class);
            intent.putExtra(ID, id);
            intent.putExtra(TITLE, itemMovie.getTitle());

            pendingIntentRTCRelease = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManagerRTCRelease = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManagerRTCRelease.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis() + 200, AlarmManager.INTERVAL_DAY, pendingIntentRTCRelease);
            id = id + 1;
        }
    }

    public void scheduleReleaseRepeatingElapsedNotif(Context context, List<ResultsItemMovie> lisMovies) {
        for (ResultsItemMovie itemMovie : lisMovies) {
            Log.d("Example", itemMovie.getTitle());

            Intent intent = new Intent(context, ReleaseReceiver.class);
            intent.putExtra(ID, id);
            intent.putExtra(TITLE, itemMovie.getTitle());

            pendingIntentElapsedRelease = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManagerElapsedRelease = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManagerElapsedRelease.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntentElapsedRelease);
            id = id + 1;
        }
    }

    public static void cancelAlarmDailyRTC() {
        if (alarmManagerRTCDaily != null) {
            alarmManagerRTCDaily.cancel(pendingIntentRTCDaily);
        }
    }

    public static void cancelAlarmReleaseRTC() {
        if (alarmManagerRTCRelease != null) {
            alarmManagerRTCRelease.cancel(pendingIntentRTCRelease);
        }
    }

    public static void enabledBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);

        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void disableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);

        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
