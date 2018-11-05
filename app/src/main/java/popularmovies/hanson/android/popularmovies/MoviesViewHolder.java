package popularmovies.hanson.android.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MoviesViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView movieTitle, genreTitle;

    MoviesViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        movieTitle = itemView.findViewById(R.id.movie_title);
        genreTitle = itemView.findViewById(R.id.genre_title);
    }


}
