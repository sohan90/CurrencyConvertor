package com.example.sohan.currencyconvertor.modules.homescreen.contract;

import android.text.TextUtils;

import com.example.sohan.currencyconvertor.common.Constants;
import com.example.sohan.currencyconvertor.models.CountryInfo;
import com.example.sohan.currencyconvertor.models.CurrencyConvertor;
import com.example.sohan.currencyconvertor.network.ResponseHandler;
import com.example.sohan.currencyconvertor.repository.CurrencyConvertorInteractorImpl;
import com.example.sohan.currencyconvertor.utils.LogUtils;

import java.text.DecimalFormat;

/**
 * Presenter class for CurrencyConvertorFragment class
 */

public class CurrencyConvertorImpl {
    private static final String TAG = CurrencyConvertorImpl.class.getSimpleName();
    private final ICurrencyConvertorView mView;
    private final CurrencyConvertorInteractorImpl mInterator;

    public CurrencyConvertorImpl(ICurrencyConvertorView view, CurrencyConvertorInteractorImpl interator) {
        this.mView = view;
        this.mInterator = interator;
    }

    public void convertCurrency(final String amount, final CountryInfo fromCurrency, final CountryInfo toCurrency) {
        if (mView != null) {
            mView.showProgress();
        }
        if (mInterator != null) {
            mInterator.convertCurrency(new ResponseHandler.ResponseCallBack<CurrencyConvertor>() {
                @Override
                public void onSuccess(CurrencyConvertor currencyConvertor) {
                    LogUtils.LOGD(TAG, "Currency convertion success");
                    updatePreferenceValue();
                    updateCurrentBalanceRespectively(amount, fromCurrency, toCurrency, currencyConvertor);
                    if (mView != null) {
                        mView.dimissProgress();
                        String fromAmountMsg = amount + " " + fromCurrency.getIsoCode();
                        String toAmountMsg = currencyConvertor.getAmount() + " " + currencyConvertor.getCurrency();
                        String commissionFee = getCommissionFee(amount);
                        updateTotalCommisionFeeinPref(commissionFee);
                        mView.onCurrencyConversionSuccess(fromAmountMsg, toAmountMsg, commissionFee);
                    }
                }

                @Override
                public void onFailure(String error) {
                    LogUtils.LOGD(TAG, "Currency conversion failed");
                    if (mView != null) {
                        mView.dimissProgress();
                        mView.onCurrencyConversionFailure(error);
                    }

                }
            }, amount, fromCurrency.getIsoCode(), toCurrency.getIsoCode());
        }
    }

    private void updateTotalCommisionFeeinPref(String commissionFee) {
        double commissFee = Double.valueOf(commissionFee);
        String totalCommissionFee = mInterator.getToatlCommissionFee(mView.getContxt());
        double updatedFee = commissFee;
        if (!TextUtils.isEmpty(totalCommissionFee)) {
            double feeFromPref = Double.valueOf(totalCommissionFee);
            updatedFee = commissFee + feeFromPref;
        }
        DecimalFormat decimalFormat = new DecimalFormat(Constants.DECIMAL_FORMAT);
        mView.updateTotalCommissionFee(decimalFormat.format(updatedFee));
        mInterator.updateTotalCommissionFee(mView.getContxt(), decimalFormat.format(updatedFee));
    }

    public String getTotalCommsionFeeFromPref() {
        String fee = null;
        if (mInterator != null) {
            fee = mInterator.getToatlCommissionFee(mView.getContxt());
        }
        return fee;
    }

    /**
     * update the total transaction limit from the preference value
     */
    private void updatePreferenceValue() {
        int totalTransactionFromPref = mInterator.getTotalTransactionFromPref(mView.getContxt());
        int totalTransactionMade = totalTransactionFromPref + 1;
        mInterator.addTotalTransactionToPreference(mView.getContxt(), totalTransactionMade);
    }

