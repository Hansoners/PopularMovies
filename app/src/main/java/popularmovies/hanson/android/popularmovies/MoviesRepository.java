package popularmovies.hanson.android.popularmovies;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static String API_KEY = "35740caead749a7b9336c86105523cc2";
    private static String LANGUAGE = "en-US";
    private static String CATEGORY = "popular";
    private static MoviesRepository moviesRepository;

    private MoviesApiService api;

    private MoviesRepository(MoviesApiService api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (moviesRepository == null) {
            moviesRepository = new MoviesRepository(RetrofitClientInstance.getRetrofitInstance().create(MoviesApiService.class));
        }
        return moviesRepository;
    }

    public void getMovies(int page, final OnGetMoviesCallback cb) {
        api.getMovies(CATEGORY, API_KEY, LANGUAGE, page)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                        if (response.isSuccessful()) {
                            Movies movies = response.body();
                            assert movies != null;
                            List<Movies.ResultsBean> listOfMovies = movies.getResults();
                            cb.onSuccess(listOfMovies, movies.getPage());
                        } else cb.onError();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                        cb.onError();
                    }
                });
    }

    public void getGenres(final OnGenresCallback cb) {
        api.getGenres(API_KEY, LANGUAGE)
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GenresResponse> call, @NonNull Response<GenresResponse> response) {
                        if (response.isSuccessful()) {
                            GenresResponse genresResponse = response.body();
                            assert genresResponse != null;
                            List<Genres> listofGenres = genresResponse.getGenres();
                            cb.onSuccess(listofGenres);
                        } else cb.onError();
                    }

                    @Override
                    public void onFailure(@NonNull Call<GenresResponse> call, @NonNull Throwable t) {
                        cb.onError();
                    }
                });
    }
}
