package com.example.submissionfinalbfaa.view.viewmovie;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.adapter.movieadapter.FavoriteMovieAdapter;
import com.example.submissionfinalbfaa.database.AplicationDatabase;
import com.example.submissionfinalbfaa.database.CatalogDB;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<CatalogDB> listMovie = new ArrayList<>();
    private AplicationDatabase aplicationDatabase;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        FavoriteMovieAdapter favoriteMovieAdapter = new FavoriteMovieAdapter();

        rvMovies = view.findViewById(R.id.rv_movie_fav);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (this.aplicationDatabase == null){
            aplicationDatabase = AplicationDatabase.initDatabase(getContext());
        }
        rvMovies.setLayoutManager(layoutManager);
        listMovie.addAll(aplicationDatabase.catalogDAO().getByCategory("movie"));
        favoriteMovieAdapter.setData(listMovie, getContext());
        rvMovies.setAdapter(favoriteMovieAdapter);

        favoriteMovieAdapter.setOnItemClickCallback(new FavoriteMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItemMovie resultsItemMovie) {
                showMovieFavSelected(resultsItemMovie);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorit, container, false);
    }

    private void showMovieFavSelected(ResultsItemMovie resultsItemMovie){
        CatalogDB catalogDBs = new CatalogDB();
        catalogDBs.setTitle(resultsItemMovie.getTitle());
        catalogDBs.setPoster_path(resultsItemMovie.getPoster_path());
        catalogDBs.setRelease_date(resultsItemMovie.getRelease_date());
        catalogDBs.setVote_count(resultsItemMovie.getVote_count());
        catalogDBs.setOriginal_language(resultsItemMovie.getOriginal_language());
        catalogDBs.setOverview(resultsItemMovie.getOverview());
        catalogDBs.setId(resultsItemMovie.getId());
        catalogDBs.setVote_average(Double.parseDouble(resultsItemMovie.getVote_average().toString()));

        Intent detaiIntentFav = new Intent(getContext(), DetailMovieActivity.class);
        detaiIntentFav.putExtra(DetailMovieActivity.EXTRA_MOVIE, resultsItemMovie);
        startActivity(detaiIntentFav);
    }

}
