package com.example.submissionfinalbfaa.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.database.AplicationDatabase;
import com.example.submissionfinalbfaa.database.CatalogDB;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.Settings.System.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<CatalogDB> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    Cursor cursor;
    AplicationDatabase aplicationDatabase;

    public StackRemoteViewsFactory(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null){
            cursor.close();
        }

        final long identity = Binder.clearCallingIdentity();
        aplicationDatabase = AplicationDatabase.initDatabase(mContext);

        mWidgetItems.addAll(aplicationDatabase.catalogDAO().getCatalogDB());
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identity);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rtViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_fav);

        URL url;
        Bitmap bitmap = null;

        try{
            url = new URL("https://image.tmdb.org/t/p/w154"+mWidgetItems.get(position).getPoster_path());

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException io){
            Log.e("Error", io.getMessage());
        }
        rtViews.setImageViewBitmap(R.id.image_view, bitmap);

        Bundle extra = new Bundle();
        extra.putInt(MvTvFavWidget.EXTRA_ITEM, position);

        Intent fillIntent = new Intent();
        fillIntent.putExtras(extra);

        rtViews.setOnClickFillInIntent(R.id.image_view, fillIntent);
        return rtViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
