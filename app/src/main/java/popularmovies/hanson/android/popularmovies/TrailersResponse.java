package popularmovies.hanson.android.popularmovies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersResponse {

    @SerializedName("results")
    @Expose
    private List<Trailers> trailers;

    public List<Trailers> getTrailers() {
        return trailers;
    }
}
