package com.example.usuario.chronotasker.data.db.repository;

import android.database.Cursor;

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
    public static UserDao userDao;

    static {
        userRepository = new UserRepository();
    }

    private ArrayList<User> users;

    private UserRepository() {
        this.users = new ArrayList<>();
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
        users.clear();
        Cursor cursor = getUserCursor();
        //El cursor siempre se coloca antes del primer elemento.
        if (cursor.moveToFirst())
            do {
                //Acceder a las columnas en el mismo orden
                User user = new User(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                users.add(user);
            } while (cursor.moveToNext());
        return users;
    }

    private Cursor getUserCursor() {
        return userDao.loadAll();
    }

    public boolean addUser(String name, String email, String password) {
        boolean result = true;
        if (users.size() == 0)
            getUsers();
        for (User user : users) {
            if (user.getName().equals(name) || user.getEmail().equals(email)) {
                result = false;
                break;
            }
        }
        if (result)
            users.add(new User(name, email, password));
        return result;
    }

    /**
     * TODO: Hacer directamente la sentencia SQL que busca el usuario
     * Extrae los usuarios de la BD y busca entre los usuarios si existe
     *
     * @param name
     * @param password
     * @return
     */
    public boolean exists(String name, String password) {
        boolean result = false;
        if (users.size() == 0)
            getUsers();
        for (User user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
