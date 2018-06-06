package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.model.User_;

import io.objectbox.Box;
import io.objectbox.query.Query;

/**
 * Singleton con acceso a la base de datos local de usuarios.
 */
public class UserDao extends BaseDao<User>{

    private static UserDao sInstance;

    public UserDao(Class<User> type) {
        super(type);
    }

    public static UserDao getInstance() {
        if(sInstance == null)
            sInstance = new UserDao(User.class);
        return sInstance;
    }

    public Box<User> getUserBox() {
        return App.getApp().getBoxStore().boxFor(User.class);
    }

    public void deleteAllUsers() {
        getUserBox().removeAll();
    }

    public long count() {
        return getUserBox().count();
    }

    public User find(String name, String password) {
        Query query = getBox().query()
                .equal(User_.name, name)
                .and()
                .equal(User_.password, password)
                .build();

        return (User) query.findFirst();
    }

}
