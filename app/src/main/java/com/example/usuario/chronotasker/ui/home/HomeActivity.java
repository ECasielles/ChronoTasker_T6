package com.example.usuario.chronotasker.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.usuario.chronotasker.App;
import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.ui.alarm.AlarmListFragment;
import com.example.usuario.chronotasker.ui.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.ui.login.LoginActivity;
import com.example.usuario.chronotasker.ui.settings.AccountSettingsActivity;
import com.example.usuario.chronotasker.ui.task.fragment.TaskListFragment;

/**
 * Activity que maneja el uso de la lista de tareas
 *
 * @author Enrique Casielles Lapeira
 */
public class HomeActivity extends AppCompatActivity implements OnFragmentActionListener.FragmentEventHandler {
    private static final String TAG = "HomeActivity";
    public FloatingActionButton floatingActionButton;
    private OnFragmentActionListener selectedFragment;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        floatingActionButton = findViewById(R.id.floationActionButton);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupNavigationView();

        addFragment(TaskListFragment.getInstance(this));
        navigationView.setCheckedItem(R.id.action_home);
    }

    /**
     * Método que inicializa el Listener NavigationItemSelected, y envía la opción
     * seleccionada al método navigationAction.
     */
    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationAction(item);
                return true;
            }
        });
    }

    public void navigationAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                popBackStack();
                item.setChecked(true);
                break;
            case R.id.action_alarm:
                launchFragment(AlarmListFragment.newInstance(this));
                item.setChecked(true);
                break;
            case R.id.action_settings:
                launchSettingsActivity();
                break;
            case R.id.action_help:
                break;
            case R.id.action_logout:
                App.getApp().getmPreferencesHelper().setCurrentUserRemember(false);
                App.getApp().getmPreferencesHelper().setCurrentUserPassword(null);
                App.getApp().getmPreferencesHelper().setCurrentUserName(null);
                App.getApp().getmPreferencesHelper().setCurrentUserId(-1);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        getSupportActionBar().setTitle(item.getTitle());
        drawerLayout.closeDrawer(GravityCompat.START);
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

    public void launchSettingsActivity() {
        //TODO: launch AboutActivity
        //TODO: launch GeneralSettingsActivity, change theme and language
        //TODO: launch AccountSettingsActivity, change profile settings, remember credentials
        //Si está abierto, lo cierra
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, AccountSettingsActivity.class));
        }
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (selectedFragment == null || !selectedFragment.onBackPressed())
            if (!App.getApp().getmPreferencesHelper().getCurrentUserRemember()) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else
                super.onBackPressed();
    }

}
