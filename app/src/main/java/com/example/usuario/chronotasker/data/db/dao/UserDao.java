package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.model.User_;

import io.objectbox.query.Query;

/**
 * Clase que maneja las llamadas a BD
 * según la consulta indicada por cada función
 *
 * @version 2.0
 * @see BaseDao
 * @see User
 */
public class UserDao extends BaseDao<User> {

    private static UserDao sInstance;

    public UserDao(Class<User> type) {
        super(type);
    }

    public static UserDao getInstance() {
        if(sInstance == null)
            sInstance = new UserDao(User.class);
        return sInstance;
    }

    public long count() {
        return getBox().count();
    }

    public User find(String name, String password) {
        Query query = getBox().query()
                .equal(User_.name, name)
                .and()
                .equal(User_.password, password)
                .build();
        return (User) query.findFirst();
    }

    //HEREDADOS DE BASEDAO
    @Override
    public User findFirst() {
        return getBox().query().equal(User_.id, 1L).build().findFirst();
    }
    @Override
    public User findById(long id) {
        return getBox().query().equal(User_.id, id).build().findFirst();
    }
    public User findCurrentUser() {
        return UserDao.getInstance().findById(App.getApp().getPreferencesHelper().getCurrentUserId());
    }

}
