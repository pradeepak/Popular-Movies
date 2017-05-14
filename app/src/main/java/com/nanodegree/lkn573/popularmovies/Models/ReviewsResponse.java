package com.nanodegree.lkn573.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prade on 1/22/2017.
 */

public class ReviewsResponse {

    @SerializedName("id")
    private int movieId;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieReview> reviewList = new ArrayList<MovieReview>();

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieReview> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<MovieReview> reviewList) {
        this.reviewList = reviewList;
    }
}
