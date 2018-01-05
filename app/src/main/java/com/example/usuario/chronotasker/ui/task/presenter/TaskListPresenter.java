package com.example.usuario.chronotasker.ui.task.presenter;

import com.example.usuario.chronotasker.ui.task.contract.TaskListContract;

/**
 * Presenter de TaskListFragment
 */
public class TaskListPresenter implements TaskListContract.Presenter {

    private final TaskListContract.View view;

    public TaskListPresenter(TaskListContract.View view) {
        this.view = view;
    }

}
