package com.example.usuario.chronotasker.ui.task.interactor;

import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.data.db.repository.TaskRepository;

/**
 * Interactor de TaskListPresenter.
 */
public class TaskListsInteractorImpl implements TaskListInteractor {
    TaskListInteractor.OnImportFinishedListener listener;

    public TaskListsInteractorImpl(TaskListInteractor.OnImportFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    public void importTasksSortedById() {
        listener.onTasksImported(TaskRepository.getInstance().getTasksOrderById());
    }

    @Override
    public void importTasksSortedByPriority() {
        listener.onTasksImported(TaskRepository.getInstance().getTasksOrderByPriority());
    }

    @Override
    public void importTasksSortedByStartDate() {
        listener.onTasksImported(TaskRepository.getInstance().getTasksOrderByStartDate());
    }

    @Override
    public void importTasksSortedByUrgency() {
        listener.onTasksImported(TaskRepository.getInstance().getTasksOrderByUrgency());
    }

    @Override
    public void importTasksSortedByImportance() {
        listener.onTasksImported(TaskRepository.getInstance().getTasksOrderByImportance());
    }

    @Override
    public void deleteTask(Task task) {
        TaskRepository.getInstance().deleteTask(task.getId());
        listener.onTaskDeletedFromRepository(task.getTitle());
    }

}
