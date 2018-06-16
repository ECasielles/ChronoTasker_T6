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


    public List<Task> getTaskList() {
        List<Task> tasks = TaskRepository.getInstance().findCurrentUserTaskList();
        displayedArray.setValue(new SparseBooleanArray(tasks.size()));
        return tasks;
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
        displayedArray.getValue().put(position, !Objects.requireNonNull(displayedArray.getValue()).get(position));
        mNavigator.adapterNotifyChange(position);
        return true;
    }


    //VISIBILITY
    public boolean isDisplayed(int position) {
        if (displayedArray != null)
            return Objects.requireNonNull(displayedArray.getValue()).valueAt(position);
        else return false;
    }


    //TASK REMOVAL
    public void removeState(int position) {
        tempDisplayArray = displayedArray.getValue();
        int count = Objects.requireNonNull(tempDisplayArray).size();
        if(position <= count) {
            for (int i = position; i < count; i++) {
                if (i == position)
                    Objects.requireNonNull(displayedArray.getValue()).delete(position);
                else {
                    boolean value = Objects.requireNonNull(displayedArray.getValue()).get(i);
                    Objects.requireNonNull(displayedArray.getValue()).put(i - 1, value);
                    Objects.requireNonNull(displayedArray.getValue()).delete(i);
                }
            }
        }
    }
    public void restoreState() {
        displayedArray.setValue(tempDisplayArray);
        tempDisplayArray = null;
    }
    public void deleteTask(Task task) {
        TaskRepository.getInstance().deleteTask(task);
        mNavigator.onDeleteTaskInfo(task.getTitle());
        tempDisplayArray = null;
    }

}
