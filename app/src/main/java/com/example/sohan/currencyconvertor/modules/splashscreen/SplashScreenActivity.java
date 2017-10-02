package com.example.sohan.currencyconvertor.modules.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.sohan.currencyconvertor.R;
import com.example.sohan.currencyconvertor.common.BaseActivity;
import com.example.sohan.currencyconvertor.common.Constants;
import com.example.sohan.currencyconvertor.modules.homescreen.HomeScreenActivity;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initHandler();
    }

    private void initHandler() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchHomeScreenActivity();
                finish();

            }
        }, Constants.SPLASH_DELAY);
    }

    private void launchHomeScreenActivity() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
