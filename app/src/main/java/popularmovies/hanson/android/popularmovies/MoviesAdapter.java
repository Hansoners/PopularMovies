package popularmovies.hanson.android.popularmovies;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private List<Movies.ResultsBean> mMoviesList;
    private List<Genres> mGenresList;
    private Context mCtx;

    MoviesAdapter(List<Movies.ResultsBean> mMoviesList, List<Genres> mGenresList, Context mCtx) {
        this.mMoviesList = mMoviesList;
        this.mGenresList = mGenresList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.movie_row, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        Movies.ResultsBean movies = mMoviesList.get(i);
        moviesViewHolder.genreTitle.setText(getGenres(movies.getGenre_ids()));
        moviesViewHolder.movieTitle.setText(movies.getTitle());
        Picasso.get().load(movies.getPoster_path())
                .placeholder(R.color.colorPrimaryDark)
                .into(moviesViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    private String getGenres(List<Integer> genreIds) {
        List<String> movieGenres = new ArrayList<>();
        for (int genreId : genreIds) {
            for (Genres genre : mGenresList) {
                if (genre.getId() == genreId) {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", movieGenres);
    }

    public void addMovies(List<Movies.ResultsBean> moviesAdd) {
        mMoviesList.addAll(moviesAdd);
        notifyDataSetChanged();
    }

    public void clearMovies() {
        mMoviesList.clear();
        notifyDataSetChanged();
    }
}



