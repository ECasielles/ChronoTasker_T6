package com.example.usuario.chronotasker.data.db.dao;

import com.example.usuario.chronotasker.App;
import com.example.usuario.chronotasker.data.model.Alarm;

import java.util.Collection;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;


public class AlarmDao {

    private static Box<Alarm> getAlarmBox() {
        return App.getApp().getBoxStore().boxFor(Alarm.class);
    }

    public static DataSubscription subscribeToAlarmList(DataObserver<List<Alarm>> observer) {
        return getAlarmBox().query().build().subscribe().on(AndroidScheduler.mainThread()).observer(observer);
    }

    public static DataSubscription subscribeToAlarm(DataObserver<Alarm> observer, long id, boolean singleUpdate) {
        //TODO: FIX!!!
        /*SubscriptionBuilder<Alarm> builder = getAlarmBox().query().eager(Alarm_.).equal(Alarm_.id, id).build().subscribe().transform(list -> {
            if (list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        }).on(AndroidScheduler.mainThread());

        if (singleUpdate) {
            builder.single();
        }
        return builder.observer(observer);*/
        return null;
    }

    public static void insertAlarm(Alarm alarm) {
        getAlarmBox().put(alarm);
    }

    public static void insertAlarms(Collection<Alarm> alarms) {
        getAlarmBox().put(alarms);
    }

    public static void deleteAlarm(Alarm alarm) {
        getAlarmBox().remove(alarm);
    }

    public List<Alarm> loadAll() {
        BoxStore boxStore = App.getApp().getBoxStore();
        Box<Alarm> alarmBox = boxStore.boxFor(Alarm.class);
        return alarmBox.getAll();
    }

}
