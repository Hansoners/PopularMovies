package popularmovies.hanson.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movies.ResultsBean> mMoviesList;
    private List<Genres> mGenresList;
    private Context mCtx;
    private SparseBooleanArray mCheckedItems = new SparseBooleanArray();

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
    public void onBindViewHolder(@NonNull final MoviesViewHolder moviesViewHolder, int i) {
        Movies.ResultsBean movies = mMoviesList.get(i);
        moviesViewHolder.genreTitle.setText(getGenres(movies.getGenre_ids()));
        moviesViewHolder.movieTitle.setText(movies.getTitle());
        Picasso.get().load(movies.getPoster_path())
                .placeholder(R.color.colorPrimaryDark)
                .into(moviesViewHolder.imageView);

        moviesViewHolder.favBtn.setChecked(mCheckedItems.get(i));

        moviesViewHolder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = moviesViewHolder.getAdapterPosition();
                final boolean newValue = moviesViewHolder.favBtn.isChecked();

                mCheckedItems.put(position, newValue);

                Snackbar snackbar;
                if (newValue) {
                    snackbar = Snackbar.make(v, "Favorited!", Snackbar.LENGTH_SHORT);
                } else {
                    snackbar = Snackbar.make(v, "Unfavorited!", Snackbar.LENGTH_SHORT);
                }
                snackbar.show();

            }
        });

    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView movieTitle, genreTitle;
        CheckBox favBtn;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            genreTitle = itemView.findViewById(R.id.genre_title);
            favBtn = itemView.findViewById(R.id.favorite_button);
        }


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