package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.R;

public class MovieDetailsFragment extends CoreFragment {

    public static final String TAG = MovieDetailsFragment.class.getSimpleName();

    Movie selectedMovieItem;

    ImageView moviePoster;

    TextView movieTitle, overview, movieRating, releaseDate, toolBarTitle;

    RatingBar movieRatingBar;

    float numerialRating;

    Toolbar toolbar;

    AppBarLayout appBarLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            selectedMovieItem = arguments.getParcelable("SelectedMovie");
            //Log.d(TAG, "onCreate: "+selectedMovieItem.getTitle());

            setHasOptionsMenu(true);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_details, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);

        if(toolbar!= null) {

            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Movie Details");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();

                }
            });
        }
        setMovieContent();

    }

    private void setMovieContent() {

        Glide.with(getContext()).load(selectedMovieItem.getPosterURL()).error(R.mipmap.ic_launcher).into(moviePoster);
        movieTitle.setText(selectedMovieItem.getTitle());
        releaseDate.setText(selectedMovieItem.getReleaseDate());
        numerialRating = (float) Double.parseDouble(selectedMovieItem.getVoteAverage());
        String maximumRating = "/10";
        movieRating.setText(selectedMovieItem.getVoteAverage() + maximumRating);
        movieRatingBar.setRating(numerialRating);
        overview.setText(selectedMovieItem.getOverView());

    }

    private void initializeViews(View view) {
        moviePoster = (ImageView) view.findViewById(R.id.moviePosterImage);
        overview = (TextView) view.findViewById(R.id.movieOverview);
        movieTitle = (TextView) view.findViewById(R.id.detailMovieTitle);
        releaseDate = (TextView) view.findViewById(R.id.movieReleaseDate);
        movieRating = (TextView) view.findViewById(R.id.movieRating);
        movieRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        toolbar = (Toolbar) view.findViewById(R.id.movieDetailsToolbar);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbarLayout);
        //toolBarTitle = (TextView) view.findViewById(R.id.main_toolbar_title);

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

    public void getMovieObject() {

    }


}
