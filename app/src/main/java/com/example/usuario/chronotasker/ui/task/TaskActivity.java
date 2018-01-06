package com.example.usuario.chronotasker.ui.task;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.ui.task.fragment.TaskListFragment;
import com.example.usuario.chronotasker.ui.task.presenter.TaskListPresenter;

public class TaskActivity extends AppCompatActivity {

    TaskListFragment taskListFragment;
    TaskListPresenter taskListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        FragmentManager fragmentManager = getSupportFragmentManager();
        taskListFragment = (TaskListFragment) fragmentManager.findFragmentByTag(TaskListFragment.TAG);

        if (taskListFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            taskListFragment = TaskListFragment.newInstance();
            fragmentTransaction.replace(android.R.id.content, taskListFragment, TaskListFragment.TAG);
            fragmentTransaction.commit();
        }

        taskListPresenter = new TaskListPresenter(taskListFragment);
        taskListFragment.setPresenter(taskListPresenter);
    }

}
