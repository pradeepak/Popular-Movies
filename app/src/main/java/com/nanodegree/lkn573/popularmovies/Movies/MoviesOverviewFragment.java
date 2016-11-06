package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nanodegree.lkn573.popularmovies.Adapters.MovieArrayAdapter;
import com.nanodegree.lkn573.popularmovies.Config.NetworkUtil;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Core.GetMovieDataTask;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.R;

import java.util.Arrays;


public class MoviesOverviewFragment extends CoreFragment implements GetMovieDataTask.OnMoviesRetrievedListener,
        AdapterView.OnItemClickListener {

    public static final String TAG = MoviesOverviewFragment.class.getSimpleName();

    onMoviesOverviewNetworkConnectionListener mMoviesOverviewListener;

    MovieArrayAdapter mMovieArrayAdapter;

    GridView moviesGridView;

    SortCriteria sortCriteria = null;

    Movie[] retreivedMovies;

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            sortCriteria = SortCriteria.BYPOPULARITY;
        }
        if (savedInstanceState != null) {
            sortCriteria = (SortCriteria) savedInstanceState.getSerializable("SortCriteria");
        }
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movies_overview, container, false);
        if (savedInstanceState == null) {
            getMovieData(sortCriteria);
        } else {
            sortCriteria = (SortCriteria) savedInstanceState.getSerializable("SortCriteria");
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.moviesToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void getMovieData(SortCriteria mSortCriteria) {
        Log.d(TAG, "getMovieData: Called" + mSortCriteria);
        if (mSortCriteria == SortCriteria.BYPOPULARITY) {
            sortCriteria = mSortCriteria;
            new GetMovieDataTask(this).execute("Popular Movies");
        } else if (mSortCriteria == SortCriteria.BYRATING) {
            sortCriteria = mSortCriteria;
            new GetMovieDataTask(this).execute("Top Rated");
        }
    }

    @Override
    public void onButtonPressed(Uri uri) {
        super.onButtonPressed(uri);
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof onMoviesOverviewNetworkConnectionListener) {
            mMoviesOverviewListener = (onMoviesOverviewNetworkConnectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMoviesOverviewListener = null;
    }

    @Override
    public void onMoviesRetrieved(Movie[] movies) {
        retreivedMovies = movies;
        for (int i = 0; i < movies.length; i++) {
            mMovieArrayAdapter = new MovieArrayAdapter(getContext(), 0, Arrays.asList(movies));
            moviesGridView.setAdapter(mMovieArrayAdapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getContext(), MovieDetails.class);
        Movie movieItem = (Movie) adapterView.getItemAtPosition(i);
        intent.putExtra("SelectedMovie", movieItem);
        startActivity(intent);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        if (sortCriteria == SortCriteria.BYPOPULARITY) {
            menu.findItem(R.id.sortByPopularity).setChecked(true);
        } else if (sortCriteria == SortCriteria.BYRATING) {
            menu.findItem(R.id.sortByRating).setChecked(true);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            sortCriteria = (SortCriteria) savedInstanceState.getSerializable("SortCriteria");
        }
        moviesGridView = (GridView) getActivity().findViewById(R.id.moviesGrid);
        moviesGridView.setOnItemClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("SortCriteria", sortCriteria);
//        outState.putParcelableArray("MovieValues", retreivedMovies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sortByPopularity:
                sortCriteria = SortCriteria.BYPOPULARITY;
                if (!item.isChecked()) {
                    if (NetworkUtil.isNetworkConnected(getContext())) {
                        getMovieData(sortCriteria);
                    }
                    mMoviesOverviewListener.isNetworkConnectionLost();
                }
                item.setChecked(!item.isChecked());
                break;
            case R.id.sortByRating:
                sortCriteria = SortCriteria.BYRATING;
                if (!item.isChecked()) {
                    if (NetworkUtil.isNetworkConnected(getContext())) {
                        getMovieData(sortCriteria);
                    }
                    mMoviesOverviewListener.isNetworkConnectionLost();
                }
                item.setChecked(!item.isChecked());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private enum SortCriteria {
        BYPOPULARITY,
        BYRATING
    }

    public interface onMoviesOverviewNetworkConnectionListener {
        void isNetworkConnectionLost();
    }
}
