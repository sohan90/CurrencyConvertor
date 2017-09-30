package com.example.sohan.currencyconvertor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Response class for currency convertor api call
 */

public class CurrencyConvertorResponse {
    @SerializedName("amount")
    private String mAmount;
    @SerializedName("currency")
    private String mCurrency;

    public String getAmount() {
        return mAmount;
    }

    public String getCurrency() {
        return mCurrency;
    }
}
