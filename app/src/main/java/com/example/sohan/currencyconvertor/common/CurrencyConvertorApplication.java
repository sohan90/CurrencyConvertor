package com.example.sohan.currencyconvertor.common;

import android.app.Application;

import com.example.sohan.currencyconvertor.network.ApiClient;
import com.example.sohan.currencyconvertor.utils.NetworkUtils;


/**
 *  Initializing common resource in Application class
 */

public class CurrencyConvertorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkUtils.getInstance().init(this);
        ApiClient.get().init(this);
    }
}
