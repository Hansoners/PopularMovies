package popularmovies.hanson.android.popularmovies;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Movies implements Serializable {

    private int page;
    private int total_results;
    private int total_pages;
    private List<ResultsBean> results;

    public int getPage() {
        return page;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {
        /**
         * vote_count : 1833
         * id : 335983
         * video : false
         * vote_average : 6.6
         * title : Venom
         * popularity : 363.221
         * poster_path : /2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg
         * original_language : en
         * original_title : Venom
         * genre_ids : [878]
         * backdrop_path : /VuukZLgaCrho2Ar8Scl9HtV3yD.jpg
         * adult : false
         * overview : When Eddie Brock acquires the powers of a symbiote, he will have to release his alter-ego "Venom" to save his life.
         * release_date : 2018-10-03
         */

        private int vote_count;
        private int id;
        private boolean video;
        private double vote_average;
        private String title;
        private double popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private String backdrop_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private boolean favorited;
        private List<Integer> genre_ids;

        public boolean isFavorited() {
            return favorited;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return "https://image.tmdb.org/t/p/w500" + poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getBackdrop_path() {
            return "https://image.tmdb.org/t/p/w500" + backdrop_path;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Movies.ResultsBean) {
                Movies.ResultsBean p = (Movies.ResultsBean) o;
                return Objects.equals(p.getId(), this.id);
            }
            return false;
        }

    }
}
