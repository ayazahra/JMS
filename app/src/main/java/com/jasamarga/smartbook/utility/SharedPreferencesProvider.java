package com.jasamarga.smartbook.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by apridosandyasa on 8/11/16.
 */
public class SharedPreferencesProvider {

    private static SharedPreferencesProvider instance = new SharedPreferencesProvider();

    private final String pref_api_key = "prefApiKey";
    private final String pref_login = "prefLogin";
    private final String pref_npp = "prefNpp";

    public static SharedPreferencesProvider getInstance() {
        return instance;
    }


    public String getApiKey(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_api_key, "null");
    }

    public void setApiKey(Context context, String api_key) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_api_key, api_key);
        editor.commit();
    }

    public boolean getLogin(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getBoolean(pref_login, false);
    }

    public void setLogin(Context context, boolean login) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(pref_login, login);
        editor.commit();
    }

    public String getNpp(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(pref_npp, "null");
    }

    public void setNpp(Context context, String npp) {
        SharedPreferences shared = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(pref_npp, npp);
        editor.commit();
    }

}
