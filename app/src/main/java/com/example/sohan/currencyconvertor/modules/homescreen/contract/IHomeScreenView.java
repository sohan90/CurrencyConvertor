package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import com.example.sohan.currencyconvertor.model.CountryInfo;

import java.util.List;

/**
 * View class for HomeScreenActivity
 */

public interface IHomeScreenView {
    void showProgress();
    void dimissProgress();
    void updateCurrentBalanceAdapter(List<CountryInfo> countryInfoList);

}
