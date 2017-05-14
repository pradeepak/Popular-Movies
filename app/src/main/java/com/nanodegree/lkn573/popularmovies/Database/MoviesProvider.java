package com.nanodegree.lkn573.popularmovies.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * MoviesProvider Class is used to to perform database actions.
 */

public class MoviesProvider extends ContentProvider {
    private static final int MOVIE = 100;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    MoviesDbHelper dbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, MoviesContract.MovieEntry.MOVIE_TABLE_NAME, MOVIE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projections, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case MOVIE: {
                cursor = dbHelper.getReadableDatabase().query(
                        MoviesContract.MovieEntry.MOVIE_TABLE_NAME,
                        projections,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return cursor;
            }

            default: {
                throw new UnsupportedOperationException("Unsupported Uri :" + uri);
            }
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                return MoviesContract.MovieEntry.CONTENT_TYPE;
            default: {
                throw new UnsupportedOperationException("Unsupported Uri :" + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri returnUri;

        SQLiteDatabase moviesDatabase = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case MOVIE: {
                long id = moviesDatabase.insert(MoviesContract.MovieEntry.MOVIE_TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = MoviesContract.MovieEntry.buildUri(id);
                else
                    throw new SQLException("Failed to insert into Table");
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unsupported Uri :" + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase moviesDatabase = dbHelper.getWritableDatabase();
        int numDeleted;
        if (null == selection) selection = "1";
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                numDeleted = moviesDatabase.delete(
                        MoviesContract.MovieEntry.MOVIE_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase moviesDatabase = dbHelper.getWritableDatabase();
        int numUpdated;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (uriMatcher.match(uri)) {
            case MOVIE: {
                numUpdated = moviesDatabase.update(MoviesContract.MovieEntry.MOVIE_TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase moviesDatabase = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int returnCount;

        switch (match) {
            case MOVIE:
                moviesDatabase.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = moviesDatabase.insert(MoviesContract.MovieEntry.MOVIE_TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    moviesDatabase.setTransactionSuccessful();
                } finally {
                    moviesDatabase.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

}