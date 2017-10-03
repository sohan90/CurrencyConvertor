package com.example.sohan.currencyconvertor.common;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.sohan.currencyconvertor.R;
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

    protected void setToolBarTitle(String toolBarTitle) {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        TextView toolBarText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolBarText.setText(toolBarTitle);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
    }

}
