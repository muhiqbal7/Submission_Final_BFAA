package com.example.submissionfinalbfaa.view.viewmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.adapter.movieadapter.MovieAdapter;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;
import com.example.submissionfinalbfaa.viewmodel.viewmodelmovie.SearchMovieViewModel;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView rvMovieSearch;
    String querySearch = "";
    String stringSearch = "";
    SearchView searchView;
    MovieAdapter movieAdapter;
    SearchMovieViewModel searchMovieViewModel;
    private static final String SEARCH_KEY = "search";
    private Observer<ArrayList<ResultsItemMovie>> getMovies = new Observer<ArrayList<ResultsItemMovie>>() {
        @Override
        public void onChanged(ArrayList<ResultsItemMovie> resultsItemMovies) {
            if (resultsItemMovies != null) {
                movieAdapter.setData(resultsItemMovies, getApplicationContext());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            stringSearch = savedInstanceState.getString(SEARCH_KEY);
        }

        rvMovieSearch = findViewById(R.id.rv_movie_search);
        rvMovieSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        stringSearch = searchView.getQuery().toString();
        outState.putString(SEARCH_KEY, stringSearch);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attributeSet) {
        return super.onCreateView(parent, name, context, attributeSet);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search_mv_tv);
        searchView = (SearchView) item.getActionView();
        if (stringSearch != null && !stringSearch.isEmpty()) {
            item.expandActionView();
            searchView.setQuery(stringSearch, true);
            searchView.clearFocus();
            onSearch(stringSearch);
        } else {
            searchView.setOnQueryTextListener(this);
            searchView.setQueryHint("Search");
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void onSearch(String querySearch) {
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();

        searchMovieViewModel = ViewModelProviders.of(this).get(SearchMovieViewModel.class);
        searchMovieViewModel.loadMovie(getResources().getString(R.string.language), querySearch);
        searchMovieViewModel.getMovie().observe(this, getMovies);
        rvMovieSearch.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItemMovie resultsItemMovie) {
                showMovieSelected(resultsItemMovie);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            this.querySearch = query;
            Log.d("SearchMovieActivity", query);
            onSearch(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        try {
            this.querySearch = query;
            Log.d("SearchMovieActivity", query);
            onSearch(query);
            return true;
        } catch (Exception e) {
            return false;
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

        Intent detailIntent = new Intent(this, DetailMovieActivity.class);
        detailIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, resultsItemMovie);
        startActivity(detailIntent);
    }
}
