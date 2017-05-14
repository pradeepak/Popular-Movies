package com.nanodegree.lkn573.popularmovies.Models;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.nanodegree.lkn573.popularmovies.Config.RequestConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {

    private static final String MOVIE_ID = "id";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String POSTER_PATH = "poster_path";
    private static final String POPULARITY = "popularity";
    private static final String TITLE = "title";
    private static final String VIDEO = "video";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VOTE_AVERAGE = "vote_average";

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("id")
    private int id;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String overView;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("popuarity")
    private String popularity;
    @SerializedName("title")
    private String title;
    @SerializedName("video")
    private Boolean video;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    private boolean isFavourite;

    public Movie(Boolean adult, int id, String originalLanguage, String originalTitle, String overView, String releaseDate,
                 String posterPath, String popularity, String title, Boolean video, String voteAverage, int voteCount) {
        this.adult = adult;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overView = overView;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public Movie(JSONObject movieJsonObject) throws JSONException {
        this.id = movieJsonObject.getInt(MOVIE_ID);
        this.originalLanguage = movieJsonObject.getString(ORIGINAL_LANGUAGE);
        this.overView = movieJsonObject.getString(OVERVIEW);
        this.releaseDate = movieJsonObject.getString(RELEASE_DATE);
        this.posterPath = movieJsonObject.getString(POSTER_PATH);
        this.popularity = movieJsonObject.getString(POPULARITY);
        this.video = movieJsonObject.getBoolean(VIDEO);
        this.title = movieJsonObject.getString(TITLE);
        this.voteAverage = movieJsonObject.getString(VOTE_AVERAGE);
        this.voteCount = movieJsonObject.getInt(VOTE_COUNT);

    }
    public Movie(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.title = cursor.getString(1);
        this.overView = cursor.getString(2);
        this.releaseDate = cursor.getString(3);
        this.posterPath = cursor.getString(4);
        this.voteAverage = cursor.getString(5);
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        overView = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        popularity = in.readString();
        title = in.readString();
        voteAverage = in.readString();
        voteCount = in.readInt();
        isFavourite = in.readByte() != 0;
    }

    public Movie(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setisFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getPosterURL() {
        String imageSize = "w185";
        Uri imageUriBuilder = Uri.parse(RequestConfiguration.IMAGE_URL)
                .buildUpon()
                .appendPath(imageSize)
                .appendEncodedPath(posterPath)
                .build();

        return imageUriBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeString(overView);
        parcel.writeString(releaseDate);
        parcel.writeString(posterPath);
        parcel.writeString(popularity);
        parcel.writeString(title);
        parcel.writeString(voteAverage);
        parcel.writeInt(voteCount);
        parcel.writeByte((byte) (isFavourite ? 1 : 0));
    }
}
