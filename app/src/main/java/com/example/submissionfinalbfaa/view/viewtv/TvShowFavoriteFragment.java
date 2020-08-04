package com.example.submissionfinalbfaa.view.viewtv;


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
import com.example.submissionfinalbfaa.adapter.tvshowadapter.FavoriteTvShowAdapter;
import com.example.submissionfinalbfaa.database.AplicationDatabase;
import com.example.submissionfinalbfaa.database.CatalogDB;
import com.example.submissionfinalbfaa.model.tvmodel.ResultsItemTv;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment {
    private RecyclerView rvTvShow;
    private ArrayList<CatalogDB> listTv = new ArrayList<>();
    private AplicationDatabase aplicationDatabase;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavoriteTvShowAdapter favoriteTvShowAdapter = new FavoriteTvShowAdapter();

        rvTvShow = view.findViewById(R.id.rv_tvshow_fav);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (this.aplicationDatabase == null) {
            aplicationDatabase = AplicationDatabase.initDatabase(getContext());
        }
        rvTvShow.setLayoutManager(layoutManager);
        listTv.addAll(aplicationDatabase.catalogDAO().getByCategory("tv"));
        favoriteTvShowAdapter.setData(listTv, getContext());
        rvTvShow.setAdapter(favoriteTvShowAdapter);

        favoriteTvShowAdapter.setOnItemClickCallback(new FavoriteTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItemTv resultsItemTv) {
                showTvSelected(resultsItemTv);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    private void showTvSelected(ResultsItemTv resultsItemTv){
        CatalogDB catalogDBs = new CatalogDB();
        catalogDBs.setTitle(resultsItemTv.getOriginal_name());
        catalogDBs.setPoster_path(resultsItemTv.getPoster_path());
        catalogDBs.setRelease_date(resultsItemTv.getFirst_air_date());
        catalogDBs.setVote_count(resultsItemTv.getVote_count());
        catalogDBs.setOriginal_language(resultsItemTv.getOriginal_language());
        catalogDBs.setOverview(resultsItemTv.getOverview());
        catalogDBs.setId(resultsItemTv.getId());
        catalogDBs.setVote_average(Double.parseDouble(resultsItemTv.getVote_average().toString()));

        Intent detailIntent = new Intent(getContext(), DetailTvShowActivity.class);
        detailIntent.putExtra(DetailTvShowActivity.EXTRA_TV, resultsItemTv);
        startActivity(detailIntent);
    }

}
