package com.example.submissionfinalbfaa.service.servicetvshow;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowService {
    Retrofit retrofit;
    public TvShowApi getTvShowApi(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TvShowApi.class);
    }
}
