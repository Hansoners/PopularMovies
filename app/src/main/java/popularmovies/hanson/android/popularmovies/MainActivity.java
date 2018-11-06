package popularmovies.hanson.android.popularmovies;

import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort:
                showSortMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortMenu() {
        PopupMenu sortMenu = new PopupMenu(this, findViewById(R.id.sort));

        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                PAGE = 1;

                switch (item.getItemId()) {
                    case R.id.popular:
                        CATEGORY = "popular";
                        getMovies(CATEGORY, PAGE);
                        return true;
                    case R.id.top_rated:
                        CATEGORY = "top_rated";
                        getMovies(CATEGORY, PAGE);
                        return true;
                    default:
                        return false;
                }

            }
        });

        sortMenu.inflate(R.menu.menu_movies_sort);
        sortMenu.show();
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
                        getMovies(CATEGORY, PAGE + 1);
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
                getMovies(CATEGORY, PAGE);
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMovies(String category, int page) {
        newMovies = true;
        CATEGORY = category;
        moviesRepository.getMovies(category, page, new OnGetMoviesCallback() {

            @Override
            public void onSuccess(List<Movies.ResultsBean> movies, int page) {
                if (mMoviesAdapter == null) {
                    mMoviesAdapter = new MoviesAdapter(movies, genresList, MainActivity.this);
                    mRecyclerView.setAdapter(mMoviesAdapter);
                } else {

                    if (page == 1) {
                        mMoviesAdapter.clearMovies();
                    }

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
