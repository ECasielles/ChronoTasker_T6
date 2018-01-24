package com.example.usuario.chronotasker.data.db.repository;

import com.example.usuario.chronotasker.data.db.dao.UserDao;
import com.example.usuario.chronotasker.data.db.model.User;

import java.util.ArrayList;

/**
 * Almacena los datos de usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see User
 */
public class UserRepository {

    public static UserRepository userRepository;

    static {
        userRepository = new UserRepository();
    }

    public UserDao userDao;

    private UserRepository() {
        userDao = new UserDao();
    }

    public static UserRepository getInstance(){
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    /**
     * Devolviendo un ArrayList.
     */
    public ArrayList<User> getUsers() {
        return userDao.loadAll();
    }

    public long addUser(String name, String email, String password) {
        return userDao.save(name, email, password);
    }

    public boolean exists(String name, String password) {
        return userDao.exists(name, password);
    }

    public void deleteUser(String name) {
        userDao.delete(name);
    }

}
