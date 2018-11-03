package popularmovies.hanson.android.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class MoviesViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView movieTitle;

    MoviesViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        movieTitle = itemView.findViewById(R.id.movie_title);
    }
}
