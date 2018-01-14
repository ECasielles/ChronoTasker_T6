package com.example.usuario.chronotasker.ui.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Activity principal con los elementos comunes a toda la aplicación
 * SIN TERMINAR
 */
public class BaseActivity extends AppCompatActivity {

    /*
    TODO: Migrate to NavigationDrawer

    //OVERFLOW MENU
    *//**
     * Infla el menú de la esquina superior derecha. Común a toda la aplicación.
     *//*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_start, menu);
        return super.onCreateOptionsMenu(menu);
    }

    *//**
     * Muestra las opciones del menú y su comportamiento
     *//*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_general_settings:
                //TODO: launch GeneralSettingsActivity, change theme and language
                break;
            case R.id.action_menu_account_settings:
                //TODO: launch AccountSettingsActivity, change profile settings, remember credentials
                break;
            case R.id.action_menu_about:
                //TODO: launch AboutActivity
                break;
            case R.id.action_menu_logout:
                //TODO: end session, launch LoginActivity
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
