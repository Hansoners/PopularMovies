package popularmovies.hanson.android.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    public static int PAGE = 1;
    public static String API_KEY = "35740caead749a7b9336c86105523cc2";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesApiService api = RetrofitClientInstance.getRetrofitInstance().create(MoviesApiService.class);
        Call<Movies> call = api.getMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                Movies movies = response.body();
                List<Movies.ResultsBean> listOfMovies = movies.getResults();


                mRecyclerView = findViewById(R.id.recyclerView);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                mMoviesAdapter = new MoviesAdapter(listOfMovies, MainActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mMoviesAdapter);

            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
