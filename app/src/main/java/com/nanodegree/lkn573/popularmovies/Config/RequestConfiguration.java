package com.nanodegree.lkn573.popularmovies.Config;


import android.net.Uri;

public class RequestConfiguration {

    private static final String BASE_URL= "http://api.themoviedb.org/3/";
    private static final String GET_POPULAR_MOVIES = BASE_URL + "movie/popular";
    private static final String GET_TOP_RATED_MOVIES = BASE_URL + "movie/top_rated";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String API_KEY = "";
    private static final String API_KEY_PARAM = "api_key";
    private static final String POPULAR_MOVIES = "Popular Movies";
    private static final String TOP_RATED = "Top Rated";


    public Uri UriBuilder(String sortCriteria){
        Uri builtUri = null;
        if(sortCriteria.equals(POPULAR_MOVIES)){
        builtUri = Uri.parse(RequestConfiguration.GET_POPULAR_MOVIES).buildUpon().
            appendQueryParameter(RequestConfiguration.API_KEY_PARAM, RequestConfiguration.API_KEY)
            .build();
        }else if(sortCriteria.equals(TOP_RATED)){
            builtUri = Uri.parse(RequestConfiguration.GET_TOP_RATED_MOVIES).buildUpon().
                appendQueryParameter(RequestConfiguration.API_KEY_PARAM, RequestConfiguration.API_KEY)
                .build();
        }
        return builtUri;
    }
}
