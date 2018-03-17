package com.example.usuario.chronotasker.data.db.repository;

import com.example.usuario.chronotasker.App;
import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.dao.UserDao;
import com.example.usuario.chronotasker.data.db.model.User;

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

    public void addUser(User user, UserRepositoryCallback callback) {
        if (userDao.save(user) > 0)
            callback.onSuccess();
        else
            callback.onError(new Throwable(App.getApp().getResources().getString(R.string.error_database_user_save)));
    }

    public void search(User user, UserRepositoryCallback callback) {
        int id = userDao.search(user);
        if (id != -1) {
            App.getApp().getmPreferencesHelper().setCurrentUserId(id);
            callback.onSuccess();
        } else
            callback.onError(new Throwable(App.getApp().getResources().getString(R.string.error_database_search)));
    }

}
