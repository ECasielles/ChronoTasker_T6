package com.example.usuario.chronotasker.mvvm.task.contract;


import com.example.usuario.chronotasker.data.model.Task;

import java.util.ArrayList;

/**
 * Contrato entre TaskListFragment y TaskListPresenter.
 */
public interface TaskListContract {

    interface View {
        void loadTasks(ArrayList<Task> tasks);
        void onTaskDeleteEvent(int position, Task task);
        void onTaskRestored(int position, Task task);

        void onDeleteTaskInfo(String title);

        void onDatabaseError(String message);
    }

    interface Presenter {
        //Modos de importación según opciones de menú
        void importTasks();
        void importTasksSortedByPriority();
        void importTasksSortedByStartDate();
        void importTasksSortedByUrgency();
        void importTasksSortedByImportance();

        void deleteTaskEvent(int position, Task task);
        void restoreTask(int position, Task task);
        void deleteTask(Task task);

        void onDestroy();
    }

}
