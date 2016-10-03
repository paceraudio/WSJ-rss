package com.pacerapps.wsjrss.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by jeffwconaway on 10/3/16.
 */

public class ConnectivityUtils {

    Context context;

    public ConnectivityUtils(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
