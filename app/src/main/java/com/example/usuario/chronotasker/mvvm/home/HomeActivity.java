package com.example.usuario.chronotasker.mvvm.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.databinding.HeaderNavviewBinding;
import com.example.usuario.chronotasker.mvvm.alarm.AlarmListFragment;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;
import com.example.usuario.chronotasker.mvvm.base.IMessageListener;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.mvvm.base.ViewModelHolder;
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
        implements OnFragmentActionListener.FragmentEventHandler, HomeNavigator, IMessageListener {

    private OnFragmentActionListener selectedFragment;
    private DrawerLayout drawerLayout;
    private HomeViewModel mViewModel;
    private HeaderNavviewBinding headerNavviewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        setupNavigationView();
        setupViewModel();
        setupToolbar();

        // Links View and ViewModel
        findOrCreateViewFragment().setViewModel(findOrCreateViewModel());
        //addFragment(TaskListFragment.getInstance(this));
    }


    /**
     * Método que inicializa el Listener NavigationItemSelected, y envía la opción
     * seleccionada al método navigationAction.
     */
    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.navview);

        headerNavviewBinding = DataBindingUtil.inflate(
                getLayoutInflater(), R.layout.header_navview, navigationView, false);

        navigationView.addHeaderView(headerNavviewBinding.getRoot());
        navigationView.setNavigationItemSelectedListener(this::navigationAction);
        navigationView.setCheckedItem(R.id.action_home);

        drawerLayout = findViewById(R.id.drawer_layout);
    }
    /**
     * Inicializa el ViewModel
     */
    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
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

        switch (item.getItemId()) {
            case R.id.action_home:
                popBackStack();
                item.setChecked(true);
                break;
            case R.id.action_alarm:
                launchFragment(AlarmListFragment.newInstance(this));
                item.setChecked(true);
                break;
            case R.id.action_game:
                getSupportFragmentManager().popBackStack();
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_content, new Sketch())
                        .commit();
                //launchFragment(GameFragment.newInstance(this, mViewModel));
                item.setChecked(true);
                break;
            case R.id.action_settings:  //launchSettingsActivity();
                break;
            case R.id.action_help:      //launchSettingsActivity();
                break;
            //TODO: launch AboutActivity
            //case R.id.action_about:      //launchAboutActivity();
            //    break;
            case R.id.action_logout:    navigateToLogin();
                break;
        }

        getSupportActionBar().setTitle(item.getTitle());
        return true;
    }


    private BaseFragment findOrCreateViewFragment() {
        setSelectedFragment((OnFragmentActionListener)
                getSupportFragmentManager().findFragmentById(R.id.frame_content));
        if (selectedFragment == null) {
            TaskListFragment taskListFragment = TaskListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    taskListFragment,
                    R.id.frame_content
            );
            setSelectedFragment(taskListFragment);
        }
        return (BaseFragment) selectedFragment;
    }


    /**
     * Handles ViewModel retention in configuration changes
     * @return Either returns currently retained ViewModel or creates a new one
     */
    private TaskListViewModel findOrCreateViewModel() {
        // Fetches a retained ViewModel from Fragment Manager
        ViewModelHolder<TaskListViewModel> retainedViewModel =
                (ViewModelHolder<TaskListViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(TaskListViewModel.TAG);
        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // Returns retained ViewModel
            return retainedViewModel.getViewmodel();
        } else {
            // Creates a new ViewModel and bind it to this Activity's lifecycle
            TaskListViewModel viewModel = new TaskListViewModel();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    TaskListViewModel.TAG
            );
            return viewModel;
        }
    }


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
        ((BaseFragment)listener).setViewModel(mViewModel);
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame_content, (Fragment) listener)
                .commit();
    }


    @Override
    public void addFragment(OnFragmentActionListener listener) {
        ((BaseFragment)listener).setViewModel(mViewModel);
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
