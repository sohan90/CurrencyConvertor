package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import com.example.sohan.currencyconvertor.models.CountryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Country list presenter impl
 */

public class CountryListPresenterImpl {

    private ICountryListView mView;
    public CountryListPresenterImpl(ICountryListView view) {
        this.mView = view;
    }


    public void onFilter(String s, List<CountryInfo> countryInfoList) {
        List<CountryInfo> originalList = new ArrayList<>(countryInfoList);
        List<CountryInfo> filterList = new ArrayList<>();
        if (s.length() == 0) {
            filterList.addAll(originalList);
        } else {
            for (CountryInfo countryInfo : originalList) {
                if (countryInfo.getCountryName().toLowerCase().contains(s.toLowerCase())) {
                    filterList.add(countryInfo);
                }
            }
        }
        mView.onFilter(filterList);
    }
}
