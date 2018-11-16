package popularmovies.hanson.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movies.ResultsBean> mMoviesList;
    private List<Movies.ResultsBean> mFavoriteList;
    private List<Genres> mGenresList;
    private Map<String, Boolean> map = new HashMap<>();
    private Context mCtx;

    MoviesAdapter(List<Movies.ResultsBean> mMoviesList, List<Genres> mGenresList, Context mCtx, List<Movies.ResultsBean> mFavoriteList) {
        this.mMoviesList = mMoviesList;
        this.mGenresList = mGenresList;
        this.mFavoriteList = mFavoriteList;
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
        final Movies.ResultsBean movies = mMoviesList.get(i);
        moviesViewHolder.genreTitle.setText(getGenres(movies.getGenre_ids()));
        moviesViewHolder.movieTitle.setText(movies.getTitle());
        Picasso.get().load(movies.getPoster_path())
                .into(moviesViewHolder.imageView);

        for (Movies.ResultsBean m : mFavoriteList) {
            map.put(m.getTitle(), true);
        }

        if (map.containsKey(movies.getTitle())) {
            if (map.get(movies.getTitle()))
                moviesViewHolder.favBtn.setChecked(true);
        } else moviesViewHolder.favBtn.setChecked(false);

        moviesViewHolder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = moviesViewHolder.getAdapterPosition();
                Movies.ResultsBean movies = mMoviesList.get(position);
                final boolean newValue = moviesViewHolder.favBtn.isChecked();

                if (newValue && (!mFavoriteList.contains(mMoviesList.get(position)))) {
                    map.put(movies.getTitle(), true);
                    mFavoriteList.add(mMoviesList.get(position));
                    moviesViewHolder.favBtn.setChecked(true);
                } else {
                    map.put(movies.getTitle(), false);
                    mFavoriteList.remove(mFavoriteList.indexOf(mMoviesList.get(position)));
                    moviesViewHolder.favBtn.setChecked(false);
                }

                Snackbar snackbar;
                if (newValue) {
                    snackbar = Snackbar.make(v, "Favorited!", Snackbar.LENGTH_SHORT);
                } else {
                    snackbar = Snackbar.make(v, "Unfavorited!", Snackbar.LENGTH_SHORT);
                }
                snackbar.show();

            }
        });

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mCtx);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String jsonMovies = gson.toJson(mFavoriteList);
        prefsEditor.putString("FavList", jsonMovies);
        prefsEditor.apply();

        moviesViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieActivity.class);
                intent.putExtra("movie", movies);
                intent.putExtra("genreList", (Serializable) mGenresList);
                v.getContext().startActivity(intent);
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

    public void addMovies(List<Movies.ResultsBean> moviesAdd, List<Movies.ResultsBean> favList) {
        mMoviesList.addAll(moviesAdd);
        mFavoriteList = favList;
        notifyDataSetChanged();
    }

    public void clearMovies() {
        mMoviesList.clear();
        notifyDataSetChanged();
    }

}