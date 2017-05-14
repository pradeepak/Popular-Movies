package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nanodegree.lkn573.popularmovies.Adapters.MovieDetailsAdapter;
import com.nanodegree.lkn573.popularmovies.Config.NetworkUtil;
import com.nanodegree.lkn573.popularmovies.Core.CoreActivity;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.R;

public class MovieDetails extends CoreActivity implements CoreFragment.OnFragmentInteractionListener, NetworkUtil.detailsNetworkConnectionListener,
        NetworkErrorFragment.onRetryListener {

    private static final String TAG = MovieDetails.class.getSimpleName();

    FragmentManager fragmentManager;

    Movie movieItem;

    Toolbar toolbar;

    TabLayout tabLayout;

    ViewPager viewPager;

    int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieItem = getMovieItemFromIntent();
        setContentView(R.layout.activity_movie_details);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        fragmentManager = getSupportFragmentManager();
        setUpToolBar();
        setTabLayout();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.movieDetailsToolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.movie_details);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    private void setTabLayout() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MovieDetailsAdapter(fragmentManager, movieItem, MovieDetails.this));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.primaryText));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //no implementation
    }

    public Movie getMovieItemFromIntent() {
        Intent intent = getIntent();
        return movieItem = intent.getParcelableExtra("SelectedMovie");
    }

    @Override
    public Fragment isNetworkConnectionLost(int position) {
        tabPosition = position;
        if (NetworkUtil.isNetworkConnected(getBaseContext())) {
            switch (position) {
                case 0:
                    return MovieDetailsFragment.newInstance(movieItem);
                case 1:
                    return MovieVideosFragment.newInstance(movieItem);
                case 2:
                    return MovieReviewsFragment.newInstance(movieItem);
            }
        } else {
            return NetworkErrorFragment.newInstance();
        }
        return null;
    }

    @Override
    public void retryNetworkClicked() {
        getTabFragment();
    }

    public void getTabFragment() {

        viewPager.getAdapter().notifyDataSetChanged();
    }
}
