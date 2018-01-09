package com.example.usuario.chronotasker.ui.task;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.ui.task.fragment.TaskCreationFragment;
import com.example.usuario.chronotasker.ui.task.fragment.TaskListFragment;
import com.example.usuario.chronotasker.ui.task.presenter.TaskCreationPresenter;
import com.example.usuario.chronotasker.ui.task.presenter.TaskListPresenter;

public class TaskActivity extends AppCompatActivity implements TaskListFragment.TaskListListener,
        TaskCreationFragment.TaskCreationListener {

    TaskListFragment taskListFragment;
    TaskListPresenter taskListPresenter;
    TaskCreationFragment taskCreationFragment;
    TaskCreationPresenter taskCreationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        FragmentManager fragmentManager = getSupportFragmentManager();
        taskListFragment = (TaskListFragment) fragmentManager.findFragmentByTag(TaskListFragment.TAG);

        if (taskListFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            taskListFragment = TaskListFragment.newInstance();
            fragmentTransaction.add(android.R.id.content, taskListFragment, TaskListFragment.TAG);
            fragmentTransaction.commit();
        }

        taskListPresenter = new TaskListPresenter(taskListFragment);
        taskListFragment.setPresenter(taskListPresenter);
    }

    @Override
    public void addTask(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        taskCreationFragment = (TaskCreationFragment) fragmentManager.findFragmentByTag(TaskCreationFragment.TAG);

        if (taskCreationFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            taskCreationFragment = TaskCreationFragment.getInstance(bundle);
            fragmentTransaction.replace(android.R.id.content, taskCreationFragment, TaskCreationFragment.TAG);
            fragmentTransaction.commit();
        }

        taskCreationPresenter = new TaskCreationPresenter(taskCreationFragment);
        taskCreationFragment.setPresenter(taskCreationPresenter);
    }

    @Override
    public void reloadTasks() {
        getSupportFragmentManager().popBackStack();
    }


    //OVERFLOW MENU

    /**
     * Infla el menú de la esquina superior derecha. Común a toda la aplicación.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activities_start, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Muestra las opciones del menú y su comportamiento
     */
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
    }


}
