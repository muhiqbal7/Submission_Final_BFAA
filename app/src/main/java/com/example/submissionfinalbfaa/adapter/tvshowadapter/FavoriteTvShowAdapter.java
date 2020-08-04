package com.example.submissionfinalbfaa.adapter.tvshowadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.database.CatalogDB;
import com.example.submissionfinalbfaa.model.tvmodel.ResultsItemTv;

import java.util.ArrayList;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.CardViewViewHolder> {
    private ArrayList<CatalogDB> listTvShow = new ArrayList<>();
    private Context context;
    private FavoriteTvShowAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(FavoriteTvShowAdapter.OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<CatalogDB> list, Context context) {
        this.context = context;
        listTvShow.clear();
        listTvShow.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_tv_show_card_view, viewGroup, false);
        return new FavoriteTvShowAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder holder, int i) {
        final ResultsItemTv resultsItemTv = new ResultsItemTv();

        resultsItemTv.setId(listTvShow.get(i).getId());
        resultsItemTv.setOriginal_name(listTvShow.get(i).getTitle());
        resultsItemTv.setFirst_air_date(listTvShow.get(i).getRelease_date());
        resultsItemTv.setOriginal_language(listTvShow.get(i).getOriginal_language());
        resultsItemTv.setVote_average(listTvShow.get(i).getVote_average());
        resultsItemTv.setVote_count(listTvShow.get(i).getVote_count());
        resultsItemTv.setOverview(listTvShow.get(i).getOverview());
        resultsItemTv.setPoster_path(listTvShow.get(i).getPoster_path());

        holder.tvTitle.setText(listTvShow.get(i).getTitle());
        holder.tvRelease.setText(listTvShow.get(i).getRelease_date());
        holder.tvScore.setText(Double.toString(listTvShow.get(i).getVote_average()));
        holder.tvOverview.setText(listTvShow.get(i).getOverview());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w154" + listTvShow.get(i).getPoster_path())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.tvPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(resultsItemTv);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView tvPoster;
        TextView tvTitle, tvRelease, tvScore, tvOverview;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPoster = itemView.findViewById(R.id.img_tv_poster);
            tvTitle = itemView.findViewById(R.id.title_tv);
            tvRelease = itemView.findViewById(R.id.release_tv);
            tvScore = itemView.findViewById(R.id.score_tv);
            tvOverview = itemView.findViewById(R.id.overview_tv);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(ResultsItemTv resultsItemTv);
    }
}
