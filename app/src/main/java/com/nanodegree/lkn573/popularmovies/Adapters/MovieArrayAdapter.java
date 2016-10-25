package com.nanodegree.lkn573.popularmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanodegree.lkn573.popularmovies.Models.Movie;
import com.nanodegree.lkn573.popularmovies.R;

import java.util.List;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

public static final String TAG = MovieArrayAdapter.class.getSimpleName();

    public MovieArrayAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Movie movieItem =  getItem(position);
        //Log.d(TAG, "getView: "+movieItem.getTitle());
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        String imageURL = movieItem.getPosterURL();
        //og.d(TAG, "getView: "+ imageURL);
        Glide.with(getContext()).load(imageURL).error(R.mipmap.ic_launcher).into(holder.poster);
        holder.title.setText(movieItem.getTitle());
        return convertView;
    }




    class ViewHolder{

        ImageView poster;
        TextView title;

        ViewHolder (View v){
            poster = (ImageView) v.findViewById(R.id.moviePoster);
            title = (TextView) v.findViewById(R.id.movieTitle);
        }
    }
}
