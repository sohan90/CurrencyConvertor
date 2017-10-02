package com.example.sohan.currencyconvertor.modules.homescreen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.common.BaseActivity;
import com.example.sohan.currencyconvertor.models.CountryInfo;
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
    @BindView(R.id.fab)
    FloatingActionButton mFloatingBtn;

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
        if (mPresenter.getCurrentBalanceListFromPref() == null) { // initially list will be  null
            fetchAllCountryList();// api call only for the first time then retrieving it from preference
        } else {
            updateAdapterFromPref();
            updateCountryListFromPref();
        }
    }

    private void updateCountryListFromPref() {
        mAllCountryInfoList = mPresenter.getAllCountryInfoFromPref();
    }

    private void updateAdapterFromPref() {
        mCurrentBalanceList = mPresenter.getCurrentBalanceListFromPref();
        mAdapter.setData(mCurrentBalanceList);
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
        List<CountryInfo> uniqueList = mPresenter.getUniqueCountryInf(fromCurrentAccount, mCurrentBalanceList);
        CurrencyConvertorFragment fragment = CurrencyConvertorFragment.getInstance(fromCurrentAccount,
                (ArrayList<CountryInfo>) uniqueList);

        FragmentHelper.replaceFragment(getSupportFragmentManager(), R.id.container,
                fragment, CURRENCY_CONVERTOR_FRAG_TAG, CURRENCY_CONVERTOR_FRAG_TAG);

    }

    @OnClick(R.id.fab)
    public void onAddCountryClick(View view) {
        if (mAllCountryInfoList != null && mAllCountryInfoList.size() != 0) {
            List<CountryInfo> list = mPresenter.removeAlreadyAddedCurrency(mCurrentBalanceList, mAllCountryInfoList);
            CountryListDialogFragment dialogFragment = CountryListDialogFragment.getInstance(list);
            dialogFragment.show(getSupportFragmentManager(), null);
        }
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

    /**
     * update respective data to adapter after the country info api call
     *
     * @param countryInfoList
     */
    @Override
    public void updateCurrentBalanceAdapter(List<CountryInfo> countryInfoList) {
        LogUtils.LOGD(TAG, "AllCountryListSize" + countryInfoList.size());
        mAllCountryInfoList = new ArrayList<>(countryInfoList);
        mCurrentBalanceList = mPresenter.getCurrentBalanceList(countryInfoList);
        LogUtils.LOGD(TAG, "currentBalanceListSize " + countryInfoList.size());
        mAdapter.setData(mCurrentBalanceList); // updating the adapter  with currentBalanceList here
        saveCurrentBalanceListToPref();
    }

    @Override
    public void updateToolBar(String title) {
        setToolBarTitle(title);
    }

    @Override
    public void hideActivityViews() {
        mFloatingBtn.setVisibility(View.INVISIBLE);
    }

    public void showActivityViews() {
        mFloatingBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (FragmentHelper.popBackStackImmediate(getSupportFragmentManager())) {
            updateToolBar(getString(R.string.home_screen_title));
            showActivityViews();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * After the currency conversion api call updating the adapter data to reflect the changes
     * and save list to preference
     */
    @Override
    public void udpateAdapterDataFromFragment() {
        saveCurrentBalanceListToPref();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void saveCurrentBalanceListToPref(){
        /*saving to preference as list's  respective model are updated in currency convertor fragment*/
        mPresenter.saveCurrentBalaneListToPref(mCurrentBalanceList);
    }

    @Override
    public void addCountryForCurrentBalance(CountryInfo countryInfo) {
        if (countryInfo != null) {
            mCurrentBalanceList.add(countryInfo);
            saveCurrentBalanceListToPref();
            mAdapter.setData(mCurrentBalanceList); // update adapter
        }
    }

    @Override
    public Context getCtxt() {
        return this;
    }
}
