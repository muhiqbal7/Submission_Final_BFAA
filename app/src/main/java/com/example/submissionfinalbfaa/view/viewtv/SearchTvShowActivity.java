package com.example.submissionfinalbfaa.view.viewtv;

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
import com.example.submissionfinalbfaa.adapter.tvshowadapter.TvShowAdapter;
import com.example.submissionfinalbfaa.model.tvmodel.ResultsItemTv;
import com.example.submissionfinalbfaa.viewmodel.viewmodeltv.SearchTvShowViewModel;

import java.util.ArrayList;

public class SearchTvShowActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView rvTvShowSearch;
    String querySearch = "";
    String stringSearch = "";
    SearchView searchView;
    TvShowAdapter tvShowAdapter;
    SearchTvShowViewModel searchTvShowViewModel;
    private static final String SEARCH_KEY = "search";
    private Observer<ArrayList<ResultsItemTv>> getTvShows = new Observer<ArrayList<ResultsItemTv>>() {
        @Override
        public void onChanged(ArrayList<ResultsItemTv> resultsItemTvs) {
            if (resultsItemTvs != null){
                tvShowAdapter.setDataTv(resultsItemTvs, getApplicationContext());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv_show);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            stringSearch = savedInstanceState.getString(SEARCH_KEY);
        }

        rvTvShowSearch = findViewById(R.id.rv_tvshow_search);
        rvTvShowSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    public void onSearch(String querySearch){
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.notifyDataSetChanged();

        searchTvShowViewModel = ViewModelProviders.of(this).get(SearchTvShowViewModel.class);
        searchTvShowViewModel.loadTvshow(getResources().getString(R.string.language), querySearch);
        searchTvShowViewModel.getTvShow().observe(this, getTvShows);
        rvTvShowSearch.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItemTv resultsItemTv) {
                showTvSelected(resultsItemTv);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            this.querySearch = query;
            Log.d("SearchTvShowActivity", query);
            onSearch(query);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        try {
            this.querySearch = query;
            Log.d("SearchTvShowActivity", query);
            onSearch(query);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void showTvSelected(ResultsItemTv resultsItemTv){
        ResultsItemTv resultsItemTvs = new ResultsItemTv();
        resultsItemTvs.setOriginal_name(resultsItemTv.getOriginal_name());
        resultsItemTvs.setPoster_path(resultsItemTv.getPoster_path());
        resultsItemTvs.setFirst_air_date(resultsItemTv.getFirst_air_date());
        resultsItemTvs.setVote_count(resultsItemTv.getVote_count());
        resultsItemTvs.setOriginal_language(resultsItemTv.getOriginal_language());
        resultsItemTvs.setOverview(resultsItemTv.getOverview());
        resultsItemTvs.setId(resultsItemTv.getId());
        resultsItemTvs.setVote_average(resultsItemTv.getVote_average());

        Intent detailIntent = new Intent(this, DetailTvShowActivity.class);
        detailIntent.putExtra(DetailTvShowActivity.EXTRA_TV, resultsItemTv);
        startActivity(detailIntent);
    }
}
