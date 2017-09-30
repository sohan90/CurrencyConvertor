package com.example.sohan.currencyconvertor.network;

import android.content.Context;


import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.model.CountryInfo;
import com.example.sohan.currencyconvertor.model.CurrencyConvertor;

import java.util.List;

import retrofit2.Call;


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

    /**
     * Method to get respective currency convertor
     * @param callBack
     * @param amount
     * @param fromCurrency
     * @param toCurrency
     */
    public void getCurrency(ResponseHandler.ResponseCallBack<CurrencyConvertor> callBack,
                            String amount, String fromCurrency, String toCurrency) {
        Call<CurrencyConvertor> call = mApiService.getCurrency(amount, fromCurrency, toCurrency);
        ResponseHandler<CurrencyConvertor> handler = new ResponseHandler<>(callBack);
        call.enqueue(handler);
    }

    /**
     * Method to get all country info like name, code, flag etc.. by making api call
     * @param callBack
     * @param url
     */
    public void getAllCountryInfo(ResponseHandler.ResponseCallBack<CountryInfo> callBack,
                          String url) {
        Call<List<CountryInfo>> call = mApiService.getAllCountry(url);
        ResponseHandler<List<CountryInfo>> handler = new ResponseHandler<>(callBack);
        call.enqueue(handler);
    }


}
