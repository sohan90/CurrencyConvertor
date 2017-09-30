package com.example.sohan.currencyconvertor.network;

import com.example.sohan.currencyconvertor.model.CurrencyConvertorResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    Call<CurrencyConvertorResponse> getCurrency(@Path("fromAmount") String amount, @Path("fromCurrency")
                                                String fromCurrency, @Path("toCurrency") String toCurrency);
}