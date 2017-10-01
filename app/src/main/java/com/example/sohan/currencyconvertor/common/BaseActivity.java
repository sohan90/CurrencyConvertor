package com.example.sohan.currencyconvertor.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.utils.DialogUtils;

/**
 * All activity should extend this BaseActivity class
 * BaseActivity for all sub activities
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void setToolBarTitle(String toolBarTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolBarText.setText(toolBarTitle);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        DialogUtils.getInstance().hideProgressDialog();
        DialogUtils.getInstance().cancelDefaultAlertDialog();
        super.onDestroy();
    }
}
