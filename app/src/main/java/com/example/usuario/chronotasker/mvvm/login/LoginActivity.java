package com.example.usuario.chronotasker.mvvm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.databinding.ActivityLoginBinding;
import com.example.usuario.chronotasker.mvvm.about.AboutActivity;
import com.example.usuario.chronotasker.mvvm.activity.ActivityUtils;
import com.example.usuario.chronotasker.mvvm.base.BaseActivity;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.mvvm.base.ViewModelHolder;
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
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    private OnFragmentActionListener selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        setupToolbar();

        // Link View and ViewModel
        findOrCreateViewFragment().setViewModel(findOrCreateViewModel());

        //btnSignup.setOnClickListener((View.OnClickListener) view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    /**
     * Initialises the Toolbar
     */
    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private LoginFragment findOrCreateViewFragment() {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_content);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.frame_content);
        }
        return loginFragment;
    }

    /**
     * Handles ViewModel retention in configuration changes
     * @return Either returns currently retained ViewModel or creates a new one
     */
    private LoginViewModel findOrCreateViewModel() {

        // Fetches a retained ViewModel from Fragment Manager
        @SuppressWarnings("unchecked")
        ViewModelHolder<LoginViewModel> retainedViewModel =
                (ViewModelHolder<LoginViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(LoginFragment.TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // Returns retained ViewModel
            return retainedViewModel.getViewmodel();
        } else {
            // Creates a new ViewModel and bind it to this Activity's lifecycle
            LoginViewModel viewModel = new LoginViewModel();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    LoginViewModel.TAG);
            return viewModel;
        }
    }

    /**
     * Arranca la ActivityPrincipal, HomeActivity, y finaliza.
     */
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Comprueba que si hay una sesión guardada anterior para iniciar la aplicación
        if (App.getApp().getPreferencesHelper().getCurrentUserRemember())
            navigateToHome();
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

    /**
     * Asigna el Fragment en uso como seleccionado para comunicación
     * entre Fragment y Activity
     *
     * @param selectedFragment
     */
    @Override
    public void setSelectedFragment(OnFragmentActionListener selectedFragment) {
        this.selectedFragment = selectedFragment;
    }
    @Override
    public void launchFragment(OnFragmentActionListener listener) {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame_content, (Fragment) listener)
                .commit();
    }
    @Override
    public void addFragment(OnFragmentActionListener listener) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_content, (Fragment) listener)
                .commit();
    }
    @Override
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }
}
