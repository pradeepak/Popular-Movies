package com.nanodegree.lkn573.popularmovies.Movies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

import com.nanodegree.lkn573.popularmovies.Config.NetworkUtil;
import com.nanodegree.lkn573.popularmovies.Core.CoreActivity;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.R;

public class MainActivity extends CoreActivity implements CoreFragment.OnFragmentInteractionListener, NetworkErrorFragment
        .onRetryListener, NetworkUtil.networkConnectionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    MoviesOverviewFragment moviesOverviewFragment;

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            moviesOverviewFragment = new MoviesOverviewFragment();
        } else {
            moviesOverviewFragment = (MoviesOverviewFragment) fragmentManager.findFragmentByTag(MoviesOverviewFragment.TAG);
        }
        isInternetConnected();
        Log.d(TAG, "onCreate: called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void displayFragment(Fragment fragment) {
        Log.d(TAG, "displayMoviesFragment: new Fragment Created");
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment, MoviesOverviewFragment.TAG).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void retryNetworkClicked() {
        isInternetConnected();
    }

    public void isInternetConnected() {
        if (NetworkUtil.isNetworkConnected(getBaseContext())) {
            displayFragment(moviesOverviewFragment);
        } else {
            displayFragment(new NetworkErrorFragment());
        }
    }

    @Override
    public void isNetworkConnectionLost() {
        isInternetConnected();
    }
}
