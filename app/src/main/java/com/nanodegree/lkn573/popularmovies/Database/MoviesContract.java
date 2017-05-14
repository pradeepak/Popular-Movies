package com.nanodegree.lkn573.popularmovies.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by prade on 1/7/2017.
 */

public class MoviesContract {
    public static final String CONTENT_AUTHORITY = "com.nanodegree.lkn573.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final String MOVIE_TABLE_NAME = "movie";

        public static final String MOVIE_ID = "id";

        public static final String MOVIE_TITLE = "title";

        public static final String MOVIE_OVERVIEW = "overview";

        public static final String MOVIE_RELEASE_DATE = "release_date";

        public static final String MOVIE_POSTER_PATH = "poster_path";

        public static final String MOVIE_RATING = "rating";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_TABLE_NAME).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_TABLE_NAME;

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildFavoriteMoviesUri() {
            return CONTENT_URI.buildUpon().appendPath("favoriteMovies").build();
        }
    }
}
