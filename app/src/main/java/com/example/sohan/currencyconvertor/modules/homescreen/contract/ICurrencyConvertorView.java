package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import android.content.Context;

/**
 * View class for {@link com.example.sohan.currencyconvertor.modules.homescreen.CurrencyConvertorFragment}
 */

public interface ICurrencyConvertorView {
    void showProgress();
    void dimissProgress();
    void onCurrencyConversionSuccess(String fromAmountMsg, String toAmontMsg, String commissionFee);
    void onCurrencyConversionFailure(String error);
    void showDialogMsg(String title, String msg, boolean popBackStack);
    void updateTotalCommissionFee(String totalCommissionFee);
    Context getContxt();
}
