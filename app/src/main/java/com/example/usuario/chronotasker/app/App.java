package com.example.usuario.chronotasker.app;

import android.app.Application;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.app.prefs.PreferencesHelper;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.db.dao.TaskDao;
import com.example.usuario.chronotasker.data.db.dao.UserDao;

import org.joda.time.DateTime;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.android.BuildConfig;

/**
 * Clase que hereda de Application y que mantiene el contexto
 * común a toda la aplicación y que permite el uso de preferencias
 *
 * @author Enrique Casielles Lapeira
 * @version 2.0
 * @see Application
 * @see PreferencesHelper
 * @see BoxStore
 */
public class App extends Application {
    private static App sApp;
    public static final String PREF_NAME = "preferences";
    private PreferencesHelper mPreferencesHelper;
    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mPreferencesHelper = PreferencesHelper.getInstance();
        mBoxStore = null;//MyObjectBox.builder().androidContext(this).build();

        //Create mock database
        initDatabase();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(mBoxStore).start(this);
        }
    }

    public static App getApp() {
        return sApp;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }

    //Mock data for testing
    public static void initDatabase() {
        int mTaskCount = 6;
        int mUserCount = 1;
        int mIcon = R.drawable.ic_action_help;

        TaskDao taskDao = TaskDao.getInstance();
        UserDao userDao = UserDao.getInstance();

        if(taskDao.getCount() != mTaskCount) taskDao.deleteAllTasks();
        if(userDao.getCount() != mUserCount) userDao.deleteAllUsers();

        if(taskDao.getCount() == 0) {
            User defaultUser = new User(0, "Enrique", "enrique@gmail.com", "Pwd123");

            for (int i = 0; i < mTaskCount; i++) {
                defaultUser.getTasks().add(new Task(
                        i, "No perder el bus: " + i, mIcon,
                        new DateTime(), new DateTime(), (int)(1 + Math.random() * 31),
                        "Salida a las 8:05", null,
                        null, null, null
                ));
            }

            UserDao.getInstance().insertUser(defaultUser);
        }

    }
}
