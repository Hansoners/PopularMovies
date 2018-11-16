package popularmovies.hanson.android.popularmovies;

import java.util.List;

public interface OnTrailersCallback {
    void onSuccess(List<Trailers> trailers);
    void onError();
}

