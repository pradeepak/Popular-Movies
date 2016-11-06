package com.nanodegree.lkn573.popularmovies.Core;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.nanodegree.lkn573.popularmovies.Config.RequestConfiguration;
import com.nanodegree.lkn573.popularmovies.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class GetMovieDataTask extends AsyncTask<String, Void, Movie[]> {

    public static final String TAG = GetMovieDataTask.class.getSimpleName();
    OnMoviesRetrievedListener moviesRetrievedListener = null;

    public GetMovieDataTask(OnMoviesRetrievedListener moviesRetrievedListener) {
        this.moviesRetrievedListener = moviesRetrievedListener;
    }

    public GetMovieDataTask() {

    }

    @Override
    protected Movie[] doInBackground(String... strings) {

        String sort = strings[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieStringData = null;

        RequestConfiguration requestConfiguration = new RequestConfiguration();
        Uri builtUri = requestConfiguration.UriBuilder(sort);

        try {
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.d(TAG, "doInBackground: no data");
                return null;
            }

            movieStringData = buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }

            try {
                return retrieveMoviesFromJson(movieStringData);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return null;

    }

    private Movie[] retrieveMoviesFromJson(String moviesStringData) throws JSONException {
        JSONObject moviesJsonObject = new JSONObject(moviesStringData);
        JSONArray resultsJsonArray = moviesJsonObject.getJSONArray("results");
        Movie[] moviesArray = new Movie[resultsJsonArray.length()];

        for (int i = 0; i < moviesArray.length; i++) {
            JSONObject movieJsonObject = resultsJsonArray.getJSONObject(i);
            moviesArray[i] = new Movie(movieJsonObject);
        }
        return moviesArray;
    }


    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);
        if (movies != null) {
            moviesRetrievedListener.onMoviesRetrieved(movies);
        }

    }

    public interface OnMoviesRetrievedListener {
        void onMoviesRetrieved(Movie[] movies);
    }

}
