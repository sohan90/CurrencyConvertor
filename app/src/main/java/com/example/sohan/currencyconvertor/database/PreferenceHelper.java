package com.example.sohan.currencyconvertor.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.sohan.currencyconvertor.model.CountryInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Shared Preference helper class
 */
public class PreferenceHelper {

    private static final String PREF_NAME = "merchant_pref";

    public static String getPrefString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void storePrefString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int getPrefInt(Context context, String key) {
        int value = 0;
        if (containsKey(context, key)) {
            SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            value = sp.getInt(key, 0);

        }
        return value;
    }

    public static void storePrefInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static Boolean getPrefBool(Context context, String key) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return sp.getBoolean(key, false);
        }
        return false;
    }

    public static void storePrefBool(Context context, String key, boolean value) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Editor editor = sp.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }
    }

    public static boolean containsKey(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static void removeKeyFromPreference(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public static void storeListOfObject(Context context, String key, List<CountryInfo> obj) {
        String userPrefMapString = new Gson().toJson(obj);
        SharedPreferences sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor sharedPrefEditor = sharedPreference.edit();
        sharedPrefEditor.putString(key, userPrefMapString);
        sharedPrefEditor.apply();
    }

    public static List<CountryInfo> getListOfObject(Context context, String key) {
        SharedPreferences sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        //get from shared prefs
        String storedHashMapString = sharedPreference.getString(key, "");
        java.lang.reflect.Type type = new TypeToken<List<CountryInfo>>() {
        }.getType();
        return new Gson().<List<CountryInfo>>fromJson(storedHashMapString, type);
    }


    public static void storeObject(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor ed = sp.edit();
        Gson gson = new Gson();
        if (object != null) {
            String json = gson.toJson(object);
            ed.putString(key, json);
        }
        ed.apply();
    }

    public static Object getObject(Context context, String key, Class type) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sp.getString(key, "");
            return gson.fromJson(json, type);
        }
        return null;
    }

}
