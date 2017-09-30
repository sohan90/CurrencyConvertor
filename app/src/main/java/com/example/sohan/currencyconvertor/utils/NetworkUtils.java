package com.example.sohan.currencyconvertor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Network utility methods to check network related stuff.
 */
public class NetworkUtils {

    private ConnectivityManager mConnectivityManager;
    private static NetworkUtils sNetworkUtils;

    private NetworkUtils() {

    }

    public static NetworkUtils getInstance() {
        if (sNetworkUtils == null) {
            sNetworkUtils = new NetworkUtils();
        }
        return sNetworkUtils;
    }

    /**
     * Init this in application.
     */
    public void init(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Boolean isNetworkConnected() {
        Boolean isConnected = false;
        if (mConnectivityManager != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                Network[] networks = mConnectivityManager.getAllNetworks();
                if (networks != null) {
                    for (Network network : networks) {
                        NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(network);
                        if (networkInfo != null && networkInfo.isConnected()) {
                            isConnected = true;
                            break;
                        }
                    }
                }
            } else {
                NetworkInfo[] networkInfos = mConnectivityManager.getAllNetworkInfo();
                if (networkInfos != null) {
                    for (NetworkInfo networkInfo : networkInfos) {
                        if (networkInfo.isConnected()) {
                            isConnected = true;
                            break;
                        }
                    }
                }
            }
        }
        return isConnected;
    }
}
