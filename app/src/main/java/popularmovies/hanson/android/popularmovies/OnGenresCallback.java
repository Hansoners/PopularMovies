package popularmovies.hanson.android.popularmovies;

import java.util.List;

public interface OnGenresCallback {

    void onSuccess(List<Genres> genres);
    void onError();

}
