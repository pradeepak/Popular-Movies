package com.nanodegree.lkn573.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prade on 12/11/2016.
 */

public class MoviesResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int totalResults;
    @SerializedName("results")
    private List<Movie> moviesList = new ArrayList<Movie>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }
}
