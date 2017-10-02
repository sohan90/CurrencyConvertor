package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import android.text.TextUtils;

import com.example.sohan.currencyconvertor.models.CountryInfo;
import com.example.sohan.currencyconvertor.network.ResponseHandler;
import com.example.sohan.currencyconvertor.repository.CurrencyConvertorInteractorImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * PresenterImpl Class for HomeScreen
 */

public class HomeScreenPresenterImpl {
    private final IHomeScreenView mView;
    private final CurrencyConvertorInteractorImpl mInterator;

    public HomeScreenPresenterImpl(IHomeScreenView view, CurrencyConvertorInteractorImpl interator) {
        this.mView = view;
        this.mInterator = interator;
    }

    /**
     * Fetching all countries info is not the part of requirement , But to give support for other currency as well.
     * So intially we are showing only 3 currency as per the requirement and then as the user add other currency ,
     * we will updating its current balance account respectively.
     * Method to fetch all countries info like name, isoCode, currencySymbol, currency.
     *
     * @param url
     */
    public void fetchAllCountry(String url) {
        mView.showProgress();
        mInterator.fetchAllCountry(new ResponseHandler.ResponseCallBack<List<CountryInfo>>() {
            @Override
            public void onSuccess(List<CountryInfo> list) {
                mView.dimissProgress();
                if (list != null && list.size() != 0) {
                    List<CountryInfo> filterList = getFilterList(list);
                    saveAllCurrentListToPref(filterList);
                    mView.updateCurrentBalanceAdapter(filterList);
                }
            }

            @Override
            public void onFailure(String error) {
                mView.dimissProgress();
            }
        }, url);

    }

    /**
     * Method filter the country list which does not have an isocode or symbol
     *
     * @param list
     * @return
     */
    private List<CountryInfo> getFilterList(List<CountryInfo> list) {
        List<CountryInfo> filterList = new ArrayList<>();
        for (CountryInfo countryInfo : list) {
            if (countryInfo != null && countryInfo.getIsoCode() != null && countryInfo.getSymbol() != null) {
                if (!countryInfo.getIsoCode().contains("none") && !countryInfo.getSymbol().contains("none")
                        && !TextUtils.isEmpty(countryInfo.getCountryName())) {
                    countryInfo.setmCurrentBalance("0");
                    filterList.add(countryInfo);
                }
            }
        }
        return filterList;
    }

    /**
     * Method to get current balance . Since requirement to show only 3 currency initially
     * so after fetching  all country info we are updating current balance only for EURO, USD, JPY.
     *
     * @param countryInfoList
     * @return current balance list
     */
    public List<CountryInfo> getCurrentBalanceList(List<CountryInfo> countryInfoList) {
        Set<CountryInfo> currentBalanceList = null; /*hash set for maintaining unique elements since list may contain same
                                                    isoCode for multiple country*/
        if (countryInfoList != null) {
            currentBalanceList = new HashSet<>();
            for (CountryInfo countryInfo : countryInfoList) {
                if (countryInfo.getIsoCode().equals("EUR")) {
                    countryInfo.setmCurrentBalance("1000");
                    currentBalanceList.add(countryInfo);
                } else if (countryInfo.getIsoCode().equals("USD")) {
                    countryInfo.setmCurrentBalance("0");
                    currentBalanceList.add(countryInfo);
                } else if (countryInfo.getIsoCode().equals("JPY")) {
                    countryInfo.setmCurrentBalance("0");
                    currentBalanceList.add(countryInfo);

                }
                if (currentBalanceList.size() == 3) {
                    break; // Initially we want to show only 3 currenc's current balance i,e EURO, USD and JPY so
                    // breaking the loop once we get list size is == 3 instead of looping all the list unnecessary
                }
            }

        }
        List<CountryInfo> list = null;
        if (currentBalanceList != null) {
            list = new ArrayList<>(currentBalanceList);
        }
        return list;

    }

    /**
     * Method return unique country info list
     *
     * @param compareInfo country info
     * @param list        country info list
     * @return unique list
     */
    public List<CountryInfo> getUniqueCountryInf(CountryInfo compareInfo, List<CountryInfo> list) {
        List<CountryInfo> uniqueList = new ArrayList<>();
        for (CountryInfo info : list) {
            if (!compareInfo.equals(info)) {
                uniqueList.add(info);
            }
        }
        return uniqueList;
    }

    public List<CountryInfo> getCurrentBalanceListFromPref() {
        return mInterator.getCurrentBalanceListFromPref(mView.getCtxt());
    }

    public void saveCurrentBalaneListToPref(List<CountryInfo> countryInfoList) {
        mInterator.setCurrentBalanceListToPref(mView.getCtxt(), countryInfoList);
    }

    public List<CountryInfo> getAllCountryInfoFromPref() {
        return mInterator.getAllCountryListFromPref(mView.getCtxt());
    }

    public void saveAllCurrentListToPref(List<CountryInfo> countryInfoList) {
        mInterator.setAllCountryListToPref(mView.getCtxt(), countryInfoList);
    }

    public List<CountryInfo> removeAlreadyAddedCurrency(List<CountryInfo> mCurrentBalanceList,
                                                        List<CountryInfo> mAllCountryInfoList) {
        List<CountryInfo> removedAlreadAddedList = new ArrayList<>(mAllCountryInfoList);
        for (int i = 0; i < mCurrentBalanceList.size(); i++) {
            for (int j = 0; j < removedAlreadAddedList.size(); j++) {
                CountryInfo currentCountry = mCurrentBalanceList.get(i);
                CountryInfo allCountry = removedAlreadAddedList.get(j);
                if (allCountry.getCountryName().equalsIgnoreCase(currentCountry.getCountryName())) {
                    removedAlreadAddedList.remove(allCountry);
                }
            }
        }
        return removedAlreadAddedList;

    }
}
