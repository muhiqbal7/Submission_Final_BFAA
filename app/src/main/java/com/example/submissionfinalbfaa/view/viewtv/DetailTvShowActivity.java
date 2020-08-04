package com.example.submissionfinalbfaa.view.viewtv;

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
import com.example.submissionfinalbfaa.model.tvmodel.ResultsItemTv;

import java.util.List;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "extra_tv";
    ResultsItemTv listTvShow;
    boolean favorite = false;
    private AplicationDatabase aplicationDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView tvTitle, tvRelease, tvScore, tvVoteCount, tvStatus, tvId, tvLanguage, tvOverview;
        ImageView tvPoster;
        final CheckBox tvLikeBox;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_tv);
        }

        tvTitle = findViewById(R.id.title_tv);
        tvRelease = findViewById(R.id.text_release_tv);
        tvScore = findViewById(R.id.text_score_tv);
        tvId = findViewById(R.id.text_id_tv);
        tvVoteCount = findViewById(R.id.text_vote_tv);
        tvLanguage = findViewById(R.id.text_ori_text_tv);
        tvOverview = findViewById(R.id.text_overview_tv);
        tvPoster = findViewById(R.id.poster_tv);
        tvLikeBox = findViewById(R.id.like_box_tv);

        listTvShow = getIntent().getParcelableExtra(EXTRA_TV);

        tvTitle.setText(listTvShow.getOriginal_name());
        tvRelease.setText(listTvShow.getFirst_air_date());
        tvScore.setText(listTvShow.getVote_average().toString());
        tvId.setText(String.valueOf(listTvShow.getId()));
        tvVoteCount.setText(String.valueOf(listTvShow.getVote_count()));
        tvLanguage.setText(listTvShow.getOriginal_language());
        tvOverview.setText(listTvShow.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w154" + listTvShow.getPoster_path())
                .apply(new RequestOptions().override(350, 550))
                .into(tvPoster);
        aplicationDatabase = AplicationDatabase.initDatabase(getApplicationContext());

        checkedFavorite();
        if (favorite){
            tvLikeBox.setChecked(true);
        }
        tvLikeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    saveFavorite();
                    favorite = true;
                    tvLikeBox.setChecked(true);
                }
                else {
                    deleteFavorite();
                    favorite = false;
                    tvLikeBox.setChecked(false);
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
        catalogDB.setId(listTvShow.getId());
        catalogDB.setRelease_date(listTvShow.getFirst_air_date());
        catalogDB.setVote_average(Double.parseDouble(listTvShow.getVote_average().toString()));
        catalogDB.setTitle(listTvShow.getFirst_air_date());
        catalogDB.setOriginal_language(listTvShow.getOriginal_language());
        catalogDB.setVote_count(listTvShow.getVote_count());
        catalogDB.setPoster_path(listTvShow.getPoster_path());
        catalogDB.setOverview(listTvShow.getOverview());
        catalogDB.setCategory("tv");
        aplicationDatabase.catalogDAO().insert(catalogDB);
    }

    public void deleteFavorite(){
        CatalogDB catalogDB = new CatalogDB();
        catalogDB.setId(listTvShow.getId());
        catalogDB.setRelease_date(listTvShow.getFirst_air_date());
        catalogDB.setVote_average(Double.parseDouble(listTvShow.getVote_average().toString()));
        catalogDB.setTitle(listTvShow.getOriginal_name());
        catalogDB.setOriginal_language(listTvShow.getOriginal_language());
        catalogDB.setVote_count(listTvShow.getVote_count());
        catalogDB.setPoster_path(listTvShow.getPoster_path());
        catalogDB.setOverview(listTvShow.getOverview());
        catalogDB.setCategory("tv");
        aplicationDatabase.catalogDAO().delete(catalogDB);
    }

    public void checkedFavorite(){
        List<CatalogDB> getById = aplicationDatabase.catalogDAO().getById(listTvShow.getId());
        if (!getById.isEmpty()){
            favorite = true;
        }
    }
}
