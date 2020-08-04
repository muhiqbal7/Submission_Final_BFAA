package com.example.submissionfinalbfaa.adapter.movieadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.database.CatalogDB;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.CardViewViewHolder> {
    private ArrayList<CatalogDB> listMovie = new ArrayList<>();
    private Context context;
    private FavoriteMovieAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(FavoriteMovieAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<CatalogDB> list, Context context) {
        this.context = context;
        listMovie.clear();
        listMovie.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movie_card_view, viewGroup, false);
        return new FavoriteMovieAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int i) {
        final ResultsItemMovie resultsItemMovie = new ResultsItemMovie();

        resultsItemMovie.setId(listMovie.get(i).getId());
        resultsItemMovie.setTitle(listMovie.get(i).getTitle());
        resultsItemMovie.setRelease_date(listMovie.get(i).getRelease_date());
        resultsItemMovie.setPoster_path(listMovie.get(i).getPoster_path());
        resultsItemMovie.setVote_average(listMovie.get(i).getVote_average());
        resultsItemMovie.setId(listMovie.get(i).getId());
        resultsItemMovie.setOverview(listMovie.get(i).getOverview());
        resultsItemMovie.setOriginal_language(listMovie.get(i).getOriginal_language());
        resultsItemMovie.setVote_count(listMovie.get(i).getVote_count());

        holder.mvTitle.setText(listMovie.get(i).getTitle());
        holder.mvOverview.setText(listMovie.get(i).getOverview());
        holder.mvRelease.setText(listMovie.get(i).getRelease_date());
        holder.mvScore.setText(Double.toString(listMovie.get(i).getVote_average()));
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w154" + listMovie.get(i).getPoster_path())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.mvPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(resultsItemMovie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView mvPoster;
        TextView mvTitle, mvRelease, mvScore, mvOverview;
        CardView cvMain;
        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            mvPoster = itemView.findViewById(R.id.img_mv_poster);
            mvTitle = itemView.findViewById(R.id.title_mv);
            mvRelease = itemView.findViewById(R.id.release_mv);
            mvScore = itemView.findViewById(R.id.score_mv);
            mvOverview = itemView.findViewById(R.id.overview_mv);
            cvMain = itemView.findViewById(R.id.card_view_mv);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(ResultsItemMovie resultsItemMovie);
    }
}
