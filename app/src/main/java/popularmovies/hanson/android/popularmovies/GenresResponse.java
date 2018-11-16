package popularmovies.hanson.android.popularmovies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse {

    @SerializedName("genres")
    @Expose
    private List<Genres> genres;

    public GenresResponse(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Genres> getGenres() {
        return genres;
    }

}
