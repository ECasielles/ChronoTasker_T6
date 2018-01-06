package com.example.usuario.chronotasker.ui.task.interactor;

import com.example.usuario.chronotasker.data.db.repository.TaskRepository;
import com.example.usuario.chronotasker.ui.task.contract.TaskListContract;

/**
 * Interactor de TaskListPresenter.
 */
public class TaskListsInteractorImpl implements TaskListInteractor {
    TaskListContract.Presenter listener;

    public TaskListsInteractorImpl(TaskListContract.Presenter listener) {
        this.listener = listener;
    }

    @Override
    public void importTasksFromRepository() {
        listener.onTasksImported(TaskRepository.getInstance());
    }
}
