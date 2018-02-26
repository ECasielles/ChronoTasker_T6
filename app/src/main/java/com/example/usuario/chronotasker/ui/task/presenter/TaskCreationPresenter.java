package com.example.usuario.chronotasker.ui.task.presenter;


import com.example.usuario.chronotasker.ui.task.contract.TaskCreationContract;
import com.example.usuario.chronotasker.ui.task.interactor.TaskViewInteractor;
import com.example.usuario.chronotasker.ui.task.interactor.TaskViewInteractorImpl;

import org.joda.time.DateTime;

public class TaskCreationPresenter implements TaskCreationContract.Presenter, TaskViewInteractor.OnTaskCreatedListener {
    private TaskCreationContract.View view;
    private TaskViewInteractor interactor;

    public TaskCreationPresenter(TaskCreationContract.View view) {
        this.view = view;
        this.interactor = new TaskViewInteractorImpl(this);
    }

    @Override
    public void addTask(String title, String description, boolean isInformal, boolean isDefault, boolean isImportant, boolean isUrgent, DateTime startDate) {
        interactor.addTask(title, description, isInformal, isDefault, isImportant, isUrgent, startDate);
    }

    @Override
    public void onTaskCreated() {
        view.reloadTaskList();
    }

    /**
     * Se eliminan las referencias del presentador
     */
    @Override
    public void onDestroy() {
        view = null;
        interactor = null;
    }

}
