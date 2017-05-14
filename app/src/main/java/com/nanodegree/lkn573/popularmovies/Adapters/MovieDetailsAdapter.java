package com.nanodegree.lkn573.popularmovies.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nanodegree.lkn573.popularmovies.Config.NetworkUtil;
import com.nanodegree.lkn573.popularmovies.Models.Movie;

/**
 * Created by prade on 2/20/2017.
 */

public class MovieDetailsAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;

    Movie movieItem;

    FragmentManager fragmentManager;

    NetworkUtil.detailsNetworkConnectionListener networkConnectionListener;

    private static final String tabTitles[] = new String[]{"Movies Overview", "Videos", "Reviews"};

    public MovieDetailsAdapter(FragmentManager fm, Movie movieItem, NetworkUtil.detailsNetworkConnectionListener networkConnectionListener) {
        super(fm);
        this.fragmentManager = fm;
        this.movieItem = movieItem;
        this.networkConnectionListener = networkConnectionListener;
    }

    @Override
    public Fragment getItem(int position) {

        return networkConnectionListener.isNetworkConnectionLost(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
