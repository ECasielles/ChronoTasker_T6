package com.example.usuario.chronotasker.data.db.repository;

import com.example.usuario.chronotasker.App;
import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.dao.AlarmDao;
import com.example.usuario.chronotasker.data.model.Alarm;

import java.util.List;

/**
 * Created by icenri on 2/26/18.
 */

public class AlarmRepository {
    private static AlarmRepository alarmRepository;

    static {
        alarmRepository = new AlarmRepository();
    }

    private AlarmDao alarmDao;

    private AlarmRepository() {
        this.alarmDao = new AlarmDao();
    }

    public static AlarmRepository getInstance() {
        if (alarmRepository == null)
            alarmRepository = new AlarmRepository();
        return alarmRepository;
    }

    public List<Alarm> loadAll() {
        return alarmDao.loadAll();
    }

    public void save(Alarm alarm, AlarmRepositoryCallback callback) {
        if (alarmDao.save(alarm) > 0)
            callback.onSuccess(alarm.getTitle());
        else
            callback.onError(new Throwable(
                    App.getApp().getResources()
                            .getString(R.string.error_database_alarm_save)
            ));
    }

    public void update(Alarm alarm, AlarmRepositoryCallback callback) {
        if (alarmDao.update(alarm) > 0)
            callback.onSuccess(alarm.getTitle());
        else
            callback.onError(new Throwable(
                    App.getApp().getResources()
                            .getString(R.string.error_database_alarm_update)
            ));
    }

    public void delete(Alarm alarm, AlarmRepositoryCallback callback) {
        if (alarmDao.delete(alarm) > 0)
            callback.onSuccess(alarm.getTitle());
        else
            callback.onError(new Throwable(
                    App.getApp().getResources()
                            .getString(R.string.error_database_alarm_delete)
            ));
    }

}
