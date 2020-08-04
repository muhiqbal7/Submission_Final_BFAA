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
import com.example.submissionfinalbfaa.model.tvmodel.ResultsItemTv;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.CardViewViewHolder> {
    private ArrayList<ResultsItemTv> listTvShow = new ArrayList<>();
    private Context context;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setDataTv(ArrayList<ResultsItemTv> list, Context context){
        this.context = context;
        listTvShow.clear();
        listTvShow.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_tv_show_card_view, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowAdapter.CardViewViewHolder holder, int i) {
        ResultsItemTv resultsItemTv = listTvShow.get(i);

        resultsItemTv.setId(listTvShow.get(i).getId());
        resultsItemTv.setOriginal_name(listTvShow.get(i).getOriginal_name());
        resultsItemTv.setFirst_air_date(listTvShow.get(i).getFirst_air_date());
        resultsItemTv.setOriginal_language(listTvShow.get(i).getOriginal_language());
        resultsItemTv.setVote_average(listTvShow.get(i).getVote_average());
        resultsItemTv.setVote_count(listTvShow.get(i).getVote_count());
        resultsItemTv.setOverview(listTvShow.get(i).getOverview());
        resultsItemTv.setPoster_path(listTvShow.get(i).getPoster_path());

        holder.tvTitle.setText(listTvShow.get(i).getOriginal_name());
        holder.tvRelease.setText(listTvShow.get(i).getFirst_air_date());
        holder.tvScore.setText(listTvShow.get(i).getVote_average().toString());
        holder.tvOverview.setText(listTvShow.get(i).getOverview());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w154" + listTvShow.get(i).getPoster_path())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.tvPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTvShow.get(holder.getAdapterPosition()));
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
