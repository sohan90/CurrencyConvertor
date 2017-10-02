package com.example.sohan.currencyconvertor.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Response class for currency convertor api call
 */

public class CurrencyConvertor implements Parcelable{
    @SerializedName("amount")
    private String mAmount;
    @SerializedName("currency")
    private String mCurrency;



    protected CurrencyConvertor(Parcel in) {
        mAmount = in.readString();
        mCurrency = in.readString();
    }

    public static final Creator<CurrencyConvertor> CREATOR = new Creator<CurrencyConvertor>() {
        @Override
        public CurrencyConvertor createFromParcel(Parcel in) {
            return new CurrencyConvertor(in);
        }

        @Override
        public CurrencyConvertor[] newArray(int size) {
            return new CurrencyConvertor[size];
        }
    };

    public String getAmount() {
        return mAmount;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAmount);
        dest.writeString(mCurrency);

    }

 }
