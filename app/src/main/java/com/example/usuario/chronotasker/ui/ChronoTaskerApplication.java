package com.example.usuario.chronotasker.ui;

import android.app.Application;

import com.example.usuario.chronotasker.data.prefs.PreferencesHelper;

public class ChronoTaskerApplication extends Application {

    public static final String PREF_NAME = "preferences";

    private PreferencesHelper preferencesHelper;
    private static ChronoTaskerApplication context;

    public ChronoTaskerApplication() {
        context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferencesHelper = preferencesHelper.getInstance();
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public static ChronoTaskerApplication getContext() {
        return context;
    }

}
