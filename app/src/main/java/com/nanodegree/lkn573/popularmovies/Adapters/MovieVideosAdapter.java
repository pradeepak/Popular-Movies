package com.nanodegree.lkn573.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanodegree.lkn573.popularmovies.Models.MovieVideo;
import com.nanodegree.lkn573.popularmovies.R;

import java.util.List;

/**
 * Created by prade on 3/12/2017.
 */

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.VideosViewHolder> {

    public static final String TAG = MovieVideosAdapter.class.getSimpleName();

    private List<MovieVideo> videoList;

    private Context context;

    private VideoOnClickListener videoOnClickListener;

    public MovieVideosAdapter(Context context, List<MovieVideo> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    public void setVideoClickListener(VideoOnClickListener videoOnClickListener) {
        this.videoOnClickListener = videoOnClickListener;
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);

        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, int position) {
        final MovieVideo videoItem = videoList.get(position);
        // Log.d(TAG, "onBindViewHolder: " + videoItem.getKey());
        Glide.with(context).load("http://img.youtube.com/vi/" + videoItem.getKey() + "/0.jpg").error(R.mipmap.ic_launcher).into(holder.videoThumbnail);
        holder.videoTitle.setText(videoItem.getVideoName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoOnClickListener.onVideoClick(videoItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public interface VideoOnClickListener {
        public void onVideoClick(MovieVideo movieVideo);
    }

    public static class VideosViewHolder extends RecyclerView.ViewHolder {
        private TextView videoTitle;
        private ImageView videoThumbnail;

        public VideosViewHolder(View itemView) {
            super(itemView);
            videoThumbnail = (ImageView) itemView.findViewById(R.id.videoThumb);
            videoTitle = (TextView) itemView.findViewById(R.id.videoTitle);
        }
    }
}
