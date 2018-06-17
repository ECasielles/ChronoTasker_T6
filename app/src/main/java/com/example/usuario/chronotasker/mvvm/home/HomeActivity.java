package com.example.usuario.chronotasker.mvvm.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.databinding.HeaderNavviewBinding;
import com.example.usuario.chronotasker.mvvm.alarm.AlarmListFragment;
import com.example.usuario.chronotasker.mvvm.alarm.AlarmViewModel;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.mvvm.calendar.CalendarFragment;
import com.example.usuario.chronotasker.mvvm.calendar.CalendarViewModel;
import com.example.usuario.chronotasker.mvvm.game.Sketch;
import com.example.usuario.chronotasker.mvvm.login.LoginActivity;
import com.example.usuario.chronotasker.mvvm.settings.AccountSettingsActivity;
import com.example.usuario.chronotasker.mvvm.task.list.TaskListFragment;
import com.example.usuario.chronotasker.mvvm.task.list.TaskListViewModel;
import com.example.usuario.chronotasker.utils.ActivityUtils;

import java.util.Objects;


/**
 * Activity que maneja el uso de la lista de tareas
 *
 * @author Enrique Casielles Lapeira
 * @version 2.0
 */
public class HomeActivity extends AppCompatActivity
        implements OnFragmentActionListener.FragmentEventHandler, HomeNavigator {

    private OnFragmentActionListener selectedFragment;
    private DrawerLayout drawerLayout;
    private DrawerViewModel mViewModel;
    private HeaderNavviewBinding headerNavviewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupNavigationView();
        setupViewModel();
        setupToolbar();

        // Links View and ViewModel
        addFragment(TaskListFragment.getInstance(this,
                    TaskListFragment.class, TaskListViewModel.class));
    }


    /**
     * Método que inicializa el Listener NavigationItemSelected, y envía la opción
     * seleccionada al método navigationAction.
     * Se infla desde HeaderNavviewBinding
     */
    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.navview);

        headerNavviewBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.header_navview, navigationView, false);

        navigationView.addHeaderView(headerNavviewBinding.getRoot());
        navigationView.setNavigationItemSelectedListener(this::navigationAction);
        navigationView.setCheckedItem(R.id.action_home);

        drawerLayout = findViewById(R.id.drawer_layout);

        headerNavviewBinding.executePendingBindings();
    }
    /**
     * Inicializa el ViewModel
     */
    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(DrawerViewModel.class);
        mViewModel.setNavigator(this);
        headerNavviewBinding.setViewModel(mViewModel);
    }
    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar())
                .setHomeAsUpIndicator(R.drawable.ic_action_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private boolean navigationAction(MenuItem item) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);

        popBackStack();

        switch (item.getItemId()) {
            case R.id.action_home:
                launchFragment(TaskListFragment.getInstance(this,
                        TaskListFragment.class, TaskListViewModel.class));
                item.setChecked(true);
                break;
            case R.id.action_alarm:
                launchFragment(AlarmListFragment.getInstance(this,
                        AlarmListFragment.class, AlarmViewModel.class));
                item.setChecked(true);
                break;
            case R.id.action_calendar:
                launchFragment(CalendarFragment.getInstance(this,
                        CalendarFragment.class, CalendarViewModel.class));
                item.setChecked(true);
                break;
            case R.id.action_game:
                getSupportFragmentManager().popBackStack();
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_content, new Sketch())
                        .commit();
                //launchFragment(GameFragment.getInstance(this, mViewModel));
                item.setChecked(true);
                break;
            case R.id.action_settings:  //launchSettingsActivity();
                break;
            case R.id.action_help:      //launchSettingsActivity();
                break;
            //TODO: launch AboutActivity
            //case R.id.action_about:      //launchAboutActivity();
            //    break;
            case R.id.action_logout:
                navigateToLogin();
                break;
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle(item.getTitle());

        return true;
    }


    //INICIALIZA EL FRAGMENT EN CAMBIOS DE CONFIGURACION E INICIO
    /*private void findOrCreateViewFragment() {
        BaseFragment fragment =
                (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        setSelectedFragment(fragment == null ? TaskListFragment.newInstance() : fragment);
        addFragment(selectedFragment);
    }
    *//**
     * Handles ViewModel retention in configuration changes
     * @return Either returns currently retained ViewModel or creates a new one
     *//*
    private BaseViewModel findOrCreateViewModel() {
        // Fetches a retained ViewModel from Fragment Manager
        ViewModelHolder<?> retainedViewModel
                = (ViewModelHolder<?>) getRetainedViewModel((BaseFragment) selectedFragment);

        // Returns retained ViewModel
        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            return (BaseViewModel) retainedViewModel.getViewmodel();
        } else {
            // Creates a new ViewModel and binds it to this Activity's lifecycle
            BaseViewModel viewModel = ((BaseFragment) selectedFragment).makeViewModel();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    viewModel.getTag()
            );
            return viewModel;
        }
    }
    private Fragment getRetainedViewModel(@Nullable BaseFragment baseFragment) {
        return baseFragment != null && baseFragment.getViewModel() != null ?
                getSupportFragmentManager().findFragmentByTag(
                        baseFragment.getViewModel().getTag()
                ) : null;
    }*/


    private void launchSettingsActivity() {
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
        setSelectedFragment(listener);
        ActivityUtils.replaceFragmentInActivity(
                getSupportFragmentManager(),
                (Fragment) listener,
                R.id.frame_content
        );
    }

    @Override
    public void addFragment(OnFragmentActionListener listener) {
        setSelectedFragment(listener);
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                (Fragment) listener,
                R.id.frame_content
        );
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
     * @param listener
     */
    @Override
    public void setSelectedFragment(OnFragmentActionListener listener) {
        selectedFragment = listener;
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



    private void showError(String e) {
        Snackbar.make((View) selectedFragment, "Error: "  + e, Snackbar.LENGTH_LONG);
    }


}
