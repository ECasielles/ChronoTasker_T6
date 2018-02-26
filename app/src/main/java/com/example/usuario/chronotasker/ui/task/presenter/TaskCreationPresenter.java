package com.example.usuario.chronotasker.ui.task.presenter;


import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.task.contract.TaskViewContract;
import com.example.usuario.chronotasker.ui.task.interactor.TaskViewInteractor;
import com.example.usuario.chronotasker.ui.task.interactor.TaskViewInteractorImpl;

public class TaskCreationPresenter implements TaskViewContract.Presenter, TaskViewInteractor.OnTaskCreatedListener {
    private TaskViewContract.View view;
    private TaskViewInteractor interactor;

    public TaskCreationPresenter(TaskViewContract.View view) {
        this.view = view;
        this.interactor = new TaskViewInteractorImpl(this);
    }

    @Override
    public void addTask(Task task) {
        interactor.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
        interactor.updateTask(task);
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
