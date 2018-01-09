package com.example.usuario.chronotasker.data.db.repository;

import android.support.annotation.Nullable;

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

    private static ArrayList<User> users;
    public static UserRepository userRepository;

    static {
        userRepository = new UserRepository();
    }

    private UserRepository() {
        this.users = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        users.add(new User(0, "", "", ""));
        users.add(new User(1, "user", "password", "email@email.com"));
        users.add(new User(2, "Enrique", "Pwd123", "enrique@gmail.com"));
        users.add(new User(3, "Lourdes", "Pwd123", "lourdes@gmail.com"));
    }

    public static UserRepository getInstance(){
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public static @Nullable
    User getUser(String name) {
        User user = null;
        for (User temp : users) {
            if (temp.getName().equals(name)) {
                user = temp;
                break;
            }
        }
        return user;
    }

    public static boolean addUser(String name, String email, String password) {
        boolean isNewUser = true;
        for (User temp : users) {
            if (temp.getName().equals(name) || temp.getEmail().equals(email)) {
                isNewUser = false;
                break;
            }
        }
        if (isNewUser)
            users.add(new User(users.size(), name, email, password));
        return isNewUser;
    }

    public static int getUserId(String name) {
        int id = -1;
        for (User temp : users) {
            if (temp.getName().equals(name)) {
                id = users.indexOf(temp);
                break;
            }
        }
        return id;
    }

}
