package com.example.sohan.currencyconvertor.models;

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

    //for local reference
    private String mCurrentBalance;

    protected CountryInfo(Parcel in) {
        mCountryName = in.readString();
        mCurrency = in.readString();
        mSymbol = in.readString();
        mIsoCode = in.readString();
        mCurrentBalance = in.readString();
    }

    public static final Creator<CountryInfo> CREATOR = new Creator<CountryInfo>() {
        @Override
        public CountryInfo createFromParcel(Parcel in) {
            return new CountryInfo(in);
        }

        @Override
        public CountryInfo[] newArray(int size) {
            return new CountryInfo[size];
        }
    };

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


    public void setmCountryName(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public void setmSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public void setmIsoCode(String mIsoCode) {
        this.mIsoCode = mIsoCode;
    }

    public String getmCurrentBalance() {
        return mCurrentBalance;
    }

    public void setmCurrentBalance(String mCurrentBalance) {
        this.mCurrentBalance = mCurrentBalance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCountryName);
        dest.writeString(mCurrency);
        dest.writeString(mSymbol);
        dest.writeString(mIsoCode);
        dest.writeString(mCurrentBalance);
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof CountryInfo) {
            CountryInfo countryInfo = (CountryInfo) obj;
            isEqual = countryInfo.getIsoCode().equals(this.getIsoCode());
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return getIsoCode();
    }

    @Override
    public int hashCode() {
        return 100 * this.getIsoCode().hashCode();
    }
}
