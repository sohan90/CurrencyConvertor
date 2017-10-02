package com.example.sohan.currencyconvertor.modules.homescreen;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.models.CountryInfo;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.CountryListPresenterImpl;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.ICountryListView;
import com.example.sohan.currencyconvertor.modules.homescreen.contract.IHomeScreenView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class to show country list dialog
 */

public class CountryListDialogFragment extends DialogFragment implements ICountryListView, TextWatcher {
    private static final String EXTRA_LIST_KEY = "extra_list_key";
    @BindView(R.id.search)
    EditText mSearchEdt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private IHomeScreenView mHomeScreenView;
    private CountryListPresenterImpl mPresenter;
    private CountryListAdapter mAdapter;

    public static CountryListDialogFragment getInstance(List<CountryInfo> list) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_LIST_KEY, (ArrayList<? extends Parcelable>) list);
        CountryListDialogFragment fragment = new CountryListDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
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
        View view = inflater.inflate(R.layout.dialog_frag_country, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogWidthHeight();
    }

    private void setDialogWidthHeight() {
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUi();
        setPresenter();
        setListAdapter();
    }

    private void setPresenter() {
        mPresenter = new CountryListPresenterImpl(this);
    }

    private void setListAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new CountryListAdapter(getBundleData(), new CountryListAdapter.CallBack() {
            @Override
            public void onClick(CountryInfo countryInfo) {
              if (mHomeScreenView != null) {
                  mHomeScreenView.addCountryForCurrentBalance(countryInfo);
                  dismiss();
              }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<CountryInfo> getBundleData() {
        List<CountryInfo> countryInfoList = null;
        if (getArguments() != null && getArguments().containsKey(EXTRA_LIST_KEY)) {
            countryInfoList = getArguments().getParcelableArrayList(EXTRA_LIST_KEY);
        }
        return countryInfoList;
    }

    private void initUi() {
        if (getView() != null) {
            ButterKnife.bind(this, getView());
            mSearchEdt.addTextChangedListener(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mPresenter != null) {
            mPresenter.onFilter(s.toString(), getBundleData());
        }

    }

    @Override
    public void onFilter(List<CountryInfo> countryInfo) {
        mAdapter.setData(countryInfo);// update adapter
    }
}
