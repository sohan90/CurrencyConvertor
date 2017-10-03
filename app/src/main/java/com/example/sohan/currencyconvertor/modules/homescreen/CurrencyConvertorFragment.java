package com.example.sohan.currencyconvertor.modules.homescreen;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.example.sohan.currencyconvertor.common.Constants;
import com.example.sohan.currencyconvertor.models.CountryInfo;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.CurrencyConvertorImpl;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.ICurrencyConvertorView;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.IHomeScreenView;
import com.example.sohan.currencyconvertor.repository.CurrencyConvertorInteractorImpl;
import com.example.sohan.currencyconvertor.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * CurrencyConvertor fragment class .
 */

public class CurrencyConvertorFragment extends BaseFragment implements ICurrencyConvertorView {

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
    private CurrencyConvertorImpl mPresenter;
    @BindView(R.id.total_commission)
    TextView mTotalCommissionTxt;


    public static CurrencyConvertorFragment getInstance(CountryInfo fromCurrency,
                                                        ArrayList<CountryInfo> toCurrencyList) {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.currency_convertor_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUi();
        setPresenter();
        getBundleData();
        setData();
        setSpinnerData();
    }

    private void setPresenter() {
        CurrencyConvertorInteractorImpl interactor = CurrencyConvertorInteractorImpl.getInstance();
        mPresenter = new CurrencyConvertorImpl(this, interactor);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHomeScreenView != null) {
            super.setToolBarTitle(getString(R.string.currency_con_title));
            mHomeScreenView.updateToolBar(getString(R.string.currency_con_title));
            mHomeScreenView.hideActivityViews();
        }
    }

    private void setSpinnerData() {
        ArrayAdapter<CountryInfo> arrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_item, mToCurrencyList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        mSpinnerView.setAdapter(arrayAdapter);
        mSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mToCurreny = mToCurrencyList.get(position);
                updateToCurrencyBalance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpinnerView.setSelection(0);
            }
        });
    }

    @Override
    public void onCurrencyConversionSuccess(String fromAmountMsg, String toAccountMsg, String commisionFee) {

        String title = getString(R.string.success);
        String msg = getString(R.string.success_msg, fromAmountMsg, toAccountMsg, commisionFee,
                String.valueOf(Constants.COMMISSION_FEE));
        showDialogMsg(title, msg, true);


    }

    @Override
    public void onCurrencyConversionFailure(String error) {
        showDialogMsg(getString(R.string.oh_sorry), getString(R.string.trans_failed), false);
    }

    @Optional
    @OnClick(R.id.sell)
    public void sell(View view) {
        String amount = mSellingAmountEdt.getText().toString();
        if (!TextUtils.isEmpty(amount)) {

            boolean isValid = mPresenter.validateAmount(amount, mFromCurrency);
            if (isValid) {
                mPresenter.convertCurrency(amount, mFromCurrency, mToCurreny);
            } else {
                showDialogMsg(getString(R.string.oh_sorry), getString(R.string.low_bal_msg), false);
            }
        } else {
            showDialogMsg(getString(R.string.invalid_data), getString(R.string.enter_ur_amt), false);
        }

    }

    private void setData() {
        String currency = mFromCurrency.getIsoCode() + " " + mFromCurrency.getSymbol();
        mFromCurrencyHeaderTxt.setText(currency);
        mFromCurrencyTableTxt.setText(currency);
        String currentBal = mFromCurrency.getmCurrentBalance() + " " + mFromCurrency.getSymbol();
        mCurrentBalTxt.setText(currentBal);
        updateToCurrencyBalance();
        mFromCurrencySymbolTxt.setText(mFromCurrency.getSymbol());
        String totalFee = mPresenter.getTotalCommsionFeeFromPref();
        updateTotalCommissionFee(totalFee);

    }

    private void updateToCurrencyBalance() {
        mToCurrencyTxt.setText(mToCurreny.getIsoCode());
        String toCurrencyBalance = mToCurreny != null ? mToCurreny.getmCurrentBalance() : "0";
        String toCurrencyBal = toCurrencyBalance + " " + mToCurreny.getSymbol();
        mToCurrentBalanceTxt.setText(toCurrencyBal);
    }

    private void getBundleData() {
        if (getArguments() != null && getArguments().containsKey(FROM_CURRENCY_KEY) && getArguments()
                .containsKey(TO_CURRENCY_LIST_KEY)) {
            mFromCurrency = getArguments().getParcelable(FROM_CURRENCY_KEY);
            mToCurrencyList = getArguments().getParcelableArrayList(TO_CURRENCY_LIST_KEY);
            if (mToCurrencyList != null) {
                mToCurreny = mToCurrencyList.get(0);// setting default value
            }
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
    public void showDialogMsg(String title, String msg, final boolean popBackStack) {
        DialogUtils.getInstance().showDefaultAlertDialog(getContext(), title, msg, getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (getActivity() != null && mHomeScreenView != null && popBackStack) {
                            mHomeScreenView.udpateAdapterDataFromFragment();
                            getActivity().onBackPressed();
                        }

                    }
                }, null, null, false);
    }

    @Override
    public void updateTotalCommissionFee(String totalCommissionFee) {
        String totalFee = getString(R.string.total_commission_fee, totalCommissionFee);
        mTotalCommissionTxt.setText(totalFee);
    }

    @Override
    public Context getContxt() {
        return getContext();
    }
}
