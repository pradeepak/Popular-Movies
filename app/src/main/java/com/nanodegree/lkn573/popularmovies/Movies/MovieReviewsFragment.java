package com.nanodegree.lkn573.popularmovies.Movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.lkn573.popularmovies.Adapters.MovieReviewsAdapter;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiClient;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiInterface;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.Models.MovieReview;
import com.nanodegree.lkn573.popularmovies.Models.ReviewsResponse;
import com.nanodegree.lkn573.popularmovies.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nanodegree.lkn573.popularmovies.Database.MoviesDbHelper.TAG;

/**
 * Created by prade on 4/1/2017.
 */

public class MovieReviewsFragment extends CoreFragment {
    Movie selectedMovieItem;

    RecyclerView reviewsRecyclerView;

    MovieReviewsAdapter movieReviewsAdapter;

    MoviesApiInterface moviesApiInterface;

    LinearLayoutManager gridLayoutManager;

    public static MovieReviewsFragment newInstance(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable("SelectedMovie", movie);

        MovieReviewsFragment fragment = new MovieReviewsFragment();
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
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_videos, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsRecyclerView = (RecyclerView) view.findViewById(R.id.videosRecyclerView);
        moviesApiInterface = MoviesApiClient.getRetrofitClient().create(MoviesApiInterface.class);
        fetchMovieVideos(selectedMovieItem.getId());
    }
    public void fetchMovieVideos(int movieId) {
        Log.d(TAG, "getMovieVideos");
        Call<ReviewsResponse> call = moviesApiInterface.getMovieReviews(movieId, MoviesApiClient.API_KEY);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if(response!=null){
                    List<MovieReview> videosResponseList = response.body().getReviewList();
                    movieReviewsAdapter = new MovieReviewsAdapter(getContext(),videosResponseList);
                    gridLayoutManager = new GridLayoutManager(getActivity(),1);
                    reviewsRecyclerView.setAdapter(movieReviewsAdapter);
                    reviewsRecyclerView.setLayoutManager(gridLayoutManager);

                    Log.d(TAG, "onResponse: Video Site = " + videosResponseList.get(0).getAuthor());
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure:  falied to retreive video data");
            }
        });
    }
}
