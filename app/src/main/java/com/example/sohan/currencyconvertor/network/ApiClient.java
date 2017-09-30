package com.example.sohan.currencyconvertor.network;

import android.content.Context;


import com.example.sohan.currencyconvertor.R;


/**
 * Wrapper class for accessing REST APIs.
 * Contains implementation of ApiService interface.
 */
public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();

    private static ApiClient sApiClient = null;

    private ApiService mApiService;

    private ApiClient() {
    }

    public void init(Context context) {
        String baseUrl = context.getString(R.string.base_url);
        createApiService(context, baseUrl, RequestType.REQUEST_GSON);
    }

    public static ApiClient get() {
        if (sApiClient == null) {
            sApiClient = new ApiClient();
        }
        return sApiClient;
    }

    private void createApiService(Context context, String baseUrl, RequestType requestType) {
        if (mApiService == null) {
            mApiService = ServiceFactory.createService(context, baseUrl, requestType);
        }
    }




}
