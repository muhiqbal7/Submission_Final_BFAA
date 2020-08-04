package com.example.submissionfinalbfaa.service.servicetvshow;

import android.os.Build;

import com.example.submissionfinalbfaa.BuildConfig;
import com.example.submissionfinalbfaa.model.tvmodel.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowApi {
    String apiKey = BuildConfig.TMDB_API_KEY;
    @GET("3/tv/popular?api_key=" + apiKey)
    Call<TvShowResponse> getTvShows(@Query("language") String language);

    @GET("3/search/tv?api_key=" + apiKey)
    Call<TvShowResponse> getTvSearch(@Query("language") String language, @Query("query") String query);
}
