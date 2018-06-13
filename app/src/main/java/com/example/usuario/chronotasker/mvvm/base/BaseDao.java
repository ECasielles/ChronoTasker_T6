package com.example.usuario.chronotasker.mvvm.base;

import com.example.usuario.chronotasker.data.App;

import java.util.Collection;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;

public abstract class BaseDao<M extends BaseModel> {

    private Class<M> modelClass;

    public Box<M> getItemsBox() {
        return App.getApp().getBoxStore().boxFor(modelClass);
    }

    public DataSubscription subscribeToList(DataObserver<List<M>> observer) {
        return getItemsBox().query().build().subscribe().on(AndroidScheduler.mainThread()).observer(observer);
    }

/*
    //@SuppressWarnings("unchecked")
    public DataSubscription subscribeToItem(DataObserver<M> observer, long id, boolean singleUpdate) {
        SubscriptionBuilder<M> builder = getItemsBox().query().equals(M_.id, id).build().subscribe().transform(list -> {
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

    //@SuppressWarnings("unchecked")
    public ObjectBoxLiveData<M> getAllItemsById() {
        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        return new ObjectBoxLiveData<>(getItemsBox().query().build());
    }

    public void insertItem(M item) {
        getItemsBox().put(item);
    }

    public void insertItems(Collection<M> models) {
        getItemsBox().put(models);
    }

}
