package com.example.usuario.chronotasker.ui.task.contract;


import com.example.usuario.chronotasker.data.model.Task;

public interface TaskViewContract {

    interface View {
        void reloadTaskList();

        void onDatabaseError(String message);

        void taskUpdatedInfo(String title);
    }

    interface Presenter {
        void addTask(Task task);
        void updateTask(Task task);
        void onDestroy();
    }
}
