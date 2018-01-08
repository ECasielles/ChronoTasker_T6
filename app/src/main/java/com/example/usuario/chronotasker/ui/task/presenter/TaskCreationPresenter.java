package com.example.usuario.chronotasker.ui.task.presenter;


import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.task.contract.TaskCreationContract;
import com.example.usuario.chronotasker.ui.task.interactor.TaskCreationInteractor;
import com.example.usuario.chronotasker.ui.task.interactor.TaskCreationInteractorImpl;

public class TaskCreationPresenter implements TaskCreationContract.Presenter, TaskCreationInteractor.OnTaskCreatedListener {

    TaskCreationContract.View view;
    TaskCreationInteractor interactor;

    public TaskCreationPresenter(TaskCreationContract.View view) {
        this.view = view;
        this.interactor = new TaskCreationInteractorImpl(this);
    }

    @Override
    public void addTask(Task task) {

    }
}
