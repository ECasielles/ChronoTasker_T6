package com.example.usuario.chronotasker.ui.task.contract;


import com.example.usuario.chronotasker.data.db.model.Task;

public interface TaskCreationContract {

    interface View {
        void setPresenter(TaskCreationContract.Presenter presenter);
        void reloadTaskList();
    }

    interface Presenter {
        void addTask(Task task);

        void updateTask(Task task);

        void onDestroy();
    }
}
