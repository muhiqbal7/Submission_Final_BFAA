package com.example.submissionfinalbfaa.viewmodel.viewmodeltv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submissionfinalbfaa.model.tvmodel.ResultsItemTv;
import com.example.submissionfinalbfaa.model.tvmodel.TvShowResponse;
import com.example.submissionfinalbfaa.service.servicetvshow.TvShowService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvShowViewModel extends ViewModel {
    private TvShowService tvShowService;
    private String language;
    private MutableLiveData<ArrayList<ResultsItemTv>> listTvShow = new MutableLiveData<>();

    public LiveData<ArrayList<ResultsItemTv>> getTvShow(){
        return listTvShow;
    }

    public void loadTvshow(String language, String query) {
        this.language = language;
        if (this.tvShowService == null) {
            tvShowService = new TvShowService();
        }
        tvShowService.getTvShowApi().getTvSearch(language, query).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful()){
                    listTvShow.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }
}
