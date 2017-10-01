package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import com.example.sohan.currencyconvertor.model.CurrencyConvertor;

/**
 * View class for {@link com.example.sohan.currencyconvertor.modules.homescreen.CurrencyConvertorFragment}
 */

public interface ICurrencyConvertorView {
    void showProgress();
    void dimissProgress();
    void onCurrencyConversionSuccess(CurrencyConvertor currencyConvertor);
    void onCurrencyConversionFailure(String error);
    void showDialogMsg();
}
