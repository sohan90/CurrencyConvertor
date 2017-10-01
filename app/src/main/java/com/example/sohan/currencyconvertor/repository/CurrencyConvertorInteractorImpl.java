package com.example.sohan.currencyconvertor.repository;

import com.example.sohan.currencyconvertor.model.CountryInfo;
import com.example.sohan.currencyconvertor.model.CurrencyConvertor;
import com.example.sohan.currencyconvertor.network.ApiClient;
import com.example.sohan.currencyconvertor.network.ResponseHandler;

import java.util.List;

/**
 * Repository Class for throughout the app used to get the data either from api call or database
 */

public class CurrencyConvertorInteractorImpl {
    public static CurrencyConvertorInteractorImpl mIneractor;

    public static CurrencyConvertorInteractorImpl getInstance() {
        if (mIneractor == null) {
            mIneractor = new CurrencyConvertorInteractorImpl();
        }
        return mIneractor;
    }

    public void fetchAllCountry(ResponseHandler.ResponseCallBack<List<CountryInfo>> callBack, String url) {
        ApiClient.get().getAllCountryInfo(callBack, url);
    }


    public void convertCurrency(ResponseHandler.ResponseCallBack<CurrencyConvertor> callBack,
                                String amount, String fromCurrency, String toCurrency) {

        ApiClient.get().getCurrency(callBack, amount, fromCurrency, toCurrency);
    }


}
