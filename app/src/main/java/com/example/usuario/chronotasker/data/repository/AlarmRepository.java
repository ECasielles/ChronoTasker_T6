package com.example.usuario.chronotasker.data.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.data.model.Alarm;
import com.example.usuario.chronotasker.data.db.dao.AlarmDao;

import java.util.Collection;
import java.util.List;

import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;


/**
 * Contiene los elementos de la lista de alarmas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Alarm
 */
public class AlarmRepository {

    private static AlarmRepository instance;
    private static AlarmDao alarmDao = AlarmDao.getInstance();

    public static AlarmRepository getInstance() {
        if (instance == null)
            instance = new AlarmRepository();
        return instance;
    }

    public DataSubscription subscribeToAlarmList(DataObserver<List<Alarm>> observer) {
        return alarmDao.subscribeToAlarmList(observer);
    }

    public DataSubscription subscribeToAlarm(DataObserver<Alarm> observer, long id, boolean singleUpdate) {
        return alarmDao.subscribeToAlarm(observer, id, singleUpdate);
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

    public ObjectBoxLiveData<Alarm> getAlarmsLiveData() {
        return alarmDao.getAllAlarmsById();
    }

}
