package com.example.usuario.chronotasker.data.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.usuario.chronotasker.App;
import com.google.gson.Gson;

/**
 * Clase que maneja las preferencias de usuario de la aplicación.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see SharedPreferences
 */
public class PreferencesHelper implements AccountPreferencesHelper {

    private static final String TAG = "PreferencesHelper";
    private static PreferencesHelper helper;
    private final SharedPreferences preferences;

    private PreferencesHelper() {
        preferences = App.getApp().getSharedPreferences(App.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance() {
        if (helper == null)
            helper = new PreferencesHelper();
        return helper;
    }

    public static void saveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }

    public static <Type> Type getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<Type> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
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
