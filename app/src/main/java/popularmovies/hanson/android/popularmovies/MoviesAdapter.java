package popularmovies.hanson.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private List<Movies.ResultsBean> mMoviesList;
    private Context mCtx;

    MoviesAdapter(List<Movies.ResultsBean> mMoviesList, Context mCtx) {
        this.mMoviesList = mMoviesList;
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
        Picasso.with(mCtx).load(movies.getPoster_path())
                .placeholder(R.color.colorPrimaryDark)
                .into(moviesViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

}
