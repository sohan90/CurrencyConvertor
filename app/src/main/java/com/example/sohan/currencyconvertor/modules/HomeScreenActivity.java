package com.example.sohan.currencyconvertor.modules;

import android.os.Bundle;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.common.BaseActivity;

/**
 * Home Screnn of app
 */
public class HomeScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {

    }
}
