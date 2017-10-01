package com.example.sohan.currencyconvertor.common;

import android.support.v4.app.Fragment;

import com.example.sohan.currencyconvertor.utils.DialogUtils;

/**
 * BaseFragment for all sub fragment class
 */

public class BaseFragment extends Fragment {

    public void showProgress(String msg){
        DialogUtils.getInstance().displayProgressDailog(getContext(), msg);
    }

    public void hideProgress(){
        DialogUtils.getInstance().hideProgressDialog();
    }
}
