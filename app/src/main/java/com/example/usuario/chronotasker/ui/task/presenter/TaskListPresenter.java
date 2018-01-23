package com.example.usuario.chronotasker.ui.task.presenter;

import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.task.contract.TaskListContract;
import com.example.usuario.chronotasker.ui.task.interactor.TaskListInteractor;
import com.example.usuario.chronotasker.ui.task.interactor.TaskListsInteractorImpl;

import java.util.ArrayList;

/**
 * Presenter de TaskListFragment.
 */
public class TaskListPresenter implements TaskListContract.Presenter, TaskListInteractor.OnImportFinishedListener {
    private final TaskListContract.View view;
    private TaskListInteractor interactor;

    public TaskListPresenter(TaskListContract.View view) {
        this.view = view;
        this.interactor = new TaskListsInteractorImpl(this);
    }

    //Modos de importación de la lista de tareas
    @Override
    public void importTasks() {
        interactor.importTasksSortedById();
    }

    @Override
    public void importTasksSortedByPriority() {
        interactor.importTasksSortedByPriority();
    }

    @Override
    public void importTasksSortedByStartDate() {
        interactor.importTasksSortedByStartDate();
    }

    @Override
    public void importTasksSortedByUrgency() {
        interactor.importTasksSortedByUrgency();
    }

    @Override
    public void importTasksSortedByImportance() {
        interactor.importTasksSortedByImportance();
    }

    @Override
    public void deleteTaskEvent(int position, Task task) {
        view.onTaskDeleteEvent(position, task);
    }

    @Override
    public void restoreTask(int position, Task task) {
        view.onTaskRestored(position, task);
    }

    @Override
    public void deleteTask(Task task) {
        interactor.deleteTask(task);
    }

    @Override
    public void onTasksImported(ArrayList<Task> tasks) {
        view.loadTasks(tasks);
    }

    @Override
    public void onTaskDeletedFromRepository(String title) {
        view.onTaskDeletedFromRepository(title);
    }

}
