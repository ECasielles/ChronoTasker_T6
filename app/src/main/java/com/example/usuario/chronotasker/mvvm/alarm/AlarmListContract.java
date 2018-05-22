package com.example.usuario.chronotasker.mvvm.alarm;

import com.example.usuario.chronotasker.data.model.Alarm;

import java.util.ArrayList;

/**
 * Created by icenri on 2/26/18.
 */

public interface AlarmListContract {
    interface View {
        void onAlarmUpdated(String title);

        void onDatabaseError(String message);

        void onAlarmsLoaded(ArrayList<Alarm> alarms);
    }

    interface Presenter {
        void loadAlarms();

        void editAlarm(Alarm alarm);

        void addAlarm(Alarm alarm);
    }

}
