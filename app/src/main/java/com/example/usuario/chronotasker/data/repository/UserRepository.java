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

    public User findUserLogin(String name, String password) {
        return userDao.findUserLogin(name, password);
    }
    public User findUserExists(String name, String email) {
        return userDao.findUserExists(name, email);
    }

    public User findFirstUser() {
        return userDao.findFirst();
    }

    public User findCurrentUser() {
        return userDao.findById(App.getApp().getPreferencesHelper().getCurrentUserId());
    }

    public void insertUser(User user) {
        userDao.insert(user);
    }


}
