package com.example.usuario.chronotasker.mvvm.task.list;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;

public interface TaskListNavigator extends INavigator {

    void openTask(Task task);
    void adapterNotifyChange(int position);
    void onDeleteTaskInfo(String title);

}
