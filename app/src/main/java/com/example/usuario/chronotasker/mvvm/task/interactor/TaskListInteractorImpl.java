package com.example.usuario.chronotasker.mvvm.task.interactor;

import com.example.usuario.chronotasker.data.repository.TaskRepository;
import com.example.usuario.chronotasker.data.model.Task;

/**
 * Interactor de TaskListPresenter.
 */
public class TaskListInteractorImpl implements TaskListInteractor {
    private TaskListInteractor.OnImportFinishedListener listener;

    public TaskListInteractorImpl(TaskListInteractor.OnImportFinishedListener listener) {
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
        TaskRepository.getInstance().deleteTask(task, this);
    }

    @Override
    public void onSuccess(String title) {
        listener.onTaskDeleted(title);
    }

    @Override
    public void onError(Throwable throwable) {
        listener.onTaskDeleteError(throwable);
    }

}
