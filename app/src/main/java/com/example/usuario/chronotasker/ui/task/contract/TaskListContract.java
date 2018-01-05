package com.example.usuario.chronotasker.ui.task.contract;


/**
 * Contrato entre TaskListFragment y su Presenter
 */
public interface TaskListContract {

    interface View {
        void setPresenter(TaskListContract.Presenter presenter);
    }

    interface Presenter {

    }

}
