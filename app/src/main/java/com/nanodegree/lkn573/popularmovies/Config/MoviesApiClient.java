package com.nanodegree.lkn573.popularmovies.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prade on 12/11/2016.
 */

public class MoviesApiClient {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String GET_POPULAR_MOVIES = BASE_URL + "movie/popular";
    public static final String GET_TOP_RATED_MOVIES = BASE_URL + "movie/top_rated";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String API_KEY = "";
    public static final String API_KEY_PARAM = "api_key";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
