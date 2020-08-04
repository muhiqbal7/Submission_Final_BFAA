package com.example.submissionfinalbfaa.service.servicemovie;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieService {
    Retrofit retrofit;
    public MovieApi getMovieApi(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit.create(MovieApi.class);
    }
}
