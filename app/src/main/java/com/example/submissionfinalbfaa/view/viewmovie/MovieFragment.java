package com.example.submissionfinalbfaa.view.viewmovie;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.adapter.movieadapter.MovieAdapter;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;
import com.example.submissionfinalbfaa.viewmodel.viewmodelmovie.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private FloatingActionButton fabSearchMovie;
    ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();

        rvMovies = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progressBar);
        fabSearchMovie = view.findViewById(R.id.fab_search_mv);

        showLoading(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.loadMovie(getResources().getString(R.string.language));
        viewModel.getMovie().observe(getViewLifecycleOwner(), getMovies);

        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItemMovie resultsItemMovie) {
                showMovieSelected(resultsItemMovie);
            }
        });

        fabSearchMovie.setOnClickListener(this);
    }

    private Observer<ArrayList<ResultsItemMovie>> getMovies = new Observer<ArrayList<ResultsItemMovie>>() {
        @Override
        public void onChanged(ArrayList<ResultsItemMovie> resultsItemMovies) {
            if (resultsItemMovies != null){
                movieAdapter.setData(resultsItemMovies, getContext());
                showLoading(false);
            } else{
                showLoading(false);
                Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        } else{
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showMovieSelected(ResultsItemMovie resultsItemMovie){
        ResultsItemMovie resultsItemMovies = new ResultsItemMovie();
        resultsItemMovies.setTitle(resultsItemMovie.getTitle());
        resultsItemMovies.setPoster_path(resultsItemMovie.getPoster_path());
        resultsItemMovies.setRelease_date(resultsItemMovie.getRelease_date());
        resultsItemMovies.setVote_count(resultsItemMovie.getVote_count());
        resultsItemMovies.setOriginal_language(resultsItemMovie.getOriginal_language());
        resultsItemMovies.setOverview(resultsItemMovie.getOverview());
        resultsItemMovies.setId(resultsItemMovie.getId());
        resultsItemMovie.setVote_average(resultsItemMovie.getVote_average());

        Intent detailIntent = new Intent(getContext(), DetailMovieActivity.class);
        detailIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, resultsItemMovie);
        startActivity(detailIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_search_mv){
            Intent searchIntent = new Intent(getContext(), SearchMovieActivity.class);
            startActivity(searchIntent);
        }
    }
}
