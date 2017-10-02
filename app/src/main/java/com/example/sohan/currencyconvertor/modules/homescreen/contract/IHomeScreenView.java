package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import android.content.Context;

import com.example.sohan.currencyconvertor.models.CountryInfo;

import java.util.List;

/**
 * View class for HomeScreenActivity
 */

public interface IHomeScreenView {
    void showProgress();
    void dimissProgress();
    void updateCurrentBalanceAdapter(List<CountryInfo> countryInfoList);
    void updateToolBar(String title);
    void hideActivityViews();
    void udpateAdapterDataFromFragment();
    void addCountryForCurrentBalance(CountryInfo countryInfo);
    Context getCtxt();


}
