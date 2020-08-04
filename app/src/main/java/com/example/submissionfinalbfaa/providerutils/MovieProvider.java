package com.example.submissionfinalbfaa.providerutils;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.submissionfinalbfaa.database.AplicationDatabase;
import com.example.submissionfinalbfaa.database.CatalogDAO;
import com.example.submissionfinalbfaa.database.CatalogDB;

import java.util.ArrayList;

public class MovieProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.submissionfinalbfaa";
    public static final Uri URI_FAVORITE = Uri.parse(
            "content://" + AUTHORITY + "/" + "favorite_mv_tv"
    );

    private static final int CODE_MENU_DIR = 1;
    private static final int CODE_MENU_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, "favorite_mv_tv", CODE_MENU_DIR);
        MATCHER.addURI(AUTHORITY, "favorite_mv_tv/*", CODE_MENU_ITEM);
    }

    public MovieProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + "favorite_mv_tv";
            case CODE_MENU_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + "favorite_mv_tv";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final int id = values.getAsInteger("id");
                AplicationDatabase.initDatabase(context).catalogDAO().insert(CatalogDB.fromContentValues(values));
                return ContentUris.withAppendedId(uri, id);

            case CODE_MENU_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MENU_DIR || code == CODE_MENU_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }

            CatalogDAO catalogDAO = AplicationDatabase.initDatabase(context).catalogDAO();
            final Cursor cursor;
            if (code == CODE_MENU_DIR) {
                cursor = catalogDAO.getAllMovie();
            } else {
                cursor = catalogDAO.getMovieById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null){
            return new ContentProviderResult[0];
        }
        final AplicationDatabase aplicationDatabase = AplicationDatabase.initDatabase(context);
        aplicationDatabase.beginTransaction();
        try{
            final ContentProviderResult[] results = super.applyBatch(operations);
            aplicationDatabase.setTransactionSuccessful();
            return results;
        } finally {
            aplicationDatabase.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] contentValues){
        switch (MATCHER.match(uri)){
            case CODE_MENU_DIR:
                final Context context = getContext();
                if (context == null){
                    return 0;
                }

                final AplicationDatabase aplicationDatabase = AplicationDatabase.initDatabase(context);
                final CatalogDB[] catalogDB = new CatalogDB[contentValues.length];

                for (int i = 0; i < contentValues.length; i++){
                    catalogDB[i] = CatalogDB.fromContentValues(contentValues[i]);
                }
                return aplicationDatabase.catalogDAO().insertAll(catalogDB).length;
            case CODE_MENU_ITEM:
                throw new IllegalArgumentException("Invalid URI, Cannot Insert With ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
