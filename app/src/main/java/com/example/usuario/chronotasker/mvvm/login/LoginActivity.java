package com.example.usuario.chronotasker.mvvm.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.mvvm.about.AboutActivity;
import com.example.usuario.chronotasker.mvvm.base.BaseActivity;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.mvvm.home.HomeActivity;


/**
 * Clase Activity de la vista principal, Login, desde la que
 * el usuario accede validando sus credenciales pudiendo recordarlos y
 * puede también registrarse accediendo a la vista de Registro.
 *
 * @author Enrique Casielles Lapeira
 * @version 2.0
 * @see android.support.v7.app.AppCompatActivity
 * @see LoginViewModel
 * @see OnFragmentActionListener
 */
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupToolbar();
        launchFragment(new LoginFragment());
    }


    public void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.login_title);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (App.getApp().getPreferencesHelper().getCurrentUserRemember())
            navigateToHome();
    }


    /**
     * Arranca la ActivityPrincipal, HomeActivity, y finaliza.
     */
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_start, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }


}
