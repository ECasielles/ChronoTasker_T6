package com.example.usuario.chronotasker.mvvm.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.usuario.chronotasker.R;

public class GeneralSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_general_settings);
    }
}
