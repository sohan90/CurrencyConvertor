package com.example.sohan.currencyconvertor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Response Class  to get all country info from api call
 */
public class CountryInfo implements Parcelable{
    @SerializedName("State or territory")
    private String mCountryName;

    @SerializedName("Currency")
    private String mCurrency;

    @SerializedName("Symbol")
    private String mSymbol;

    @SerializedName("ISO code")
    private String mIsoCode;

    public String getCountryName() {
        return mCountryName;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public String getIsoCode() {
        return mIsoCode;
    }

    //for local reference
    private String mCurrentBalance;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
