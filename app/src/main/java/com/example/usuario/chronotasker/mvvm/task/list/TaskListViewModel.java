package com.example.usuario.chronotasker.mvvm.task.list;

import android.arch.lifecycle.MutableLiveData;
import android.util.SparseBooleanArray;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.repository.TaskRepository;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

import java.util.List;
import java.util.Objects;


public class TaskListViewModel extends NavigatorViewModel {

    //CONSTANTS
    public static String TAG = TaskListViewModel.class.getSimpleName();

    private MutableLiveData<SparseBooleanArray> displayedArray = new MutableLiveData<>();

    private SparseBooleanArray tempDisplayArray;
    private TaskListNavigator mNavigator;


    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (TaskListNavigator) navigator;
    }

    public TaskListViewModel() {
        displayedArray.setValue(new SparseBooleanArray());
    }

    public List<Task> getTaskList() {
        return TaskRepository.getInstance().findCurrentUserTaskList();
    }


    //ONCLICK CALLBACK METHODS
    //OnClick methods must return true because of reasons.
    //See this: https://n8ebel.github.io/2017-07-27-bug-busting-databinding-onLongClick/
    public boolean newTask() {
        mNavigator.openTask(null);
        return true;
    }
    public boolean openTaskView(Task task) {
        mNavigator.openTask(task);
        return true;
    }
    public boolean changeDisplay(int position) {
        boolean value = displayedArray.getValue().get(position);
        displayedArray.getValue().put(position, !value);
        mNavigator.adapterNotifyItemChanged(position);
        return true;
    }


    //VISIBILITY
    public boolean isDisplayed(int position) {
        return displayedArray.getValue().valueAt(position);
    }


    //TASK REMOVAL
    public void removeState(int position) {
        tempDisplayArray = Objects.requireNonNull(displayedArray.getValue()).clone();

        SparseBooleanArray localArray = displayedArray.getValue();

        int start = localArray.indexOfKey(position);
        localArray.delete(position);
        int count = localArray.size();

        for (int i = start; i < count; i++) {
            int key = localArray.keyAt(i);
            boolean value = localArray.valueAt(i);
            localArray.delete(key);
            localArray.put(key - 1, value);
        }

        displayedArray.setValue(localArray);
        mNavigator.adapterNotifyItemRemoved(position);
    }
    public void restoreState() {
        displayedArray.setValue(tempDisplayArray);
        tempDisplayArray = null;
        mNavigator.adapterNotifySetChanged();
    }
    public void deleteTask(Task task) {
        TaskRepository.getInstance().deleteTask(task);
        mNavigator.onDeleteTaskInfo(task.getTitle());
        tempDisplayArray = null;
    }


    @Override
    public void reset() {

    }

}
