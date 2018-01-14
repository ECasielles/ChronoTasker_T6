package com.example.usuario.chronotasker.ui.task.interactor;


import org.joda.time.DateTime;

public interface TaskCreationInteractor {

    void addTask(String title, String description, boolean isInformal, boolean isDefault, boolean isImportant, boolean isUrgent, DateTime startDate);

    interface OnTaskCreatedListener {
        void onTaskCreated();
    }

}
