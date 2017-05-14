package com.nanodegree.lkn573.popularmovies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prade on 1/7/.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {
    public static final String TAG = MoviesDbHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "movies.db";

    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_MOVIE_TABLE = "CREATE TABLE " +
            MoviesContract.MovieEntry.MOVIE_TABLE_NAME + "(" +
            MoviesContract.MovieEntry.MOVIE_ID + " INTEGER PRIMARY KEY, " +
            MoviesContract.MovieEntry.MOVIE_TITLE + " TEXT NOT NULL, " +
            MoviesContract.MovieEntry.MOVIE_OVERVIEW + " TEXT NOT NULL, " +
            MoviesContract.MovieEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
            MoviesContract.MovieEntry.MOVIE_POSTER_PATH + " TEXT, " +
            MoviesContract.MovieEntry.MOVIE_RATING + " REAL);" ;

    public static final String DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.MOVIE_TABLE_NAME;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_MOVIE_TABLE);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                MoviesContract.MovieEntry.MOVIE_TABLE_NAME + "'");
        onCreate(sqLiteDatabase);
    }
}
