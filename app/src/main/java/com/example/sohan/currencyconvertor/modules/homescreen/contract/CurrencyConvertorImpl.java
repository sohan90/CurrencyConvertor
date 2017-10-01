package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import com.example.sohan.currencyconvertor.model.CountryInfo;
import com.example.sohan.currencyconvertor.model.CurrencyConvertor;
import com.example.sohan.currencyconvertor.network.ResponseHandler;
import com.example.sohan.currencyconvertor.repository.CurrencyConvertorInteractorImpl;
import com.example.sohan.currencyconvertor.utils.LogUtils;

/**
 * Presenter class for CurrencyConvertorFragment class
 */

public class CurrencyConvertorImpl {
    private static final String TAG = CurrencyConvertorImpl.class.getSimpleName();
    private final ICurrencyConvertorView mView;
    private final CurrencyConvertorInteractorImpl mInterator;
    private CountryInfo mFromCurrency;
    private CountryInfo mToCurrency;

    public CurrencyConvertorImpl(ICurrencyConvertorView view, CurrencyConvertorInteractorImpl interator) {
        this.mView = view;
        this.mInterator = interator;
    }

    public void convertCurrency(final String amount, CountryInfo fromCurrency, CountryInfo toCurrency) {
        mFromCurrency = fromCurrency;
        mToCurrency = toCurrency;
        if (mInterator != null) {
            mInterator.convertCurrency(new ResponseHandler.ResponseCallBack<CurrencyConvertor>() {
                @Override
                public void onSuccess(CurrencyConvertor currencyConvertor) {
                    LogUtils.LOGD(TAG, "Currency convertion success");
                    updateCurrentBalanceRespectively(amount, currencyConvertor);
                    if (mView != null){
                        mView.showDialogMsg();
                    }
                }

                @Override
                public void onFailure(String error) {
                    LogUtils.LOGD(TAG, "Currency conversion failed");

                }
            }, amount, fromCurrency.getIsoCode(), toCurrency.getIsoCode());
        }
    }

    private void updateCurrentBalanceRespectively(String amount, CurrencyConvertor currencyConvertor) {
        double fromCurrentBalance = Double.valueOf(mFromCurrency.getmCurrentBalance());
        double afterSellingFromBal = fromCurrentBalance - Double.valueOf(amount);
        mFromCurrency.setmCurrentBalance(String.valueOf(afterSellingFromBal));

        double toCurrentBalance = Double.valueOf(mToCurrency.getmCurrentBalance());
        double conversionAmount = Double.valueOf(currencyConvertor.getAmount());
        double aferConversionToBal = toCurrentBalance + conversionAmount;
        mToCurrency.setmCurrentBalance(String.valueOf(aferConversionToBal));
    }
}
