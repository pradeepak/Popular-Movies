package com.nanodegree.lkn573.popularmovies.Config;

import com.nanodegree.lkn573.popularmovies.Models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by prade on 12/11/2016.
 */

public interface MoviesApiInterface {

        @GET("movie/top_rated")
        Call<MoviesResponse> getTopRatedMovies(@Query(MoviesApiClient.API_KEY_PARAM) String apiKey);

        @GET("movie/popular")
        Call<MoviesResponse> getPoplarMovies(@Query(MoviesApiClient.API_KEY_PARAM) String apiKey);

        @GET("movie/{id}")
        Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query(MoviesApiClient.API_KEY_PARAM) String apiKey);

}
