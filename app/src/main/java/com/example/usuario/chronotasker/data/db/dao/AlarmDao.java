package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.data.model.Alarm;
import com.example.usuario.chronotasker.data.model.Alarm_;

import java.util.Collection;
import java.util.List;

import io.objectbox.query.Query;

/**
 * Clase que maneja las llamadas a BD
 * según la consulta indicada por cada función
 *
 * @version 2.0
 * @see BaseDao
 * @see Alarm
 */
public class AlarmDao extends BaseDao<Alarm> {
    
    private static AlarmDao sInstance;

    public AlarmDao(Class<Alarm> type) {
        super(type);
    }

    public static AlarmDao getInstance() {
        if(sInstance == null)
            sInstance = new AlarmDao(Alarm.class);
        return sInstance;
    }

    public void insertAlarm(Alarm alarm) {
        getBox().put(alarm);
    }
    public void insertAlarms(Collection<Alarm> alarms) {
        getBox().put(alarms);
    }
    public void deleteAlarm(Alarm alarm) {
        getBox().remove(alarm);
    }
    public void deleteAllAlarms() {
        getBox().removeAll();
    }
    public long getCount() {
        return getBox().count();
    }

    //TODO: Try return ObjectBoxLiveData<>(query) for liveData responses
    public List<Alarm> getAllAlarmsOrderById() {
        Query<Alarm> query = getBox().query().order(Alarm_.id).build();
        return query.find();
    }

    //HEREDADOS DE BASEDAO
    @Override
    public Alarm findFirst() {
        return getBox().query().equal(Alarm_.id, 1L).build().findFirst();
    }
    @Override
    public Alarm findById(long id) {
        return getBox().query().equal(Alarm_.id, id).build().findFirst();
    }

}
