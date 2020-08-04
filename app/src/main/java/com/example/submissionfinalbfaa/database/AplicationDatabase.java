package com.example.submissionfinalbfaa.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        CatalogDB.class
}, version = 1,
        exportSchema = false)
public abstract class AplicationDatabase extends RoomDatabase {
    public abstract CatalogDAO catalogDAO();
    private static AplicationDatabase aplicationDatabase;

    public static AplicationDatabase initDatabase(Context context){
        if (aplicationDatabase == null){
            aplicationDatabase = Room
                    .databaseBuilder(context, AplicationDatabase.class, "favorite")
                    .allowMainThreadQueries()
                    .build();
        }
        return aplicationDatabase;
    }
}
