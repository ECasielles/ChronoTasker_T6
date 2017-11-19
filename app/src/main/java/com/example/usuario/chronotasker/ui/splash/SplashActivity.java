package com.example.usuario.chronotasker.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.prefs.UserPreferences;
import com.example.usuario.chronotasker.ui.login.LoginActivity;

/**
 * Activity de bienvenida. Lee el fichero preferencias para lanzar la actividad Login
 * o la actividad Task.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see android.app.Activity
 * @see UserPreferences
 */
public class SplashActivity extends AppCompatActivity {
    private static final long TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*
        //TODO: fix UserPreferences.getUserPreferencesHolder(getApplicationContext()).isRememberUserData()
        final boolean remember = false;
        final String classname;
        if(!remember)
            classname = LoginActivity.class.getName();
        else
            classname = TaskActivity.class.getName();
        */
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                //TODO: fix classname.getClass()
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        };
        new Handler().postDelayed(r, TIMEOUT);

    }

}
