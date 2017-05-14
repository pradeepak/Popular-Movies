package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiClient;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiInterface;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Database.MoviesContract;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.R;

public class MovieDetailsFragment extends CoreFragment {

    public static final String TAG = MovieDetailsFragment.class.getSimpleName();

    Movie selectedMovieItem;

    ImageView moviePoster;

    TextView movieTitle, overview, movieRating, releaseDate;

    RatingBar movieRatingBar;

    float numericalRating;

    FloatingActionButton favoritesButton;

    MoviesApiInterface moviesApiInterface;

    public static MovieDetailsFragment newInstance(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable("SelectedMovie", movie);

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            selectedMovieItem = arguments.getParcelable("SelectedMovie");
            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        if (isMovieInFavorites(selectedMovieItem.getId()) > 0) {
            selectedMovieItem.setisFavourite(true);
        } else {
            selectedMovieItem.setisFavourite(false);
        }
        setMovieContent();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesApiInterface = MoviesApiClient.getRetrofitClient().create(MoviesApiInterface.class);
    }

    private void setMovieContent() {
        Glide.with(getContext()).load(selectedMovieItem.getPosterURL()).error(R.mipmap.ic_launcher).into(moviePoster);
        movieTitle.setText(selectedMovieItem.getTitle());
        releaseDate.setText(selectedMovieItem.getReleaseDate());
        numericalRating = (float) Double.parseDouble(selectedMovieItem.getVoteAverage());
        movieRating.setText(getResources().getString(R.string.movie_vote_average,selectedMovieItem.getVoteAverage()));
        movieRatingBar.setRating(numericalRating);
        overview.setText(selectedMovieItem.getOverView());
        favoritesButton.setImageResource(selectedMovieItem.isFavourite() ? R.drawable.star_circle : R.drawable.plus);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMovieItem.isFavourite()) {
                    favoritesButton.setSelected(false);
                    favoritesButton.setImageResource(R.drawable.plus);
                    selectedMovieItem.setisFavourite(false);
                    getActivity().getContentResolver().delete(
                            MoviesContract.MovieEntry.CONTENT_URI,
                            MoviesContract.MovieEntry.MOVIE_ID + " = ?",
                            new String[]{Integer.toString(selectedMovieItem.getId())}
                    );

                } else {
                    favoritesButton.setSelected(true);
                    selectedMovieItem.setisFavourite(true);
                    favoritesButton.setImageResource(R.drawable.star_circle);

                    ContentValues values = new ContentValues();
                    values.put(MoviesContract.MovieEntry.MOVIE_ID, selectedMovieItem.getId());
                    values.put(MoviesContract.MovieEntry.MOVIE_TITLE, selectedMovieItem.getTitle());
                    values.put(MoviesContract.MovieEntry.MOVIE_OVERVIEW, selectedMovieItem.getOverView());
                    values.put(MoviesContract.MovieEntry.MOVIE_RELEASE_DATE, selectedMovieItem.getReleaseDate());
                    values.put(MoviesContract.MovieEntry.MOVIE_POSTER_PATH, selectedMovieItem.getPosterPath());
                    values.put(MoviesContract.MovieEntry.MOVIE_RATING, selectedMovieItem.getVoteAverage());
                    getActivity().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI,
                            values);
                }
            }
        });
    }

    private void initializeViews(View view) {
        favoritesButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        moviePoster = (ImageView) view.findViewById(R.id.moviePosterImage);
        overview = (TextView) view.findViewById(R.id.movieOverview);
        movieTitle = (TextView) view.findViewById(R.id.detailMovieTitle);
        releaseDate = (TextView) view.findViewById(R.id.movieReleaseDate);
        movieRating = (TextView) view.findViewById(R.id.movieRating);
        movieRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
    }

    private int isMovieInFavorites(int id) {
        Cursor cursor = getActivity().getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                null,
                MoviesContract.MovieEntry.MOVIE_ID + " = ?", // selection
                new String[]{Integer.toString(id)},   // selectionArgs
                null
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }

    @Override
    public void onButtonPressed(Uri uri) {
        super.onButtonPressed(uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
