package com.example.usuario.chronotasker;

import android.app.Application;

import com.example.usuario.chronotasker.data.prefs.PreferencesHelper;

import io.objectbox.BoxStore;

/**
 * Clase que hereda de Application y que mantiene el contexto
 * común a toda la aplicación y que permite el uso de preferencias
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Application
 * @see PreferencesHelper
 */
public class App extends Application {

    public static final String PREF_NAME = "preferences";
    private static App sApp;
    private PreferencesHelper mPreferencesHelper;
    private BoxStore mBoxStore;

    public static App getApp() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mBoxStore = MyObjectBox.builder().androidContext(App.this).build();
        mPreferencesHelper = PreferencesHelper.getInstance();
    }

    public PreferencesHelper getmPreferencesHelper() {
        return mPreferencesHelper;
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}
