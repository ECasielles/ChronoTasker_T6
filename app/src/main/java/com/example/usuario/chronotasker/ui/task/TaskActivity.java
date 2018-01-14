package com.example.usuario.chronotasker.ui.task;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.ui.base.BaseActivity;
import com.example.usuario.chronotasker.ui.task.fragment.TaskCreationFragment;
import com.example.usuario.chronotasker.ui.task.fragment.TaskListFragment;
import com.example.usuario.chronotasker.ui.task.presenter.TaskCreationPresenter;
import com.example.usuario.chronotasker.ui.task.presenter.TaskListPresenter;

/**
 * Activity que maneja el uso de la lista de tareas
 *
 * @author Enrique Casielles Lapeira
 * @see BaseActivity
 */
public class TaskActivity extends BaseActivity implements TaskListFragment.TaskListListener,
        TaskCreationFragment.TaskCreationListener {

    private Toolbar toolbar;
    //Es público para que los Fragment lo puedan acceder y modificar
    public FloatingActionButton floatingActionButton;

    private TaskListFragment taskListFragment;
    private TaskListPresenter taskListPresenter;
    private TaskCreationFragment taskCreationFragment;
    private TaskCreationPresenter taskCreationPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton
        floatingActionButton = findViewById(R.id.floationActionButton);

        FragmentManager fragmentManager = getSupportFragmentManager();
        taskListFragment = (TaskListFragment) fragmentManager.findFragmentByTag(TaskListFragment.TAG);

        if (taskListFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            taskListFragment = TaskListFragment.newInstance();
            fragmentTransaction.add(R.id.frame_content, taskListFragment, TaskListFragment.TAG);
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
     * Cierra el último fragment añadido al BackStack
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
