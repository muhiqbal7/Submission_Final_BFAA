package com.example.submissionfinalbfaa.viewmodel.viewmodelmovie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submissionfinalbfaa.model.moviemodel.MovieResponse;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;
import com.example.submissionfinalbfaa.service.servicemovie.MovieService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private MovieService movieService;
    private String language;
    private MutableLiveData<ArrayList<ResultsItemMovie>> listMovie = new MutableLiveData<>();

    public LiveData<ArrayList<ResultsItemMovie>> getMovie(){
        return listMovie;
    }

    public void loadMovie(String language){
        this.language = language;
        if (this.movieService == null){
            movieService = new MovieService();
        }
        movieService.getMovieApi().getMovies(language).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    listMovie.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
