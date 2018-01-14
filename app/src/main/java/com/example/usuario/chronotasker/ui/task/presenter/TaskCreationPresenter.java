package com.example.usuario.chronotasker.ui.task.presenter;


import com.example.usuario.chronotasker.ui.task.contract.TaskCreationContract;
import com.example.usuario.chronotasker.ui.task.interactor.TaskCreationInteractor;
import com.example.usuario.chronotasker.ui.task.interactor.TaskCreationInteractorImpl;

import org.joda.time.DateTime;

public class TaskCreationPresenter implements TaskCreationContract.Presenter, TaskCreationInteractor.OnTaskCreatedListener {

    TaskCreationContract.View view;
    TaskCreationInteractor interactor;

    public TaskCreationPresenter(TaskCreationContract.View view) {
        this.view = view;
        this.interactor = new TaskCreationInteractorImpl(this);
    }

    @Override
    public void addTask(String title, String description, boolean isInformal, boolean isDefault, boolean isImportant, boolean isUrgent, DateTime startDate) {
        interactor.addTask(title, description, isInformal, isDefault, isImportant, isUrgent, startDate);
    }

    @Override
    public void onTaskCreated() {
        view.reloadTaskList();
    }
}
