package com.example.usuario.chronotasker.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.ui.alarm.AlarmListFragment;
import com.example.usuario.chronotasker.ui.settings.AccountSettingsActivity;
import com.example.usuario.chronotasker.ui.task.fragment.TaskCreationFragment;
import com.example.usuario.chronotasker.ui.task.fragment.TaskListFragment;
import com.example.usuario.chronotasker.ui.task.presenter.TaskCreationPresenter;

/**
 * Activity que maneja el uso de la lista de tareas
 *
 * @author Enrique Casielles Lapeira
 */
public class HomeActivity extends AppCompatActivity implements
        TaskListFragment.TaskListListener, TaskCreationFragment.TaskCreationListener {

    public FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TaskCreationFragment taskCreationFragment;
    private TaskCreationPresenter taskCreationPresenter;

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
        navigationView.setCheckedItem(R.id.action_home);

        TaskListFragment taskListFragment =
                (TaskListFragment) getSupportFragmentManager().findFragmentByTag(TaskListFragment.TAG);
        if (taskListFragment == null)
            taskListFragment = TaskListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, taskListFragment, TaskListFragment.TAG)
                .commit();
    }

    @Override
    public void addTask(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        taskCreationFragment = (TaskCreationFragment) fragmentManager.findFragmentByTag(TaskCreationFragment.TAG);

        if (taskCreationFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            taskCreationFragment = TaskCreationFragment.getInstance(bundle);
            fragmentTransaction.addToBackStack(TaskCreationFragment.TAG);
            fragmentTransaction.replace(R.id.frame_content, taskCreationFragment, TaskCreationFragment.TAG);
            fragmentTransaction.commit();
        }

        taskCreationPresenter = new TaskCreationPresenter(taskCreationFragment);
        taskCreationFragment.setPresenter(taskCreationPresenter);
    }

    @Override
    public void reloadTasks() {
        getSupportFragmentManager().popBackStack();
    }

    /**
     * Método que inicializa el Listener NavigationItemSelected, y envía la opción
     * seleccionada al método launchFragment.
     */
    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                launchFragment(item);
                return true;
            }
        });
    }

    public void launchSettingsActivity() {
        //TODO: end session, launch LoginActivity
        //TODO: launch AboutActivity
        //TODO: launch GeneralSettingsActivity, change theme and language
        //TODO: launch AccountSettingsActivity, change profile settings, remember credentials
        //Si está abierto, lo cierra
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this, AccountSettingsActivity.class));
            //startActivity(new Intent(BaseActivity.this, GeneralSettingsActivity.class));
        }
    }

    public void launchFragment(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_home:
                fragment = TaskListFragment.newInstance();
                break;
            case R.id.action_alarm:
                fragment = AlarmListFragment.newInstance();
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();

        //No hay que deseleccionar en los elementos del grupo de menú
        item.setChecked(true);

        //TODO: Deseleccionar las opciones de help y settings, que no están en un grupo
        getSupportActionBar().setTitle(item.getTitle());
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Abre el panel lateral al pulsar el botón Home de la Toolbar
     *
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
     * Cierra el último fragment añadido a la pila.
     * Si el panel lateral está abierto, lo cierra.
     */
    @Override
    public void onBackPressed() {
        //TODO: Watch carefully if this is the right order of operations
        if (getFragmentManager().getBackStackEntryCount() != 0)
            getFragmentManager().popBackStack();
        else if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
            this.drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

}
