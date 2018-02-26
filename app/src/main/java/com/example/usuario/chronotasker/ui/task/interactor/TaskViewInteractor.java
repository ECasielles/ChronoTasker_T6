package com.example.usuario.chronotasker.ui.task.interactor;


import com.example.usuario.chronotasker.data.db.model.Task;

public interface TaskViewInteractor {

    void addTask(Task task);

    void updateTask(Task task);

    interface OnTaskCreatedListener {
        void onTaskCreated();
    }

}
