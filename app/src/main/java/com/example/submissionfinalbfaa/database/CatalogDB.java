package com.example.submissionfinalbfaa.database;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_mv_tv")
public class CatalogDB implements Parcelable {
    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "vote_count")
    private int vote_count;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    private String release_date;

    @ColumnInfo(name = "poster_path")
    private String poster_path;

    @ColumnInfo(name = "original_language")
    private String original_language;

    @ColumnInfo(name = "vote_average")
    private double vote_average;

    @ColumnInfo(name = "category")
    private String category;

    public CatalogDB() {

    }

    protected CatalogDB(Parcel in) {
        id = in.readInt();
        vote_count = in.readInt();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        original_language = in.readString();
        vote_average = in.readDouble();
        category = in.readString();
    }

    public static final Creator<CatalogDB> CREATOR = new Creator<CatalogDB>() {
        @Override
        public CatalogDB createFromParcel(Parcel in) {
            return new CatalogDB(in);
        }

        @Override
        public CatalogDB[] newArray(int size) {
            return new CatalogDB[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(vote_count);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeDouble(vote_average);
        dest.writeString(category);
    }

    public static CatalogDB fromContentValues(ContentValues values){
        final CatalogDB catalogDB = new CatalogDB();
        if (values.containsKey("id")){
            catalogDB.id = values.getAsInteger("id");
        }
        if (values.containsKey("vote_count")){
            catalogDB.vote_count = values.getAsInteger("vote_count");
        }
        if (values.containsKey("title")){
            catalogDB.title = values.getAsString("title");
        }
        if (values.containsKey("overview")){
            catalogDB.overview = values.getAsString("overview");
        }
        if (values.containsKey("release_date")){
            catalogDB.release_date = values.getAsString("release_date");
        }
        if (values.containsKey("poster_path")){
            catalogDB.poster_path = values.getAsString("poster_path");
        }
        if (values.containsKey("original_language")){
            catalogDB.original_language = values.getAsString("original_language");
        }
        if (values.containsKey("vote_average")){
            catalogDB.vote_average = values.getAsDouble("vote_average");
        }
        return catalogDB;
    }
}
