package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.lkn573.popularmovies.Adapters.MoviesAdapter;
import com.nanodegree.lkn573.popularmovies.Adapters.RecyclerTouchListener;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiClient;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiInterface;
import com.nanodegree.lkn573.popularmovies.Config.NetworkUtil;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Database.MoviesContract;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.Models.MoviesResponse;
import com.nanodegree.lkn573.popularmovies.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesOverviewFragment extends CoreFragment implements RecyclerTouchListener.ClickListener {

    public static final String TAG = MoviesOverviewFragment.class.getSimpleName();
    private static final String[] MOVIE_COLUMNS = {
            MoviesContract.MovieEntry.MOVIE_ID,
            MoviesContract.MovieEntry.MOVIE_TITLE,
            MoviesContract.MovieEntry.MOVIE_OVERVIEW,
            MoviesContract.MovieEntry.MOVIE_RELEASE_DATE,
            MoviesContract.MovieEntry.MOVIE_POSTER_PATH,
            MoviesContract.MovieEntry.MOVIE_RATING
    };

    private enum SortCriteria {
        BYPOPULARITY,
        BYRATING,
        BYFAVORITES
    }

    NetworkUtil.networkConnectionListener networkConnectionListener;

    SortCriteria sortCriteria = null;

    Toolbar toolbar;

    RecyclerView moviesRecyclerView;

    List<Movie> moviesList;

    MoviesAdapter moviesAdapter;

    RecyclerTouchListener recyclerTouchListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_movies_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.moviesToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            sortCriteria = (SortCriteria) savedInstanceState.getSerializable("SortCriteria");
        } else {
            sortCriteria = SortCriteria.BYPOPULARITY;
        }
        moviesRecyclerView = (RecyclerView) getActivity().findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (sortCriteria != SortCriteria.BYFAVORITES) {
            getMovieData(sortCriteria);
        } else {
            getFavoritesData();
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onButtonPressed(Uri uri) {
        super.onButtonPressed(uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NetworkUtil.networkConnectionListener) {
            networkConnectionListener = (NetworkUtil.networkConnectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        networkConnectionListener = null;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (sortCriteria == SortCriteria.BYPOPULARITY) {
            menu.findItem(R.id.sortByPopularity).setChecked(true);
        } else if (sortCriteria == SortCriteria.BYRATING) {
            menu.findItem(R.id.sortByRating).setChecked(true);
        } else if (sortCriteria == SortCriteria.BYFAVORITES) {
            menu.findItem(R.id.sortByFavorites).setCheckable(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("SortCriteria", sortCriteria);
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
                    networkConnectionListener.isNetworkConnectionLost();
                }
                item.setChecked(!item.isChecked());
                toolbar.setTitle(R.string.movie_popular);

                break;
            case R.id.sortByRating:
                sortCriteria = SortCriteria.BYRATING;
                if (!item.isChecked()) {
                    if (NetworkUtil.isNetworkConnected(getContext())) {
                        getMovieData(sortCriteria);
                    }
                    networkConnectionListener.isNetworkConnectionLost();
                }
                item.setChecked(!item.isChecked());
                toolbar.setTitle(R.string.movie_rating);
                break;
            case R.id.sortByFavorites:
                sortCriteria = SortCriteria.BYFAVORITES;
                if (!item.isChecked()) {
                    getFavoritesData();
                }
                item.setChecked(!item.isChecked());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMovieData(SortCriteria mSortCriteria) {
        MoviesApiInterface moviesApiInterface = MoviesApiClient.getRetrofitClient().create(MoviesApiInterface.class);
        Log.d(TAG, "getMovieData: Called" + mSortCriteria);

        Call<MoviesResponse> call = null;
        if (mSortCriteria == SortCriteria.BYPOPULARITY) {
            call = moviesApiInterface.getPoplarMovies(MoviesApiClient.API_KEY);
        } else if (mSortCriteria == SortCriteria.BYRATING) {
            call = moviesApiInterface.getTopRatedMovies(MoviesApiClient.API_KEY);
        }

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.body() != null) {
                    if (moviesList != null && !moviesList.isEmpty()) {
                        moviesList.clear();
                    }
                    moviesList = response.body().getMoviesList();
                    Log.d(TAG, "onResponse: " + moviesList.size());
                    setUpAdapter();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure:  falied to retreived data");
            }
        });
    }

    public void getFavoritesData() {
        sortCriteria = SortCriteria.BYFAVORITES;
        Cursor cursor = getContext().getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                MOVIE_COLUMNS,
                null,
                null,
                null
        );
        getFavoriteMoviesDataFromCursor(cursor);
    }

    private void getFavoriteMoviesDataFromCursor(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            moviesList.clear();
            do {
                Movie movie = new Movie(cursor);
                moviesList.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d(TAG, "getFavoriteMoviesDataFromCursor: " + moviesList.size());
        toolbar.setTitle(R.string.movie_favorites);
        setUpAdapter();
    }

    private void setUpAdapter() {
        moviesAdapter = new MoviesAdapter(getContext(), moviesList);
        moviesRecyclerView.setAdapter(moviesAdapter);
        if (recyclerTouchListener == null) {
            recyclerTouchListener = new RecyclerTouchListener(getContext(), this);
        }
        moviesRecyclerView.addOnItemTouchListener(recyclerTouchListener);
    }

    @Override
    public void onClick(View view, int position) {
        Movie selectedMovie = moviesList.get(position);
        Intent intent = new Intent(getContext(), MovieDetails.class);
        intent.putExtra("SelectedMovie", selectedMovie);
        startActivity(intent);
    }
}
