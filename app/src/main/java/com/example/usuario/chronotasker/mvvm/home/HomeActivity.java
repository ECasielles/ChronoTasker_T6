package com.example.usuario.chronotasker.mvvm.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.databinding.HeaderNavviewBinding;
import com.example.usuario.chronotasker.mvvm.alarm.AlarmListFragment;
import com.example.usuario.chronotasker.mvvm.base.BaseActivity;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.mvvm.base.ViewModelHolder;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;
import com.example.usuario.chronotasker.mvvm.calendar.CalendarFragment;
import com.example.usuario.chronotasker.mvvm.game.GameSketchFragment;
import com.example.usuario.chronotasker.mvvm.game.GameViewModel;
import com.example.usuario.chronotasker.mvvm.login.LoginActivity;
import com.example.usuario.chronotasker.mvvm.task.list.TaskListFragment;
import com.example.usuario.chronotasker.utils.ActivityUtils;


/**
 * Activity que maneja el uso de la lista de tareas
 *
 * @author Enrique Casielles Lapeira
 * @version 2.0
 */
public class HomeActivity extends BaseActivity implements HomeNavigator {

    private DrawerLayout drawerLayout;
    private DrawerViewModel mViewModel;
    private HeaderNavviewBinding headerNavviewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();
        setupNavigationViewBinding();
        setupNavigationViewModel();

        launchFragment(new TaskListFragment());
    }


    /**
     * Método que inicializa el Listener NavigationItemSelected, y envía la opción
     * seleccionada al método navigationAction.
     * Se infla desde HeaderNavviewBinding
     */
    private void setupNavigationViewBinding() {
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
     * Inicializa el ViewModel del Navigator
     */
    private void setupNavigationViewModel() {
        mViewModel = ViewModelProviders.of(this).get(DrawerViewModel.class);
        headerNavviewBinding.setViewModel(mViewModel);
    }


    @Override
    public void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Cierra la sesión, finaliza la activity y lanza LoginActivity
     */
    private void navigateToLogin() {
        App.getApp().getPreferencesHelper().resetUser();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else if (!App.getApp().getPreferencesHelper().getCurrentUserRemember())
            navigateToLogin();
        else
            super.onBackPressed();
    }



    private boolean navigationAction(MenuItem item) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);


        if(selectedFragment instanceof GameSketchFragment) {
            ((GameSketchFragment) selectedFragment).stop();
        }

        switch (item.getItemId()) {
            case R.id.action_home:
                launchFragment(TaskListFragment.getInstance());
                item.setChecked(true);
                break;
            case R.id.action_alarm:
                launchFragment(AlarmListFragment.getInstance());
                item.setChecked(true);
                break;
            case R.id.action_calendar:
                launchFragment(CalendarFragment.getInstance());
                item.setChecked(true);
                break;
            case R.id.action_game:
                launchGameFragment();
                item.setChecked(true);
                break;
            //TODO: launch GeneralSettingsActivity, change theme
            //TODO: launch AccountSettingsActivity, change profile settings
            case R.id.action_settings:  //startActivity(new Intent(this, AccountSettingsActivity.class));
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

        getSupportActionBar().setTitle(item.getTitle());

        return true;
    }



    private void launchGameFragment() {
        if(selectedFragment instanceof GameSketchFragment) {
            ((GameSketchFragment) selectedFragment).resume();
        } else {
            GameSketchFragment instance = new GameSketchFragment();
            selectedFragment = (OnFragmentActionListener) getFragmentManager().findFragmentByTag(instance.getFragmentTag());
            if (selectedFragment == null)
                selectedFragment = instance;

            if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_content, (android.app.Fragment) selectedFragment)
                    .commit();

            Fragment viewModelHolder = getSupportFragmentManager().findFragmentByTag(selectedFragment.getViewModelTag());
            ViewModelHolder<?> retainedViewModel = (ViewModelHolder<?>) viewModelHolder;

            if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
                localViewModel = (NavigatorViewModel) retainedViewModel.getViewmodel();
            } else {
                localViewModel = selectedFragment.makeViewModel();
                ActivityUtils.addFragmentToActivity(
                        getSupportFragmentManager(),
                        ViewModelHolder.createContainer(localViewModel),
                        selectedFragment.getViewModelTag()
                );
            }
            ((GameSketchFragment) selectedFragment).setViewModel((GameViewModel) localViewModel);
        }
    }

    public void refreshCollection() {
        localViewModel.reset();
    }


}
