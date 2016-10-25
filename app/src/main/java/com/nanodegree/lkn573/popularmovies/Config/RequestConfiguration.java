package com.nanodegree.lkn573.popularmovies.Config;


import android.net.Uri;

public class RequestConfiguration {

    public static final String BASE_URL= "http://api.themoviedb.org/3/";
    public static final String GET_POPULAR_MOVIES = BASE_URL + "movie/popular";
    public static final String GET_TOP_RATED_MOVIES = BASE_URL + "movie/top_rated";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String API_KEY = "b502150f844a7e7f551492d3a317865c";
    public static final String API_KEY_PARAM = "api_key";


    public Uri UriBuilder(String sortCriteria){
        Uri builtUri = null;
        if(sortCriteria == "Popular Movies"){
        builtUri = Uri.parse(RequestConfiguration.GET_POPULAR_MOVIES).buildUpon().
            appendQueryParameter(RequestConfiguration.API_KEY_PARAM, RequestConfiguration.API_KEY)
            .build();
        }else if(sortCriteria == "Top Rated"){
            builtUri = Uri.parse(RequestConfiguration.GET_TOP_RATED_MOVIES).buildUpon().
                appendQueryParameter(RequestConfiguration.API_KEY_PARAM, RequestConfiguration.API_KEY)
                .build();
        }
        return builtUri;
    }
}
