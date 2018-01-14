package com.example.usuario.chronotasker.data.prefs;

import android.app.Application;

/**
 * Clase que hereda de Application y que mantiene el contexto
 * común a toda la aplicación y que permite el uso de preferencias
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Application
 * @see PreferencesHelper
 */
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
        preferencesHelper = PreferencesHelper.getInstance();
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public static ChronoTaskerApplication getContext() {
        return context;
    }

}
