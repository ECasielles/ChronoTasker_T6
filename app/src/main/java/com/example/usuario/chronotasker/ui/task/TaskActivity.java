package com.example.usuario.chronotasker.ui.task;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

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

}
