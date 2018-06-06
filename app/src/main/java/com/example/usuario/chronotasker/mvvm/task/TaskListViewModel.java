package com.example.usuario.chronotasker.mvvm.task;

import android.databinding.ObservableField;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

import java.util.List;

public class TaskListViewModel extends NavigatorViewModel {

    //CONSTANTS
    public static String TAG = TaskListViewModel.class.getSimpleName();

    public ObservableField<Integer> selectedIndex = new ObservableField<>();
    public ObservableField<List<Task>> taskList = new ObservableField<>();

    private TaskListNavigator mNavigator;

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (TaskListNavigator) navigator;
    }

}
