package com.example.usuario.chronotasker.ui.task.contract;


import org.joda.time.DateTime;

public interface TaskCreationContract {

    interface View {
        void setPresenter(TaskCreationContract.Presenter presenter);

        void reloadTaskList();
    }

    interface Presenter {
        void addTask(String title, String description, boolean isInformal, boolean isDefault, boolean isImportant, boolean isUrgent, DateTime startDate);
    }
}
