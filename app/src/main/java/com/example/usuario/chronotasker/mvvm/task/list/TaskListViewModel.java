package com.example.usuario.chronotasker.mvvm.task.list;

import android.os.Bundle;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.repository.TaskRepository;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

import java.util.List;


public class TaskListViewModel extends NavigatorViewModel {

    //CONSTANTS
    public static String TAG = TaskListViewModel.class.getSimpleName();


    private TaskListNavigator mNavigator;


    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (TaskListNavigator) navigator;
    }

    public void newTask() {
        mNavigator.openTask(null);
    }
    public void openTask(Bundle bundle) {
        mNavigator.openTask(bundle);
    }

    public List<Task> getTaskList() {
        return TaskRepository.getInstance().findCurrentUserTaskList();
    }
}
