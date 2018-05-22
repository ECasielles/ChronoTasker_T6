package com.example.usuario.chronotasker.mvvm.task.presenter;


import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.mvvm.task.contract.TaskViewContract;
import com.example.usuario.chronotasker.mvvm.task.interactor.TaskViewInteractor;
import com.example.usuario.chronotasker.mvvm.task.interactor.TaskViewInteractorImpl;

public class TaskViewPresenter implements TaskViewContract.Presenter, TaskViewInteractor.OnTaskEditionListener {
    private TaskViewContract.View view;
    private TaskViewInteractor interactor;

    public TaskViewPresenter(TaskViewContract.View view) {
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

    @Override
    public void onTaskUpdated(String title) {
        view.taskUpdatedInfo(title);
        view.reloadTaskList();
    }

    @Override
    public void onError(Throwable throwable) {
        view.onDatabaseError(throwable.getMessage());
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
