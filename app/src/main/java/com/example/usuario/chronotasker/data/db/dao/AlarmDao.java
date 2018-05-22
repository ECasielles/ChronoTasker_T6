package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.data.model.Alarm;
import com.example.usuario.chronotasker.data.model.Alarm_;

import java.util.Collection;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;
import io.objectbox.reactive.SubscriptionBuilder;

public class AlarmDao {
    
    private static AlarmDao sInstance;

    public static AlarmDao getInstance() {
        if(sInstance != null)
            sInstance = new AlarmDao();
        return sInstance;
    }

    public Box<Alarm> getAlarmBox() {
        return App.getApp().getBoxStore().boxFor(Alarm.class);
    }

    public DataSubscription subscribeToAlarmList(DataObserver<List<Alarm>> observer) {
        return getAlarmBox().query().build().subscribe().on(AndroidScheduler.mainThread()).observer(observer);
    }

    public DataSubscription subscribeToAlarm(DataObserver<Alarm> observer, long id, boolean singleUpdate) {
        SubscriptionBuilder<Alarm> builder = getAlarmBox().query().equal(Alarm_.id, id).build().subscribe().transform(list -> {
            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        }).on(AndroidScheduler.mainThread());

        if (singleUpdate) {
            builder.single();
        }
        return builder.observer(observer);
    }

    public void insertAlarm(Alarm alarm) {
        getAlarmBox().put(alarm);
    }

    public void insertAlarms(Collection<Alarm> alarms) {
        getAlarmBox().put(alarms);
    }

    public void deleteAlarm(Alarm alarm) {
        getAlarmBox().remove(alarm);
    }

    public void deleteAllAlarms() {
        getAlarmBox().removeAll();
    }

    public long getCount() {
        return getAlarmBox().count();
    }

    public ObjectBoxLiveData<Alarm> getAllAlarmsById() {
        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        return new ObjectBoxLiveData<>(getAlarmBox().query().order(Alarm_.id).build());
    }
    
}
