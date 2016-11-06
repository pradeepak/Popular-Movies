package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.nanodegree.lkn573.popularmovies.Core.CoreActivity;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.R;

public class MovieDetails extends CoreActivity implements CoreFragment.OnFragmentInteractionListener {

    private static final String TAG = MovieDetails.class.getSimpleName();

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;

    MovieDetailsFragment movieDetailsFragment;

    Movie movieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieDetailsFragment = new MovieDetailsFragment();
        movieItem = getMovieItemFromIntent();
        if (movieItem != null) {
            displayMoviesDetailsFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void displayMoviesDetailsFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("SelectedMovie", movieItem);
        movieDetailsFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.detailsActivity, movieDetailsFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public Movie getMovieItemFromIntent() {
        Intent intent = getIntent();
        return movieItem = intent.getParcelableExtra("SelectedMovie");
    }
}
