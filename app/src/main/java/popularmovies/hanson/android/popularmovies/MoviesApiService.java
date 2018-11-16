package popularmovies.hanson.android.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApiService {
    @GET("/3/movie/{category}")
    Call<Movies> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page);

    @GET("/3/genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );


    @GET("/3/movie/{movie_id}/videos")
    Call<TrailersResponse> getTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/movie/{movie_id}/reviews")
    Call<ReviewsResponse> getReviews(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );
}



