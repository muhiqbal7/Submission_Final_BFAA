package com.example.submissionfinalbfaa.view.viewmovie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.submissionfinalbfaa.R;
import com.example.submissionfinalbfaa.database.AplicationDatabase;
import com.example.submissionfinalbfaa.database.CatalogDB;
import com.example.submissionfinalbfaa.model.moviemodel.ResultsItemMovie;

import java.util.List;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    ResultsItemMovie listMovie;
    boolean favorite = false;
    private AplicationDatabase aplicationDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView mvTitle, mvRelease, mvScore, mvId, mvVoteCount, mvLanguage, mvOverview;
        ImageView mvPoster;
        final CheckBox mvLikeBox;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_movie);
        }

        mvTitle = findViewById(R.id.text_title_mv);
        mvRelease = findViewById(R.id.text_release_mv);
        mvScore = findViewById(R.id.text_score_mv);
        mvId = findViewById(R.id.text_id_mv);
        mvVoteCount = findViewById(R.id.text_vote_mv);
        mvLanguage = findViewById(R.id.text_ori_text_mv);
        mvOverview = findViewById(R.id.text_overview_mv);
        mvPoster = findViewById(R.id.poster_mv);
        mvLikeBox = findViewById(R.id.like_box);

        listMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        mvTitle.setText(listMovie.getTitle());
        mvRelease.setText(listMovie.getRelease_date());
        mvScore.setText(listMovie.getVote_average().toString());
        mvId.setText(String.valueOf(listMovie.getId()));
        mvVoteCount.setText(String.valueOf(listMovie.getVote_count()));
        mvLanguage.setText(listMovie.getOriginal_language());
        mvOverview.setText(listMovie.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w154" + listMovie.getPoster_path())
                .apply(new RequestOptions().override(350, 550))
                .into(mvPoster);

        aplicationDatabase = AplicationDatabase.initDatabase(getApplicationContext());

        checkedFavorite();
        if (favorite){
            mvLikeBox.setChecked(true);
        }
        mvLikeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    saveFavorite();
                    favorite = true;
                    mvLikeBox.setChecked(true);
                }
                else {
                    deleteFavorite();
                    favorite = false;
                    mvLikeBox.setChecked(false);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home){
                finish();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void saveFavorite(){
        CatalogDB catalogDB = new CatalogDB();
        catalogDB.setId(listMovie.getId());
        catalogDB.setRelease_date(listMovie.getRelease_date());
        catalogDB.setVote_average(Double.parseDouble(listMovie.getVote_average().toString()));
        catalogDB.setTitle(listMovie.getTitle());
        catalogDB.setOriginal_language(listMovie.getOriginal_language());
        catalogDB.setVote_count(listMovie.getVote_count());
        catalogDB.setPoster_path(listMovie.getPoster_path());
        catalogDB.setOverview(listMovie.getOverview());
        catalogDB.setCategory("movie");
        aplicationDatabase.catalogDAO().insert(catalogDB);
    }

    public void deleteFavorite(){
        CatalogDB catalogDB = new CatalogDB();
        catalogDB.setId(listMovie.getId());
        catalogDB.setRelease_date(listMovie.getRelease_date());
        catalogDB.setVote_average(Double.parseDouble(listMovie.getVote_average().toString()));
        catalogDB.setTitle(listMovie.getTitle());
        catalogDB.setOriginal_language(listMovie.getOriginal_language());
        catalogDB.setVote_count(listMovie.getVote_count());
        catalogDB.setPoster_path(listMovie.getPoster_path());
        catalogDB.setOverview(listMovie.getOverview());
        catalogDB.setCategory("movie");
        aplicationDatabase.catalogDAO().delete(catalogDB);
    }

    public void checkedFavorite(){
        List<CatalogDB> getById = aplicationDatabase.catalogDAO().getById(listMovie.getId());
        if (!getById.isEmpty()){
            favorite = true;
        }
    }
}
