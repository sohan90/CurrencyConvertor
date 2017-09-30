package com.example.sohan.currencyconvertor.network;

import com.example.sohan.currencyconvertor.model.CountryInfo;
import com.example.sohan.currencyconvertor.model.CurrencyConvertor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Java Interface for all REST API's.
 */
public interface ApiService {

    /**
     * Get respective Currency from current Currency
     * @param amount
     * @param fromCurrency
     * @param toCurrency
     * @return
     */
    @GET("/currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    Call<CurrencyConvertor> getCurrency(@Path("fromAmount") String amount, @Path("fromCurrency")
                                                String fromCurrency, @Path("toCurrency") String toCurrency);


    /**
     * Get all country info like name, code, flag etc..
     * @param url dynamic url
     * @return
     */
    @GET
    Call<List<CountryInfo>> getAllCountry(@Url String url);
}