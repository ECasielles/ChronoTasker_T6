package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.data.model.Task;

import java.util.Collection;
import java.util.List;

import io.objectbox.Box;

/**
 * Clase que maneja los cursores que recorren la tabla Task de la BD
 * según la consulta indicada por cada función
 *
 * @version 2.0
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

    public Box<Task> getTaskBox() {
        return App.getApp().getBoxStore().boxFor(Task.class);
    }

/*
    public DataSubscription subscribeToTaskList(DataObserver<List<Task>> observer) {
        Query<Task> query = getTaskBox().query().notEqual(Task_.priority, Category.ARCHIVED_NAME).build();
        SubscriptionBuilder<List<Task>> builder = query.subscribe();
        builder.observer(observer);
        return query.subscribe().on(AndroidScheduler.mainThread()).observer(observer);
    }

    public DataSubscription subscribeToTask(DataObserver<Task> observer, long id, boolean singleUpdate) {
        Query<Task> query = getTaskBox().query().equal(Task_.id, id).build();
        SubscriptionBuilder<Task> builder = query.subscribe().transform(list -> {
            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        }).on(AndroidScheduler.mainThread());

        if (singleUpdate) {
            builder.single();
        }
        return builder.observer(observer);
    }
*/

    public void insertTask(Task task) {
        getTaskBox().put(task);
    }

    public void insertTasks(Collection<Task> tasks) {
        getTaskBox().put(tasks);
    }

    public void deleteTask(Task task) {
        getTaskBox().remove(task);
    }

    public void deleteAllTasks() {
        getTaskBox().removeAll();
    }

    public long getCount() {
        return getTaskBox().count();
    }

    /*public ObjectBoxLiveData<Task> getAllTasksById() {
        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        return new ObjectBoxLiveData<>(getTaskBox().query().order(Task_.id).build());
    }

    public ObjectBoxLiveData<Task> getAllTasksByIdFromUserId(User user) {
        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        return new ObjectBoxLiveData<>(getTaskBox().query().equal(Task_.ownerId, user.getId()).build());
    }*/

    @Override
    public Task findFirst() {
        return null;
    }

    @Override
    public Task findById(long id) {
        return null;
    }

    /*
    public List<Task> find(User user) {
        return getBox().query().equal(Task_.owner, user).build().findFirst();
    }
    */

    public List<Task> findCurrentUserTaskList() {
        return UserDao.getInstance().findCurrentUser().tasks;
    }

}
