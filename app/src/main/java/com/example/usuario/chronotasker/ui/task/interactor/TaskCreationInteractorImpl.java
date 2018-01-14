package com.example.usuario.chronotasker.ui.task.interactor;


import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.repository.TaskRepository;

import org.joda.time.DateTime;

public class TaskCreationInteractorImpl implements TaskCreationInteractor {

    private final OnTaskCreatedListener listener;

    public TaskCreationInteractorImpl(TaskCreationInteractor.OnTaskCreatedListener listener) {
        this.listener = listener;
    }

    @Override
    public void addTask(String title, String description, boolean isInformal, boolean isDefault, boolean isImportant, boolean isUrgent, DateTime startDate) {
        //TODO: Migrate to Category as a method
        int flag = 0;
        if (isInformal)
            flag += 1;
        if (isDefault)
            flag += 2;
        if (isImportant)
            flag += 4;
        if (isUrgent)
            flag += 8;
        //TODO: Implement the rest of the features
        TaskRepository.getInstance().addTask(title, -1, startDate, null, new Category(flag), description, null, -1, null, null);
        listener.onTaskCreated();
    }
}
