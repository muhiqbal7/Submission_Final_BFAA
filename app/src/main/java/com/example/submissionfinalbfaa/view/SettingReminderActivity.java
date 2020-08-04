package com.example.submissionfinalbfaa.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.model.moviemodel.MovieResponse;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;
import com.example.submissionfinalbfaa.notification.NotificationHelper;
import com.example.submissionfinalbfaa.notification.ReleaseReceiver;
import com.example.submissionfinalbfaa.service.servicemovie.MovieService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.submissionfinalbfaa.providerutils.Utils.KEY_DAILY_REMINDER;
import static com.example.submissionfinalbfaa.providerutils.Utils.KEY_RELEASE_REMINDER;
import static com.example.submissionfinalbfaa.providerutils.Utils.STATE_DAILY_REMINDER;
import static com.example.submissionfinalbfaa.providerutils.Utils.STATE_RELEASE_REMINDER;

public class SettingReminderActivity extends AppCompatActivity {
    private Switch switchRelease, switchDaily;
    private SharedPreferences spDailyRe, spReleaseRe;
    private SharedPreferences.Editor editorDailyRe, editorReleaseRe;

    private List<ResultsItemMovie> movies = new ArrayList<>();

    boolean setStateDailyRe, setStateReleaseRe;
    NotificationHelper notificationHelper = new NotificationHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_reminder);

        switchRelease = findViewById(R.id.switch_release_reminder);
        switchDaily = findViewById(R.id.switch_daily_reminder);

        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorDailyRe = spDailyRe.edit();
                if (isChecked){
                    editorDailyRe.putBoolean(STATE_DAILY_REMINDER,true);
                    editorDailyRe.apply();
                    switchDaily.setText(R.string.on_switch);
                    NotificationHelper.scheduleDailyRepeatingRTCNotif(getApplicationContext(), "11", "12");
                    NotificationHelper.enabledBootReceiver(getApplicationContext());
                } else {
                    editorDailyRe.putBoolean(STATE_DAILY_REMINDER, false);
                    editorDailyRe.apply();
                    switchDaily.setText(R.string.off_switch);
                    NotificationHelper.cancelAlarmDailyRTC();
                    NotificationHelper.disableBootReceiver(getApplicationContext());
                }
            }
        });

        if (switchDaily.isChecked()){
            switchDaily.setText(R.string.on_switch);
            NotificationHelper.scheduleDailyRepeatingRTCNotif(getApplicationContext(), "8", "0");
            NotificationHelper.enabledBootReceiver(getApplicationContext());
        } else {
            switchDaily.setText(R.string.off_switch);
            NotificationHelper.cancelAlarmDailyRTC();
            NotificationHelper.disableBootReceiver(getApplicationContext());
        }

        movies = ReleaseReceiver.setMovieRelease();

        switchRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorReleaseRe = spReleaseRe.edit();
                if(isChecked){
                    editorReleaseRe.putBoolean(STATE_RELEASE_REMINDER, true);
                    editorReleaseRe.apply();
                    switchRelease.setText(R.string.on_switch);
                    notificationHelper.scheduleReleaseRepeatingRTCNotif(getApplicationContext(), movies);
                    NotificationHelper.enabledBootReceiver(getApplicationContext());
                } else {
                    editorReleaseRe.putBoolean(STATE_RELEASE_REMINDER, false);
                    editorReleaseRe.apply();
                    switchRelease.setText(R.string.off_switch);
                    NotificationHelper.cancelAlarmReleaseRTC();
                    NotificationHelper.disableBootReceiver(getApplicationContext());
                }
            }
        });

        setSharedPreferences();

        if (switchRelease.isChecked()){
            switchRelease.setText(R.string.on_switch);
            notificationHelper.scheduleReleaseRepeatingElapsedNotif(getApplicationContext(), movies);
            NotificationHelper.enabledBootReceiver(getApplicationContext());
        } else {
            switchRelease.setText(R.string.off_switch);
            NotificationHelper.cancelAlarmReleaseRTC();
            NotificationHelper.disableBootReceiver(getApplicationContext());
        }
    }

    private void setSharedPreferences(){
        spDailyRe = getSharedPreferences(KEY_DAILY_REMINDER, MODE_PRIVATE);
        setStateDailyRe = spDailyRe.getBoolean(STATE_DAILY_REMINDER, false);
        switchDaily.setChecked(setStateDailyRe);

        spReleaseRe = getSharedPreferences(KEY_RELEASE_REMINDER, MODE_PRIVATE);
        setStateReleaseRe = spReleaseRe.getBoolean(STATE_RELEASE_REMINDER, false);
        switchRelease.setChecked(setStateReleaseRe);
    }
}
