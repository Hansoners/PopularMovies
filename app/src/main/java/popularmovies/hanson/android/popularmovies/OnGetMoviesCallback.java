package popularmovies.hanson.android.popularmovies;
import android.graphics.Movie;

import java.util.List;

public interface OnGetMoviesCallback {
    void onSuccess(List<Movies.ResultsBean> movies, int page);
    void onError();
}