package com.example.usuario.chronotasker.data;

import android.app.Application;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.dao.TaskDao;
import com.example.usuario.chronotasker.data.db.dao.UserDao;
import com.example.usuario.chronotasker.data.model.MyObjectBox;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.prefs.PreferencesHelper;

import org.joda.time.DateTime;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

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

    public static final String PREF_NAME = "preferences";
    private static App sApp;
    private PreferencesHelper mPreferencesHelper;
    private BoxStore mBoxStore;

    //SINGLETON
    public static App getApp() {
        return sApp;
    }


    //CICLO DE VIDA
    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        buildDatabase();
        emptyDatabase();
        initDatabase();
        new AndroidObjectBrowser(mBoxStore).start(this);
    }

    //GETTERS
    public BoxStore getBoxStore() {
        return mBoxStore;
    }
    public PreferencesHelper getPreferencesHelper() {
        if (mPreferencesHelper == null) {
            mPreferencesHelper = PreferencesHelper.getInstance();
        }
        return mPreferencesHelper;
    }


    //BASE DE DATOS
    private void buildDatabase() {
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
    }
    private void emptyDatabase() {
        if (mBoxStore != null) {
            if (!mBoxStore.isClosed())
                mBoxStore.close();
            mBoxStore.deleteAllFiles();
            buildDatabase();
        }
    }
    private void initDatabase() {
        int mIcon = R.drawable.ic_check_box;

        if(UserDao.getInstance().getBox().count() == 0) {
            User user = new User(0, "Usuario", "example@test.com", "Abc123", R.drawable.profile);

            UserDao.getInstance().insert(user);

            user.tasks.add(new Task("No perder el bus: " + 0, mIcon,
                    new DateTime(), new DateTime(), (int) (1 + Math.random() * 31),
                    "Salida a las 8:05", null,
                    null, null, null));
            user.tasks.applyChangesToDb();
            user.tasks.add(new Task("No perder las llaves: " + 1, mIcon,
                            new DateTime(), new DateTime(), (int) (1 + Math.random() * 31),
                            "Salida a las 8:05", null,
                            null, null, null));
            user.tasks.applyChangesToDb();
            user.tasks.add(new Task("No perder el juicio: " + 2, mIcon,
                            new DateTime(), new DateTime(), (int) (1 + Math.random() * 31),
                            "Salida a las 8:05", null,
                            null, null, null));
            user.tasks.applyChangesToDb();

            TaskDao.getInstance().getBox().getAll();
        }
    }

}
