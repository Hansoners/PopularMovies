package popularmovies.hanson.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieGenres;
    private TextView movieOverview;
    private MoviesRepository moviesRepository;
    private TextView movieOverviewLabel;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private LinearLayout movieTrailers;
    private LinearLayout movieReviews;
    private ImageView trailerThumbnail;
    private TextView trailersLabel;
    List<Genres> genresList;
    private static String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent i = getIntent();
        moviesRepository = MoviesRepository.getInstance();
        Movies.ResultsBean movie = (Movies.ResultsBean) i.getSerializableExtra("movie");
        genresList = (List<Genres>) i.getSerializableExtra("genreList");

        initUI();
        setupToolbar();
        bindUI(movie);
        getTrailers(movie);

    }


    private void initUI() {
        movieBackdrop = findViewById(R.id.movieDetailsBackdrop);
        movieTitle = findViewById(R.id.movieDetailsTitle);
        movieGenres = findViewById(R.id.movieDetailsGenres);
        movieOverview = findViewById(R.id.movieDetailsOverview);
        movieOverviewLabel = findViewById(R.id.summaryLabel);
        trailersLabel = findViewById(R.id.trailersLabel);
        movieReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieRating = findViewById(R.id.movieDetailsRating);
        movieReviews = findViewById(R.id.movieReviews);
        trailerThumbnail = findViewById(R.id.trailer_thumbnail);
    }

    private void bindUI(Movies.ResultsBean movie) {
        movieTitle.setText(movie.getTitle());
        movieTitle.setText(movie.getTitle());
        movieOverviewLabel.setVisibility(View.VISIBLE);
        movieOverview.setText(movie.getOverview());
        movieRating.setVisibility(View.VISIBLE);
        movieRating.setRating((float) (movie.getVote_average() / 2));
        movieReleaseDate.setText(movie.getRelease_date());
        movieGenres.setText(getGenres(movie.getGenre_ids(), genresList));
        Picasso.get().load(movie.getBackdrop_path())
                .placeholder(R.color.colorPrimaryDark)
                .into(movieBackdrop);


    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void getTrailers(Movies.ResultsBean movies) {
        moviesRepository.getTrailers(movies.getId(), new OnTrailersCallback() {
            @Override
            public void onSuccess(final List<Trailers> trailers) {
                trailersLabel.setVisibility(View.VISIBLE);

                trailerThumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTrailer(String.format(YOUTUBE_VIDEO_URL, trailers.get(0).getKey()));
                    }
                });
                Picasso.get()
                        .load(String.format(YOUTUBE_THUMBNAIL_URL, trailers.get(0).getKey()))
                        .placeholder(R.color.colorPrimaryDark)
                        .into(trailerThumbnail);
            }

            @Override
            public void onError() {
                trailersLabel.setVisibility(View.GONE);

            }
        });
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

    private void showTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}



