package com.example.usuario.chronotasker.data.repository;

import com.example.usuario.chronotasker.data.db.dao.TaskDao;
import com.example.usuario.chronotasker.data.model.Task;

import java.util.List;


/**
 * Contiene los elementos de la lista de tareas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Task
 */
public class TaskRepository {

    private static TaskRepository instance;
    private static TaskDao taskDao = TaskDao.getInstance();

    public static TaskRepository getInstance() {
        if (instance == null)
            instance = new TaskRepository();
        return instance;
    }

    /*
    public DataSubscription subscribeToTaskList(DataObserver<List<Task>> observer) {
        return taskDao.subscribeToTaskList(observer);
    }

    public DataSubscription subscribeToTask(DataObserver<Task> observer, long id, boolean singleUpdate) {
        return taskDao.subscribeToTask(observer, id, singleUpdate);
    }

    public void addTaskCollection(Collection<Task> tasks) {
        taskDao.insertTasks(tasks);
    }

    public void updateTask(Task task, MutableLiveData<Task> liveResponse) {
        if (task != null){
            liveResponse.postValue(task);
            taskDao.insertTask(task);
        }
    }

    public ObjectBoxLiveData<Task> getTasksLiveData() {
        return taskDao.getAllTasksById();
    }
    */


    public List<Task> findCurrentUserTaskList() {
        return taskDao.findCurrentUserTaskList();
    }

    /*
    public User findFirstUser() {
        return taskDao.findFirst();
    }

    public User findCurrentUser() {
        long userId = App.getApp().getPreferencesHelper().getCurrentUserId();
        return taskDao.findById(userId);
    }
    */

}
