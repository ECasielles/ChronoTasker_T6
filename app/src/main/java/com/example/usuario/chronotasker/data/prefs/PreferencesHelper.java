package com.example.usuario.chronotasker.data.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.usuario.chronotasker.ui.ChronoTaskerApplication;

/**
 * Clase que maneja las preferencias de usuario de la aplicación.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see SharedPreferences
 */
public class PreferencesHelper implements AccountPreferencesHelper {

    private static final String TAG = "PreferencesHelper";

    private final SharedPreferences preferences;
    private static PreferencesHelper helper;

    private PreferencesHelper() {
        preferences = ChronoTaskerApplication.getContext().getSharedPreferences(ChronoTaskerApplication.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance() {
        if (helper == null)
            helper = new PreferencesHelper();
        return helper;
    }

    public int getCurrentUserId() {
        return preferences.getInt(PREF_KEY_CURRENT_USER_ID, -1);
    }

    public void setCurrentUserId(int id) {
        preferences.edit().putInt(PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    public String getCurrentUserName() {
        return preferences.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    public void setCurrentUserName(String name) {
        preferences.edit().putString(PREF_KEY_CURRENT_USER_NAME, name).apply();
    }

    public String getCurrentUserPassword() {
        return preferences.getString(PREF_KEY_CURRENT_USER_PASSWORD, null);
    }

    public void setCurrentUserPassword(String password) {
        preferences.edit().putString(PREF_KEY_CURRENT_USER_PASSWORD, password).apply();
    }

    public Boolean getCurrentUserRemember() {
        return preferences.getBoolean(PREF_KEY_CURRENT_USER_REMEMBER, false);
    }

    public void setCurrentUserRemember(Boolean remember) {
        preferences.edit().putBoolean(PREF_KEY_CURRENT_USER_REMEMBER, remember).apply();
    }

}
