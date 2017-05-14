package com.nanodegree.lkn573.popularmovies.Config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;


public class NetworkUtil {

    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
    public interface networkConnectionListener {
        void isNetworkConnectionLost();
    }
    public interface detailsNetworkConnectionListener {
        Fragment isNetworkConnectionLost(int Position);
    }
}