    /**
     * Method calculate the commission fee before hitting api call and make sure currentBalance doesn't go to
     * negative value or else return false and don't call api
     *
     * @param sellingAmount selling amount
     * @param fromCurrency  from currency
     * @return isValid Amount or not
     */
    public boolean validateAmount(String sellingAmount, CountryInfo fromCurrency) {
        Double currentBalance = Double.valueOf(fromCurrency.getmCurrentBalance());
        if (currentBalance == 0) {
            return false;
        }
        boolean isValidAmount = false;
        double finalAmount = debitCurrentBalance(sellingAmount, fromCurrency);
        if (finalAmount > 0) {
            isValidAmount = true;
        }
        return isValidAmount;
    }

    /**
     * Debit the current balance from selling amount and if it reaches free transaction limit then
     * sub with the commision fee amount and return
     *
     * @param sellingAmount selling amount
     * @param fromCurrency  from currency
     * @return final amount
     */
    private double debitCurrentBalance(String sellingAmount, CountryInfo fromCurrency) {
        int totalTransactionFromPref = mInterator.getTotalTransactionFromPref(mView.getContxt());
        double sellingAmtDouble = Double.valueOf(sellingAmount);
        double fromCurrentBalanceDouble = Double.valueOf(fromCurrency.getmCurrentBalance());
        double afterDeductionFromSellingAmt = fromCurrentBalanceDouble - sellingAmtDouble;
        double finalAmount = afterDeductionFromSellingAmt;
        if (totalTransactionFromPref >= Constants.FREE_TRANSACTION_LIMIT) {
            double commissionFee = caluclateCommissionFee(Double.valueOf(sellingAmount));
            finalAmount = afterDeductionFromSellingAmt - commissionFee;

        }
        return finalAmount;
    }

    /**
     * Add current balance with credit amount from the api call.
     *
     * @param currentBalance current balance
     * @param creditAmount   credit amount
     * @return final amount
     */
    private double creditCurrentBalance(String currentBalance, String creditAmount) {
        double toCurrentBalance = Double.valueOf(currentBalance);
        double conversionAmount = Double.valueOf(creditAmount);
        double finalAmt = toCurrentBalance + conversionAmount;
        return finalAmt;
    }

    /**
     * Calculate the commission fee for the respective amount
     *
     * @param amount respective amount
     * @return commision fee
     */
    private double caluclateCommissionFee(double amount) {
        double fee = (amount * Constants.COMMISSION_FEE) / 100;
        return fee;
    }

    /**
     * This method used to show commission fee message
     * Get respective commission fee if it more than free transaction limit else return no
     * commision fee i,e 0
     *
     * @param amount amount
     * @return commission fee returning in string since we gonna show in message
     */
    private String getCommissionFee(String amount) {
        DecimalFormat decimalFormat = new DecimalFormat(Constants.DECIMAL_FORMAT);
        int totalTrans = mInterator.getTotalTransactionFromPref(mView.getContxt());
        String commissinFee;
        if (totalTrans > Constants.FREE_TRANSACTION_LIMIT) {
            double commFeeDble = caluclateCommissionFee(Double.valueOf(amount));
            commissinFee = decimalFormat.format(commFeeDble);
        } else {
            commissinFee = "0.0";
        }
        return commissinFee;
    }

    private void updateCurrentBalanceRespectively(String sellingAmount, CountryInfo fromCurrency,
                                                  CountryInfo toCurrency, CurrencyConvertor currencyConvertor) {

        DecimalFormat twoPlacesDecFormat = new DecimalFormat(Constants.DECIMAL_FORMAT);
        double curr = debitCurrentBalance(sellingAmount, fromCurrency);
        String currentBalance = twoPlacesDecFormat.format(curr);
        fromCurrency.setmCurrentBalance(currentBalance);

        double toCurr = creditCurrentBalance(toCurrency.getmCurrentBalance(), currencyConvertor.getAmount());
        String toCurrenBalance = twoPlacesDecFormat.format(toCurr);
        toCurrency.setmCurrentBalance(toCurrenBalance);
    }
}
