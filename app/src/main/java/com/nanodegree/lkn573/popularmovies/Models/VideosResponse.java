package com.nanodegree.lkn573.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prade on 1/22/2017.
 */

public class VideosResponse {
    @SerializedName("id")
    private int movieId;
    @SerializedName("results")
    private List<MovieVideo> vidoeList = new ArrayList<MovieVideo>();

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<MovieVideo> getVidoeList() {
        return vidoeList;
    }

    public void setVidoeList(List<MovieVideo> vidoeList) {
        this.vidoeList = vidoeList;
    }
}
