package com.example.submissionfinalbfaa.view.viewtv;


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
import com.example.submissionfinalbfaa.adapter.tvshowadapter.TvShowAdapter;
import com.example.submissionfinalbfaa.model.tvmodel.ResultsItemTv;
import com.example.submissionfinalbfaa.viewmodel.viewmodeltv.TvShowViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rvTvShow;
    private TvShowAdapter tvShowAdapter;
    private FloatingActionButton fabSearchTvShow;
    ProgressBar progressBar;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.notifyDataSetChanged();

        rvTvShow = view.findViewById(R.id.rv_tvshow);
        progressBar = view.findViewById(R.id.progressBarTv);
        fabSearchTvShow = view.findViewById(R.id.fab_search_tv);

        showLoading(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        TvShowViewModel viewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        viewModel.loadTvshow(getResources().getString(R.string.language));
        viewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShows);

        rvTvShow.setLayoutManager(layoutManager);
        rvTvShow.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItemTv resultsItemTv) {
                showTvSelected(resultsItemTv);
            }
        });
        fabSearchTvShow.setOnClickListener(this);
    }

    private Observer<ArrayList<ResultsItemTv>> getTvShows = new Observer<ArrayList<ResultsItemTv>>() {
        @Override
        public void onChanged(ArrayList<ResultsItemTv> resultsItemTvs) {
            if(resultsItemTvs != null){
                tvShowAdapter.setDataTv(resultsItemTvs, getContext());
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

        Intent detailIntent = new Intent(getContext(), DetailTvShowActivity.class);
        detailIntent.putExtra(DetailTvShowActivity.EXTRA_TV, resultsItemTv);
        startActivity(detailIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_search_tv){
            Intent searchIntentTv = new Intent(getContext(), SearchTvShowActivity.class);
            startActivity(searchIntentTv);
        }
    }
}
