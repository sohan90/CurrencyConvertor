package com.example.sohan.currencyconvertor.utils;

import android.util.Log;

/**
 * A wrapper class for logging
 */

public class LogUtils {

    public static final boolean ENABLE_LOG = false;

    public static void LOGD(final String tag, String message) {
        if (ENABLE_LOG) {
            Log.d(tag, message);
        }
    }

    public static void LOGV(final String tag, String message) {
        if (ENABLE_LOG) {
            Log.v(tag, message);
        }
    }

    public static void LOGI(final String tag, String message) {
        Log.i(tag, message);
    }

    public static void LOGW(final String tag, String message) {
        Log.w(tag, message);
    }

    public static void LOGE(final String tag, String message) {
        Log.e(tag, message);
    }

    private LogUtils() {
    }
}
