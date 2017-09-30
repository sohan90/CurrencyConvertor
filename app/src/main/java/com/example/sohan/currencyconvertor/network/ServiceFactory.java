package com.example.sohan.currencyconvertor.network;

import android.content.Context;


import com.example.sohan.currencyconvertor.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory class for creating Retrofit Rest client
 */
public class ServiceFactory {

    private static OkHttpClient getOkHttpClient(Context context) {
        OkHttpClient.Builder okHttpClientBuilder = getOkHttpClientBuilder();
        // set 10MB cache
        okHttpClientBuilder.cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024));
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level loggingLevel = (LogUtils.ENABLE_LOG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
        interceptor.setLevel(loggingLevel);
        return okHttpClientBuilder.addInterceptor(interceptor).build();
    }

    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        try {
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);
            okHttpClient.sslSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ApiService getGSONService(Context context, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(context))
                .build()
                .create(ApiService.class);
    }

    public static ApiService createService(Context context, String baseUrl, RequestType requestType) {
        ApiService apiService = null;
        switch (requestType) {
            case REQUEST_GSON:
                apiService = getGSONService(context, baseUrl);
                break;
            case REQUEST_AUTH_HEADER_GSON:
                break;
            case REQUEST_STRING:
                break;
            case REQUEST_AUTH_HEADER_STRING:
                break;
            default:
                apiService = getGSONService(context, baseUrl);
        }
        return apiService;
    }
}
