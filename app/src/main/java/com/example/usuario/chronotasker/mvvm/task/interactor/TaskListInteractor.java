package com.example.usuario.chronotasker.mvvm.task.interactor;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.repository.callback.TaskRepositoryCallback;

import java.util.ArrayList;

/**
 * Interfaz de TaskListInteractorImpl. Contiene el contrato con el Presenter para que lo llame.
 */
public interface TaskListInteractor extends TaskRepositoryCallback {

    void importTasksSortedById();
    void importTasksSortedByPriority();
    void importTasksSortedByStartDate();
    void importTasksSortedByUrgency();
    void importTasksSortedByImportance();

    void deleteTask(Task task);

    interface OnImportFinishedListener {
        void onTasksImported(ArrayList<Task> tasks);
        void onTaskDeleted(String title);
        void onTaskDeleteError(Throwable throwable);
    }

}
