package com.example.sohan.currencyconvertor.modules.homescreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.common.BaseFragment;
import com.example.sohan.currencyconvertor.model.CountryInfo;
import com.example.sohan.currencyconvertor.model.CurrencyConvertor;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.ICurrencyConvertorView;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.IHomeScreenView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * CurrencyConvertor fragment class .
 */

public class CurrencyConvertorFragment extends BaseFragment implements ICurrencyConvertorView{

    public static final String FROM_CURRENCY_KEY = "from_currency_key";
    public static final String TO_CURRENCY_LIST_KEY = "to_currency_list_key";
    private IHomeScreenView mHomeScreenView;
    private CountryInfo mFromCurrency;
    private List<CountryInfo> mToCurrencyList;
    private CountryInfo mToCurreny;

    @BindView(R.id.spinner)
    Spinner mSpinnerView;
    @BindView(R.id.from_currency)
    TextView mFromCurrencyHeaderTxt;
    @BindView(R.id.currency)
    TextView mFromCurrencyTableTxt;
    @BindView(R.id.to_currency)
    TextView mToCurrencyTxt;
    @BindView(R.id.current_bal)
    TextView mCurrentBalTxt;
    @BindView(R.id.to_balance)
    TextView mToCurrentBalanceTxt;
    @BindView(R.id.symbol)
    TextView mFromCurrencySymbolTxt;
    @BindView(R.id.selling_amount)
    EditText mSellingAmountEdt;


    public static CurrencyConvertorFragment getInstance(CountryInfo fromCurrency, ArrayList<CountryInfo> toCurrencyList) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(FROM_CURRENCY_KEY, fromCurrency);
        bundle.putParcelableArrayList(TO_CURRENCY_LIST_KEY, toCurrencyList);
        CurrencyConvertorFragment fragment = new CurrencyConvertorFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IHomeScreenView) {
            mHomeScreenView = (IHomeScreenView) context;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.currency_convertor_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUi();
        getBundleData();
        setData();
        setSpinnerData();
    }

    private void setSpinnerData() {
        ArrayAdapter<CountryInfo> arrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_item, mToCurrencyList);
        mSpinnerView.setAdapter(arrayAdapter);
        mSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mToCurreny = mToCurrencyList.get(position);
                mToCurrencyTxt.setText(mToCurreny.getIsoCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onCurrencyConversionSuccess(CurrencyConvertor currencyConvertor) {

    }

    @Override
    public void onCurrencyConversionFailure(String error) {

    }

    @Optional
    @OnClick(R.id.sell)
    public void sell(View view) {
        String amount = mSellingAmountEdt.getText().toString();

    }

    private void setData() {
        String currency = mFromCurrency.getIsoCode() + " " + mFromCurrency.getSymbol();
        mFromCurrencyHeaderTxt.setText(currency);
        mFromCurrencyTableTxt.setText(currency);
        mCurrentBalTxt.setText(mFromCurrency.getmCurrentBalance());
        String toCurrencyBalance = mToCurreny != null ? mToCurreny.getmCurrentBalance() : "0";
        mToCurrentBalanceTxt.setText(toCurrencyBalance);
        mFromCurrencySymbolTxt.setText(mFromCurrency.getSymbol());

    }

    private void getBundleData() {
        if (getArguments() != null && getArguments().containsKey(FROM_CURRENCY_KEY) && getArguments()
                .containsKey(TO_CURRENCY_LIST_KEY)) {
            mFromCurrency = getArguments().getParcelable(FROM_CURRENCY_KEY);
            mToCurrencyList = getArguments().getParcelableArrayList(TO_CURRENCY_LIST_KEY);
        }
    }

    private void initUi() {
        if (getView() != null) {
            ButterKnife.bind(this, getView());
        }
    }

    @Override
    public void showProgress() {
        super.showProgress(getString(R.string.loading));
    }

    @Override
    public void dimissProgress() {
      super.hideProgress();
    }

    @Override
    public void showDialogMsg() {

    }
}
