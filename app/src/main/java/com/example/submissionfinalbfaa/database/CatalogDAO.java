package com.example.submissionfinalbfaa.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatalogDAO {
    @Insert
    void insert(CatalogDB catalogDB);

    @Query("Select * from favorite_mv_tv")
    List<CatalogDB> getCatalogDB();

    @Query("Select * from favorite_mv_tv where id = :idMovie limit 1")
    List<CatalogDB> getById(int idMovie);

    @Query("Select * from favorite_mv_tv where category = :category")
    List<CatalogDB> getByCategory(String category);

    @Delete
    void delete(CatalogDB catalogDB);

    @Query("SELECT COUNT(*) FROM " + "favorite_mv_tv")
    int count();

    @Insert
    long[] insertAll(CatalogDB[] catalogDB);

    @Insert
    long insertMovie(CatalogDB catalogDB);

    @Query("SELECT * FROM favorite_mv_tv")
    Cursor getAllMovie();

    @Query("SELECT * FROM favorite_mv_tv WHERE id = :idMovie LIMIT 1")
    Cursor getMovieById(Long idMovie);
}
