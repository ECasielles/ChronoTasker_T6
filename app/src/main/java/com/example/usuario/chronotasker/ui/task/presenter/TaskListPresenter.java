package com.example.usuario.chronotasker.ui.task.presenter;

import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.task.contract.TaskListContract;
import com.example.usuario.chronotasker.ui.task.interactor.TaskListInteractor;

import java.util.ArrayList;

/**
 * Presenter de TaskListFragment.
 */
public class TaskListPresenter implements TaskListContract.Presenter, TaskListInteractor.OnImportFinishedListener {

    private final TaskListContract.View view;
    private TaskListInteractor interactor;

    public TaskListPresenter(TaskListContract.View view) {
        this.view = view;
    }

    @Override
    public void importTasks() {
        interactor.importTasksFromRepository();
    }

    @Override
    public void onTasksImported(ArrayList<Task> tasks) {
        view.loadTasks(tasks);
    }
}
