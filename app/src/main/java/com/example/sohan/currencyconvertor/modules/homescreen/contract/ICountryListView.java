package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import com.example.sohan.currencyconvertor.models.CountryInfo;

import java.util.List;

/**
 * Created by SOHAN on 10/2/2017.
 */

public interface ICountryListView {

    void onFilter(List<CountryInfo> countryInfo);
}
