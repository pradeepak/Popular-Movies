package com.nanodegree.lkn573.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prade on 1/22/2017.
 */

public class MovieVideo implements Parcelable {

    public static final Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {
        @Override
        public MovieVideo createFromParcel(Parcel in) {
            return new MovieVideo(in);
        }

        @Override
        public MovieVideo[] newArray(int size) {
            return new MovieVideo[size];
        }
    };
    @SerializedName("id")
    private String videoId;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String videoName;
    @SerializedName("site")
    private String videoSite;
    @SerializedName("size")
    private int videoSize;
    private String videoType;

    protected MovieVideo(Parcel in) {
        videoId = in.readString();
        key = in.readString();
        videoName = in.readString();
        videoSite = in.readString();
        videoSize = in.readInt();
        videoType = in.readString();
    }

    public MovieVideo(String videoId, String key, String videoName, String videoSite, int videoSize, String videoType) {
        this.videoId = videoId;
        this.key = key;
        this.videoName = videoName;
        this.videoSite = videoSite;
        this.videoSize = videoSize;
        this.videoType = videoType;
    }

    public MovieVideo (JSONObject json) throws JSONException {
        this.videoId = json.getString("id");
        this.key = json.getString("key");
        this.videoName = json.getString("name");
        this.videoSize = json.getInt("size");
        this.videoSite = json.getString("site");
        this.videoType = json.getString("type");
    }
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoSite() {
        return videoSite;
    }

    public void setVideoSite(String videoSite) {
        this.videoSite = videoSite;
    }

    public int getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(int videoSize) {
        this.videoSize = videoSize;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(videoId);
        parcel.writeString(key);
        parcel.writeString(videoName);
        parcel.writeString(videoSite);
        parcel.writeInt(videoSize);
        parcel.writeString(videoType);
    }
}
