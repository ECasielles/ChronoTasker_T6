package com.example.usuario.chronotasker.data.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.db.dao.UserDao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;


/**
 * Devuelve los datos de usuario desde el origen de datos.
 *
 * @author Enrique Casielles Lapeira
 * @version 2.0
 * @see User
 */
public class UserRepository {

    private static UserRepository instance;
    private static UserDao userDao = UserDao.getInstance();

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    public DataSubscription subscribeToUserList(DataObserver<List<User>> observer) {
        return userDao.subscribeToUserList(observer);
    }

    public DataSubscription subscribeToUser(DataObserver<User> observer, long id, boolean singleUpdate) {
        return userDao.subscribeToUser(observer, id, singleUpdate);
    }
    public void addUserCollection(Collection<User> users) {
        userDao.insertUsers(users);
    }

    public void updateUser(User user, MutableLiveData<User> liveResponse) {
        if (user != null){
            liveResponse.postValue(user);
            userDao.insertUser(user);
        }
    }

    public ObjectBoxLiveData<User> getUsersLiveData() {
        return userDao.getAllUsersById();
    }

    public boolean findUser(User user) {
        List<User> users = Objects.requireNonNull(userDao.getAllUsersById().getValue());
        Iterator<User> iterator = users.iterator();
        boolean found = false;

        while (iterator.hasNext() && !found)
            if(iterator.next().equals(user))
                found = true;

        return found;
    }
}
