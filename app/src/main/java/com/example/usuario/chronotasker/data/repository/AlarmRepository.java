package com.example.usuario.chronotasker.data.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.data.db.dao.AlarmDao;
import com.example.usuario.chronotasker.data.model.Alarm;

import java.util.Collection;
import java.util.List;


/**
 * Contiene los elementos de la lista de alarmas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Alarm
 */
public class AlarmRepository {

    private static AlarmRepository instance;
    private AlarmDao alarmDao;

    public AlarmRepository() {
        alarmDao = AlarmDao.getInstance();
    }

    public static AlarmRepository getInstance() {
        if (instance == null)
            instance = new AlarmRepository();
        return instance;
    }

    public List<Alarm> getAlarmsLiveData() {
        return alarmDao.getAllAlarmsOrderById();
    }

    public Alarm findAlarmById(long id) {
        return alarmDao.findById(id);
    }

    public void addAlarmCollection(Collection<Alarm> alarms) {
        alarmDao.insertAlarms(alarms);
    }

    public void updateAlarm(Alarm alarm, MutableLiveData<Alarm> liveResponse) {
        if (alarm != null){
            liveResponse.postValue(alarm);
            alarmDao.insertAlarm(alarm);
        }
    }


}
