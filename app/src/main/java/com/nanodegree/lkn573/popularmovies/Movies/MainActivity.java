package com.nanodegree.lkn573.popularmovies.Movies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

import com.nanodegree.lkn573.popularmovies.Config.NetworkUtil;
import com.nanodegree.lkn573.popularmovies.Core.CoreActivity;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.R;

public class MainActivity extends CoreActivity implements CoreFragment.OnFragmentInteractionListener, NetworkErrorFragment
    .onRetryListener, MoviesOverviewFragment.onMoviesOverviewNetworkConnectionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    MoviesOverviewFragment moviesOverviewFragment;

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        moviesOverviewFragment = new MoviesOverviewFragment();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isInternetConnected();
        Log.d(TAG, "onCreate: called");
    }

    private void displayNetworkErrorFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_container, new NetworkErrorFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void displayMoviesFragment() {
        Log.d(TAG, "displayMoviesFragment: new Fragment Created");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_container, moviesOverviewFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // getSupportFragmentManager().putFragment(outState, "mContent", moviesOverviewFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void retryNetworkClicked() {
        isInternetConnected();
    }

    public void isInternetConnected(){
        if(NetworkUtil.isNetworkConnected(getBaseContext())){
            displayMoviesFragment();
        }else{
            displayNetworkErrorFragment();
        }
    }

    @Override
    public void isNetworkConnectionLost() {
        isInternetConnected();
    }
}
