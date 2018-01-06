package com.example.usuario.chronotasker.ui.task.contract;


import com.example.usuario.chronotasker.data.db.model.Task;

import java.util.ArrayList;

/**
 * Contrato entre TaskListFragment y TaskListPresenter.
 */
public interface TaskListContract {

    interface View {
        void setPresenter(TaskListContract.Presenter presenter);

        void loadTasks(ArrayList<Task> tasks);
    }

    interface Presenter {
        void importTasks();

        void onTasksImported(ArrayList<Task> tasks);
    }

}
