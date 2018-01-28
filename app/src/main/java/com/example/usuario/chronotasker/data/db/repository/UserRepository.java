package com.example.usuario.chronotasker.data.db.repository;

import com.example.usuario.chronotasker.data.db.dao.UserDao;
import com.example.usuario.chronotasker.data.db.model.User;

import java.util.ArrayList;

/**
 * Devuelve los datos de usuario desde el origen de datos.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see User
 */
public class UserRepository {

    private static UserRepository userRepository;

    static {
        userRepository = new UserRepository();
    }

    private UserDao userDao;

    private UserRepository() {
        userDao = new UserDao();
    }

    public static UserRepository getInstance(){
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    public ArrayList<User> getUsers() {
        return userDao.loadAll();
    }

    public void addUser(String name, String email, String password) {
        userDao.save(name, email, password);
    }

    //TODO: Handle error cases
    public boolean exists(String name, String password) {
        return userDao.exists(name, password) > 0;
    }

    public void deleteUser(int id) {
        userDao.delete(id);
    }

}
