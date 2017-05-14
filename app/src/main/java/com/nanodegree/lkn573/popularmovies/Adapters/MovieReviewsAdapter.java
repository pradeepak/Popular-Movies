package com.nanodegree.lkn573.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.lkn573.popularmovies.Models.MovieReview;
import com.nanodegree.lkn573.popularmovies.R;

import java.util.List;

/**
 * Created by prade on 4/1/2017.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewsViewHolder> {

    private Context context;
    private List<MovieReview> reviewList;

    public MovieReviewsAdapter(Context context, List<MovieReview> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        final MovieReview reviewItem = reviewList.get(position);
        holder.reviewUser.setText(reviewItem.getAuthor());
        holder.reviewContent.setText(reviewItem.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewUser;
        private TextView reviewContent;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            reviewUser = (TextView) itemView.findViewById(R.id.reviewUser);
            reviewContent = (TextView) itemView.findViewById(R.id.reviewContent);
        }
    }
}
