package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.data.App;

import java.util.Collection;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;

public abstract class BaseDao<T> {

    private final Class<T> type;

    public BaseDao(Class<T> type) {
        this.type = type;
    }

    public Class<T> getMyType() {
        return this.type;
    }

    public Box<T> getBox() {
        return App.getApp().getBoxStore().boxFor(getMyType());
    }

    public DataSubscription subscribeToList(DataObserver<List<T>> observer) {
        return getBox().query().build().subscribe().on(AndroidScheduler.mainThread()).observer(observer);
    }

    public void insert(T item) {
        getBox().put(item);
    }

    public void insertCollection(Collection<T> items) {
        getBox().put(items);
    }

    public void delete(T item) {
        getBox().remove(item);
    }

    public void deleteAll() {
        getBox().removeAll();
    }

    public long count() {
        return getBox().count();
    }

    public abstract T findFirst();

    public abstract T findById(long id);

}
