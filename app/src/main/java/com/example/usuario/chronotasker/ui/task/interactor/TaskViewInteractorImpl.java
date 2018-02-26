package com.example.usuario.chronotasker.ui.task.interactor;


import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.data.db.repository.TaskRepository;
import com.example.usuario.chronotasker.data.db.repository.TaskRepositoryCallback;

public class TaskViewInteractorImpl implements TaskViewInteractor, TaskRepositoryCallback {

    private final OnTaskCreatedListener listener;

    public TaskViewInteractorImpl(TaskViewInteractor.OnTaskCreatedListener listener) {
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

    }

    @Override
    public void onError(Throwable throwable) {

    }
}