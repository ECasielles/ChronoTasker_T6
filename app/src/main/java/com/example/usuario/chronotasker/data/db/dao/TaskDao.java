package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.model.Task_;

import java.util.Collection;
import java.util.List;

import io.objectbox.query.Query;

/**
 * Clase que maneja las llamadas a BD
 * según la consulta indicada por cada función
 *
 * @version 2.0
 * @see BaseDao
 * @see Task
 */
public class TaskDao extends BaseDao<Task> {

    private static TaskDao sInstance;

    public TaskDao(Class<Task> type) {
        super(type);
    }

    public static TaskDao getInstance() {
        if(sInstance == null)
            sInstance = new TaskDao(Task.class);
        return sInstance;
    }

    public void insertTask(Task task) {
        getBox().put(task);
    }
    public void insertTasks(Collection<Task> tasks) {
        getBox().put(tasks);
    }
    public void deleteTask(Task task) {
        getBox().remove(task);
    }
    public void deleteAllTasks() {
        getBox().removeAll();
    }
    public long getCount() {
        return getBox().count();
    }

    @Override
    public Task findFirst() {
        return null;
    }
    @Override
    public Task findById(long id) {
        return null;
    }

    //TODO: Find if fix is needed
    public List<Task> findCurrentUserTaskList() {
        //return UserDao.getInstance().findCurrentUser().tasks;
        Query<Task> query = getBox().query().equal(
                Task_.userId,
                App.getApp().getPreferencesHelper().getCurrentUserId()
        ).build();
        return query.find();
    }
    public List<Task> getAllTasksOrderById() {
        Query<Task> query = getBox().query().order(Task_.id).build();
        return query.find();
    }

}
