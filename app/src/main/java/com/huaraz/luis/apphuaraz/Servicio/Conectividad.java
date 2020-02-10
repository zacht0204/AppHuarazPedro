package com.huaraz.luis.apphuaraz.Servicio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Conectividad {

    private static ConnectivityManager manager;

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
