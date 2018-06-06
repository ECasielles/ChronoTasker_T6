package com.example.usuario.chronotasker.mvvm.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.databinding.ActivityBaseBinding;
import com.example.usuario.chronotasker.mvvm.activity.IMessageListener;
import com.example.usuario.chronotasker.mvvm.alarm.AlarmListFragment;
import com.example.usuario.chronotasker.mvvm.base.BaseActivity;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.mvvm.login.LoginActivity;
import com.example.usuario.chronotasker.mvvm.settings.AccountSettingsActivity;
import com.example.usuario.chronotasker.mvvm.task.fragment.TaskListFragment;

import java.util.Objects;


/**
 * Activity que maneja el uso de la lista de tareas
 *
 * @author Enrique Casielles Lapeira
 */
public class HomeActivity extends BaseActivity<ActivityBaseBinding, HomeViewModel>
        implements OnFragmentActionListener.FragmentEventHandler, HomeNavigator, IMessageListener {

    public FloatingActionButton floatingActionButton;
    private OnFragmentActionListener selectedFragment;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //Inicializa el ViewModel
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        //mViewModel.setNavigator(this);

        floatingActionButton = findViewById(R.id.floationActionButton);
        drawerLayout = findViewById(R.id.drawer_layout);

        setupToolbar();

        setupNavigationView();

        addFragment(TaskListFragment.getInstance(this));
    }


    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar())
                .setHomeAsUpIndicator(R.drawable.ic_action_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Método que inicializa el Listener NavigationItemSelected, y envía la opción
     * seleccionada al método navigationAction.
     */
    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.navview);
        navigationView.setNavigationItemSelectedListener(this::navigationAction);
        navigationView.setCheckedItem(R.id.action_home);
    }


    private boolean navigationAction(MenuItem item) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.action_home:
                popBackStack();
                item.setChecked(true);
                break;
            case R.id.action_alarm:
                launchFragment(AlarmListFragment.newInstance(this));
                item.setChecked(true);
                break;
            case R.id.action_settings:  launchSettingsActivity();
                break;
            case R.id.action_help:
                break;
            case R.id.action_logout:    navigateToLogin();
                break;
        }

        getSupportActionBar().setTitle(item.getTitle());
        return true;
    }


    private void launchSettingsActivity() {
        //TODO: launch AboutActivity
        //TODO: launch GeneralSettingsActivity, change theme and language
        //TODO: launch AccountSettingsActivity, change profile settings
        startActivity(new Intent(this, AccountSettingsActivity.class));
    }


    private void navigateToLogin() {
        App.getApp().getPreferencesHelper().resetUser();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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


    /**
     * Abre el panel lateral al pulsar el botón Home de la Toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
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
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }


    /**
     * Fuerza al último fragment añadido a la pila a consumir el evento de cierre.
     * Si el panel lateral está abierto, lo cierra.
     * Si el usuario quiere que la aplicación lo recuerde, se cierra la aplicación sin
     * cerrar sesión.
     * Si el usuario no quiere que la aplicación lo recuerde,
     * se abre la Activity de Login.
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            if (!App.getApp().getPreferencesHelper().getCurrentUserRemember()) {
                navigateToLogin();
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public void show(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
