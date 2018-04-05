package com.example.usuario.chronotasker.ui.task.interactor;


import com.example.usuario.chronotasker.data.db.repository.TaskRepository;
import com.example.usuario.chronotasker.data.db.repository.TaskRepositoryCallback;
import com.example.usuario.chronotasker.data.model.Task;

public class TaskViewInteractorImpl implements TaskViewInteractor, TaskRepositoryCallback {

    private final OnTaskEditionListener listener;

    public TaskViewInteractorImpl(OnTaskEditionListener listener) {
        this.listener = listener;
    }

    @Override
    public void addTask(Task task) {
        TaskRepository.getInstance().addTask(task);
        listener.onTaskCreated();
    }

    @Override
    public void updateTask(Task task) {
        TaskRepository.getInstance().updateTask(task, this);
    }

    @Override
    public void onSuccess(String title) {
        listener.onTaskUpdated(title);
    }

    @Override
    public void onError(Throwable throwable) {
        listener.onError(throwable);
    }

}
