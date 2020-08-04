package com.example.submissionfinalbfaa.model.moviemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ResultsItemMovie implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("vote_average")
    private Number vote_average;

    public ResultsItemMovie() {

    }

    protected ResultsItemMovie(Parcel in) {
        id = in.readInt();
        vote_count = in.readInt();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        original_language = in.readString();
        vote_average = (Number) in.readSerializable();
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
        dest.writeSerializable(vote_average);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultsItemMovie> CREATOR = new Creator<ResultsItemMovie>() {
        @Override
        public ResultsItemMovie createFromParcel(Parcel in) {
            return new ResultsItemMovie(in);
        }

        @Override
        public ResultsItemMovie[] newArray(int size) {
            return new ResultsItemMovie[size];
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

    public Number getVote_average() {
        return vote_average;
    }

    public void setVote_average(Number vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public String toString() {
        return
                "ResultsItemMovie{" +
                        "overview = '" + overview + '\'' +
                        ",original_language = '" + original_language + '\'' +
                        ",title = '" + title + '\'' +
                        ",poster_path = '" + poster_path + '\'' +
                        ",release_date = '" + release_date + '\'' +
                        ",vote_average = '" + vote_average + '\'' +
                        ",id = '" + id + '\'' +
                        ",vote_count = '" + vote_count + '\'' +
                        "}";
    }
}
