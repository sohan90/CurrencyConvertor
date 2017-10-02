package com.example.sohan.currencyconvertor.repository;

import android.content.Context;

import com.example.sohan.currencyconvertor.common.Constants;
import com.example.sohan.currencyconvertor.database.PreferenceHelper;
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

    private CurrencyConvertorInteractorImpl() {
        //empty constructor
    }

    public static CurrencyConvertorInteractorImpl getInstance() {
        if (mIneractor == null) {
            mIneractor = new CurrencyConvertorInteractorImpl();
        }
        return mIneractor;
    }

    public void fetchAllCountry(ResponseHandler.ResponseCallBack<List<CountryInfo>> callBack, String url) {
        ApiClient.get().getAllCountryInfo(callBack, url);
    }

    public List<CountryInfo> getCurrentBalanceListFromPref(Context context) {
        return PreferenceHelper.getListOfObject(context, Constants.PREF_CURRENT_BAL_LIST);
    }

    public void setCurrentBalanceListToPref(Context context, List<CountryInfo> list) {
        PreferenceHelper.storeListOfObject(context,Constants.PREF_CURRENT_BAL_LIST, list);
    }

    public void convertCurrency(ResponseHandler.ResponseCallBack<CurrencyConvertor> callBack,
                                String amount, String fromCurrency, String toCurrency) {

        ApiClient.get().getCurrency(callBack, amount, fromCurrency, toCurrency);
    }


    public void addTotalTransactionToPreference(Context context, int totalTransactionTimes) {
        PreferenceHelper.storePrefInt(context, Constants.PREF_TOTAL_TRANS_KEY , totalTransactionTimes);
    }

    public int getTotalTransactionFromPref(Context context){
       return PreferenceHelper.getPrefInt(context, Constants.PREF_TOTAL_TRANS_KEY);
    }
}

