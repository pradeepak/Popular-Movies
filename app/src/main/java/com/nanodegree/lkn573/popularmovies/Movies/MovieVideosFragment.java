package com.nanodegree.lkn573.popularmovies.Movies;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.nanodegree.lkn573.popularmovies.Adapters.MovieVideosAdapter;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiClient;
import com.nanodegree.lkn573.popularmovies.Config.MoviesApiInterface;
import com.nanodegree.lkn573.popularmovies.Core.CoreFragment;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.Models.MovieVideo;
import com.nanodegree.lkn573.popularmovies.Models.VideosResponse;
import com.nanodegree.lkn573.popularmovies.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nanodegree.lkn573.popularmovies.Adapters.MovieArrayAdapter.TAG;

/**
 * Created by prade on 3/12/2017.
 */

public class MovieVideosFragment extends CoreFragment implements MovieVideosAdapter.VideoOnClickListener {


    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    Movie selectedMovieItem;

    RecyclerView videosRecyclerView;

    MovieVideosAdapter movieVideosAdapter;

    MoviesApiInterface moviesApiInterface;

    GridLayoutManager gridLayoutManager;

    public static MovieVideosFragment newInstance(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable("SelectedMovie", movie);

        MovieVideosFragment fragment = new MovieVideosFragment();
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
        videosRecyclerView = (RecyclerView) view.findViewById(R.id.videosRecyclerView);
        moviesApiInterface = MoviesApiClient.getRetrofitClient().create(MoviesApiInterface.class);
        fetchMovieVideos(selectedMovieItem.getId());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void fetchMovieVideos(int movieId) {
        Call<VideosResponse> call = moviesApiInterface.getMovieVideos(movieId, MoviesApiClient.API_KEY);
        call.enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
                if (response != null) {
                    List<MovieVideo> videosResponseList = response.body().getVidoeList();
                    movieVideosAdapter = new MovieVideosAdapter(getContext(), videosResponseList);
                    movieVideosAdapter.setVideoClickListener(MovieVideosFragment.this);
                    gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                    videosRecyclerView.setAdapter(movieVideosAdapter);
                    videosRecyclerView.setLayoutManager(gridLayoutManager);

                    Log.d(TAG, "onResponse: Video Site = " + videosResponseList.get(0).getVideoSite());
                }
            }

            @Override
            public void onFailure(Call<VideosResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:  falied to retreive video data");
            }
        });
    }

    @Override
    public void onVideoClick(MovieVideo movieVideo) {
        initYouTubePlayer(movieVideo.getKey());
    }

    private void initYouTubePlayer(String videoKey) {

        int startTimeMillis = 0;

        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                getActivity(), MoviesApiClient.YOUTUBE_API_KEY, videoKey, startTimeMillis, true, false);

        if (intent != null) {
            if (canResolveIntent(intent)) {
                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            } else {
                // Could not resolve the intent - must need to install or update the YouTube API service.
                YouTubeInitializationResult.SERVICE_MISSING
                        .getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != getActivity().RESULT_OK) {
            YouTubeInitializationResult errorReason =
                    YouTubeStandalonePlayer.getReturnedInitializationResult(data);
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(getActivity(), 0).show();
            } else {
                String errorMessage = getResources().getString(R.string.youtube_player_error) + errorReason.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }
}

