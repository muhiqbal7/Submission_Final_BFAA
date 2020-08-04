package com.example.submissionfinalbfaa.service.servicemovie;

import com.example.submissionfinalbfaa.BuildConfig;
import com.example.submissionfinalbfaa.model.moviemodel.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    String apiKey = BuildConfig.TMDB_API_KEY;
    @GET("3/movie/popular?api_key=" + apiKey)
    Call<MovieResponse> getMovies(@Query("language") String language);

    @GET("3/search/movie?api_key="+ apiKey)
    Call<MovieResponse> getMoviesSearch(@Query("language") String language, @Query("query") String query);

    @GET("3/movie/popular?api_key=" + apiKey)
    Call<MovieResponse> getMoviesRelease(@Query("primary_release_date.gte") String primary_release_date_gte, @Query("primary_release_date.lte") String primary_release_date_lte);
}
