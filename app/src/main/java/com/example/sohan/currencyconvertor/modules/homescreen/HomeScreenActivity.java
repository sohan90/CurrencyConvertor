package com.example.sohan.currencyconvertor.modules.homescreen;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.common.BaseActivity;
import com.example.sohan.currencyconvertor.model.CountryInfo;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.HomeScreenPresenterImpl;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.IHomeScreenView;
import com.example.sohan.currencyconvertor.repository.CurrencyConvertorInteractorImpl;
import com.example.sohan.currencyconvertor.utils.DialogUtils;
import com.example.sohan.currencyconvertor.utils.FragmentHelper;
import com.example.sohan.currencyconvertor.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Home Screen of app used to show current balance of user account. and we are following mvp design
 * pattern
 */

public class HomeScreenActivity extends BaseActivity implements IHomeScreenView {

    private static final String TAG = HomeScreenActivity.class.getSimpleName();
    public static final String CURRENCY_CONVERTOR_FRAG_TAG = "currency_convertor_fragment";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    AccountBalanceAdapter mAdapter;
    private HomeScreenPresenterImpl mPresenter;
    private List<CountryInfo> mAllCountryInfoList;
    private List<CountryInfo> mCurrentBalanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        setPresenter();
        fetchAllCountryList();
    }

    private void setPresenter() {
        CurrencyConvertorInteractorImpl interactor = CurrencyConvertorInteractorImpl.getInstance();
        mPresenter = new HomeScreenPresenterImpl(this, interactor);
    }

    private void initUi() {
        ButterKnife.bind(this);//initializing butterKnife lib here
        setToolBarTitle(getString(R.string.home_screen_title));
        setRecylerViewAdapter();
    }

    /**
     * Initially setting empty list and updating once we get the list from api call
     */
    private void setRecylerViewAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AccountBalanceAdapter(new ArrayList<CountryInfo>()
                , new AccountBalanceAdapter.OnItemClickListener() {
            @Override
            public void OnSellButtonClick(CountryInfo selectedCurrentBalanceAccount) {
                //launch currency conversion screen
                launchCurrencyConvetorFragment(selectedCurrentBalanceAccount);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void launchCurrencyConvetorFragment(CountryInfo fromCurrentAccount) {
        CurrencyConvertorFragment fragment = CurrencyConvertorFragment.getInstance(fromCurrentAccount,
                (ArrayList<CountryInfo>) mCurrentBalanceList);
        FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.container,
                 fragment, CURRENCY_CONVERTOR_FRAG_TAG, CURRENCY_CONVERTOR_FRAG_TAG);

    }

    @OnClick(R.id.fab)
    public void onAddCountryClick(View view) {
        //show country list dialog fragment
    }

    private void fetchAllCountryList() {
        if (mPresenter != null) {
            mPresenter.fetchAllCountry(getString(R.string.country_url));
        }
    }


    @Override
    public void showProgress() {
        DialogUtils.getInstance().displayProgressDailog(this, getString(R.string.loading));
    }

    @Override
    public void dimissProgress() {
        DialogUtils.getInstance().hideProgressDialog();
    }

    @Override
    public void updateCurrentBalanceAdapter(List<CountryInfo> countryInfoList) {
        LogUtils.LOGD(TAG, "AllCountryListSize" + countryInfoList.size());
        mAllCountryInfoList = countryInfoList;
        mCurrentBalanceList = mPresenter.getCurrentBalanceList(countryInfoList);
        LogUtils.LOGD(TAG, "currentBalanceListSize " + countryInfoList.size());
        mAdapter.setData(mCurrentBalanceList); // updating the adapter  with currentBalanceList here
    }
}
