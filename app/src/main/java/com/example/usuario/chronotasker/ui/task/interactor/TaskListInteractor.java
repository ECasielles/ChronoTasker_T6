package com.example.usuario.chronotasker.ui.task.interactor;

import com.example.usuario.chronotasker.data.db.model.Task;

import java.util.ArrayList;

/**
 * Interfaz de TaskListInteractorImpl. Contiene el contrato con el Presenter para que lo llame.
 */
public interface TaskListInteractor {

    void importTasksSortedById();

    void importTasksSortedByPriority();

    void importTasksSortedByStartDate();

    void importTasksSortedByUrgency();

    void importTasksSortedByImportance();

    void deleteTask(Task task);

    interface OnImportFinishedListener {
        void onTasksImported(ArrayList<Task> tasks);

        void onTaskDeletedFromRepository(String title);
    }

}
