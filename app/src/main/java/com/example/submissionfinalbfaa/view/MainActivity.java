package com.example.submissionfinalbfaa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.adapter.movieadapter.MovieAdapter;
import com.example.submissionfinalbfaa.page.favorite.FavoriteFragment;
import com.example.submissionfinalbfaa.page.home.HomeFragment;
import com.example.submissionfinalbfaa.providerutils.MovieProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.submissionfinalbfaa.providerutils.Utils.KEY_DAILY_REMINDER;
import static com.example.submissionfinalbfaa.providerutils.Utils.KEY_RELEASE_REMINDER;
import static com.example.submissionfinalbfaa.providerutils.Utils.STATE_DAILY_REMINDER;
import static com.example.submissionfinalbfaa.providerutils.Utils.STATE_RELEASE_REMINDER;

public class MainActivity extends AppCompatActivity {
    private Fragment selectedFragment = new HomeFragment();
    private SharedPreferences spDailyRe, spReleaseRe;

    public static final String KEY_FRAGMENT = "fragment";
    boolean setStateDailyRe, setStateReleaseRe;

    MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            loadFragment(selectedFragment);
        } else {
            selectedFragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            loadFragment(selectedFragment);
        }

        setSharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.setting_reminder:
                Intent rIntent = new Intent(this, SettingReminderActivity.class);
                startActivity(rIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    loadFragment(selectedFragment);
                    return true;
                case R.id.navigation_favorite:
                    selectedFragment = new FavoriteFragment();
                    loadFragment(selectedFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, selectedFragment);
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void setSharedPreferences(){
        spDailyRe = getSharedPreferences(KEY_DAILY_REMINDER, MODE_PRIVATE);
        setStateDailyRe = spDailyRe.getBoolean(STATE_DAILY_REMINDER, false);

        spReleaseRe = getSharedPreferences(KEY_RELEASE_REMINDER, MODE_PRIVATE);
        setStateReleaseRe = spReleaseRe.getBoolean(STATE_RELEASE_REMINDER, false);
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
           switch (id){
               case 1:
               return new CursorLoader(
                       getApplicationContext(),
                       MovieProvider.URI_FAVORITE,
                       new String[]{
                               "title",
                               "poster_path",
                               "overview",
                               "vote_average",
                               "release_date",
                               "category"
                       },
                       null,
                       null,
                       null
               );
               default:
                   throw new IllegalArgumentException();
           }
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            switch (loader.getId()){
                case 1:
                    movieAdapter.setDataCursor(cursor);
                    break;
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            switch (loader.getId()){
                case 1:
                    movieAdapter.setDataCursor(null);
                    break;
            }
        }
    };

}
