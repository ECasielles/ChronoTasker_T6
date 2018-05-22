package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.model.User_;

import java.util.Collection;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.reactive.SubscriptionBuilder;

public class UserDao {

    private static UserDao sInstance;

    public static UserDao getInstance() {
        if(sInstance != null)
            sInstance = new UserDao();
        return sInstance;
    }

    public Box<User> getUserBox() {
        return App.getApp().getBoxStore().boxFor(User.class);
    }

    public DataSubscription subscribeToUserList(DataObserver<List<User>> observer) {
        return getUserBox().query().build().subscribe().on(AndroidScheduler.mainThread()).observer(observer);
    }

    public DataSubscription subscribeToUser(DataObserver<User> observer, long id, boolean singleUpdate) {
        SubscriptionBuilder<User> builder = getUserBox().query().equal(User_.id, id).build().subscribe().transform(list -> {
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

    public void insertUser(User user) {
        getUserBox().put(user);
    }

    public void insertUsers(Collection<User> users) {
        getUserBox().put(users);
    }

    public void deleteUser(User user) {
        getUserBox().remove(user);
    }

    public void deleteAllUsers() {
        getUserBox().removeAll();
    }

    public long getCount() {
        return getUserBox().count();
    }

    public ObjectBoxLiveData<User> getAllUsersById() {
        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        return new ObjectBoxLiveData<>(getUserBox().query().order(User_.id).build());
    }
}
