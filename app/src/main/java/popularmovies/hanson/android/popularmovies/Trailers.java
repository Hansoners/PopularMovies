package popularmovies.hanson.android.popularmovies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trailers {
    @SerializedName("key")
    @Expose
    private String key;

    public String getKey() {
        return key;
    }
}
