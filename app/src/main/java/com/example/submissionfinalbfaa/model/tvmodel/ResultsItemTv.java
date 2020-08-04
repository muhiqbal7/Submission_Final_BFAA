package com.example.submissionfinalbfaa.model.tvmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ResultsItemTv implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("original_name")
    private String original_name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("vote_average")
    private Number vote_average;

    public ResultsItemTv() {

    }

    protected ResultsItemTv(Parcel in) {
        id = in.readInt();
        vote_count = in.readInt();
        original_name = in.readString();
        overview = in.readString();
        first_air_date = in.readString();
        poster_path = in.readString();
        original_language = in.readString();
        vote_average = (Number) in.readSerializable();
    }

    public static final Creator<ResultsItemTv> CREATOR = new Creator<ResultsItemTv>() {
        @Override
        public ResultsItemTv createFromParcel(Parcel in) {
            return new ResultsItemTv(in);
        }

        @Override
        public ResultsItemTv[] newArray(int size) {
            return new ResultsItemTv[size];
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

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(vote_count);
        dest.writeString(original_name);
        dest.writeString(overview);
        dest.writeString(first_air_date);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeSerializable(vote_average);
    }

    @Override
    public String toString() {
        return
                "ResultsItemMovie{" +
                        "overview = '" + overview + '\'' +
                        ",original_language = '" + original_language + '\'' +
                        ",original_name = '" + original_name + '\'' +
                        ",poster_path = '" + poster_path + '\'' +
                        ",first_air_date = '" + first_air_date + '\'' +
                        ",vote_average = '" + vote_average + '\'' +
                        ",id = '" + id + '\'' +
                        ",vote_count = '" + vote_count + '\'' +
                        "}";
    }
}
