package popularmovies.hanson.android.popularmovies;

import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private MoviesRepository moviesRepository;
    private List<Genres> genresList;
    private boolean newMovies;
    public static int PAGE = 1;
    public static String API_KEY = "35740caead749a7b9336c86105523cc2";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesRepository = MoviesRepository.getInstance();

        //RecyclerView setup
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        onScrollListener();

        getGenres();
    }

    private void onScrollListener() {
        final GridLayoutManager gManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(gManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int itemCount = gManager.getItemCount();
                int childCount = gManager.getChildCount();
                int firstVisible = gManager.findFirstVisibleItemPosition();

                if (firstVisible + childCount >= itemCount / 2) {
                    if (!newMovies) {
                        getMovies(PAGE + 1);
                    }
                }
            }
        });

    }

    private void getGenres() {
        moviesRepository.getGenres(new OnGenresCallback() {
            @Override
            public void onSuccess(List<Genres> genres) {
                genresList = genres;
                getMovies(PAGE);
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMovies(int page) {
        newMovies = true;
        moviesRepository.getMovies(page, new OnGetMoviesCallback() {

            @Override
            public void onSuccess(List<Movies.ResultsBean> movies, int page) {
                if (mMoviesAdapter == null) {
                    mMoviesAdapter = new MoviesAdapter(movies, genresList, MainActivity.this);
                    mRecyclerView.setAdapter(mMoviesAdapter);
                } else {
                    mMoviesAdapter.addMovies(movies);
                }
                PAGE = page;
                newMovies = false;
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
