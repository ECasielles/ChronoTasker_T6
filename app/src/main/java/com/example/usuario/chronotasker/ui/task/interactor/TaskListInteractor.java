package com.example.usuario.chronotasker.ui.task.interactor;

/**
 * Interfaz de TaskListInteractorImpl. Contiene el contrato con el Presenter para que lo llame.
 */
public interface TaskListInteractor {

    void importTasksFromRepository();

    interface OnImportFinishedListener {

    }

}
