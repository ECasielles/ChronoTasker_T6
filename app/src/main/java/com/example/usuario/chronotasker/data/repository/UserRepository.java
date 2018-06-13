package com.example.usuario.chronotasker.data.repository;

import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.data.db.dao.UserDao;
import com.example.usuario.chronotasker.data.model.User;


/**
 * Devuelve los datos de usuario desde el origen de datos.
 *
 * @author Enrique Casielles Lapeira
 * @version 2.0
 * @see User
 */
public class UserRepository {

    private static UserRepository instance;

    private UserDao userDao;

    private UserRepository() {
        userDao = UserDao.getInstance();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User findUser(String name, String password) {
        return userDao.find(name, password);
    }

    public User findFirstUser() {
        return userDao.findFirst();
    }

    public User findCurrentUser() {
        long userId = App.getApp().getPreferencesHelper().getCurrentUserId();
        return userDao.findById(userId);
    }

}
