package com.example.usuario.chronotasker.mvvm.task.list;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;

public interface TaskListNavigator extends INavigator {

    void openTask(Task task);
    void onDeleteTaskInfo(String title);
    void adapterNotifyItemChanged(int position);
    void adapterNotifyRangeChanged(int startPosition);
    void adapterNotifySetChanged();
    void adapterNotifyItemRemoved(int position);

}
