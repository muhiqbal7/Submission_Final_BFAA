package com.example.submissionfinalbfaa.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.model.moviemodel.MovieResponse;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;
import com.example.submissionfinalbfaa.service.servicemovie.MovieService;
import com.example.submissionfinalbfaa.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseReceiver extends BroadcastReceiver {
    public static String CHANNEL_ID = "channel_07";
    public static CharSequence CHANNEL_NAME = "dicoding channel 07";
    private static List<ResultsItemMovie> listMovies = new ArrayList<>();
    private static MovieService movieService;
    private List<ResultsItemMovie> movies = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        movies = setMovieRelease();

        Intent intentToRepeat = new Intent(context, MainActivity.class);
        int id = intent.getIntExtra(NotificationHelper.ID, 0);

        Log.d("nilai", intent.getStringExtra(NotificationHelper.TITLE));
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setContentTitle(intent.getStringExtra(NotificationHelper.TITLE))
                .setContentText("Release Reminder")
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null){
            notificationManager.notify(id, notification);
        }
    }

    public static List<ResultsItemMovie> setMovieRelease(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        final String dateNow = simpleDateFormat.format(date);

        if (movieService == null){
            movieService = new MovieService();
        }

        movieService.getMovieApi().getMoviesRelease(dateNow, dateNow).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    listMovies.clear();
                    listMovies.addAll(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return listMovies;
    }
}
