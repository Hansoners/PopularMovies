package popularmovies.hanson.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieGenres;
    private TextView movieOverview;
    private TextView movieOverviewLabel;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private LinearLayout movieTrailers;
    private LinearLayout movieReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent i = getIntent();
        Movies.ResultsBean movie = (Movies.ResultsBean)i.getSerializableExtra("movie");
        List<Genres> genresList = (List<Genres>)i.getSerializableExtra("genreList");

        initUI();
        setupToolbar();

        Picasso.get().load(movie.getBackdrop_path())
                .placeholder(R.color.colorPrimaryDark)
                .into(movieBackdrop);

        movieTitle.setText(movie.getTitle());
        movieTitle.setText(movie.getTitle());
        movieOverviewLabel.setVisibility(View.VISIBLE);
        movieOverview.setText(movie.getOverview());
        movieRating.setVisibility(View.VISIBLE);
        movieRating.setRating((float) (movie.getVote_average() / 2));
        movieReleaseDate.setText(movie.getRelease_date());
        movieGenres.setText(getGenres(movie.getGenre_ids(), genresList));

    }


    private void initUI() {
        movieBackdrop = findViewById(R.id.movieDetailsBackdrop);
        movieTitle = findViewById(R.id.movieDetailsTitle);
        movieGenres = findViewById(R.id.movieDetailsGenres);
        movieOverview = findViewById(R.id.movieDetailsOverview);
        movieOverviewLabel = findViewById(R.id.summaryLabel);
        movieReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieRating = findViewById(R.id.movieDetailsRating);
        movieTrailers = findViewById(R.id.movieTrailers);
        movieReviews = findViewById(R.id.movieReviews);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private String getGenres(List<Integer> genreIds, List<Genres> genresList) {
        List<String> movieGenres = new ArrayList<>();
        for (int genreId : genreIds) {
            for (Genres genre : genresList) {
                if (genre.getId() == genreId) {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", movieGenres);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
