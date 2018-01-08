package com.example.usuario.chronotasker.ui.task.interactor;


public class TaskCreationInteractorImpl implements TaskCreationInteractor {

    private final OnTaskCreatedListener listener;

    public TaskCreationInteractorImpl(TaskCreationInteractor.OnTaskCreatedListener listener) {
        this.listener = listener;
    }


}
