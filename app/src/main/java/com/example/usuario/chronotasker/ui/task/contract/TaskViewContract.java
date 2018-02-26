package com.example.usuario.chronotasker.ui.task.contract;


import com.example.usuario.chronotasker.data.db.model.Task;

public interface TaskViewContract {

    interface View {
        void reloadTaskList();
    }

    interface Presenter {
        void addTask(Task task);

        void updateTask(Task task);

        void onDestroy();
    }
}
