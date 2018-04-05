package com.example.usuario.chronotasker.ui.task.interactor;

import com.example.usuario.chronotasker.data.model.Task;


public interface TaskViewInteractor {
    void addTask(Task task);
    void updateTask(Task task);

    interface OnTaskEditionListener {
        void onTaskCreated();

        void onTaskUpdated(String title);

        void onError(Throwable throwable);
    }

}
