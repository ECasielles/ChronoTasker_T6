package com.example.usuario.chronotasker.mvvm.alarm;

import com.example.usuario.chronotasker.data.model.Alarm;
import com.example.usuario.chronotasker.data.repository.callback.AlarmRepositoryCallback;

import java.util.ArrayList;


public interface AlarmListInteractor extends AlarmRepositoryCallback {
    void loadAlarms();

    void addAlarm(Alarm alarm);

    void editAlarm(Alarm alarm);

    interface OnAlarmViewListener {
        void onAlarmsLoaded(ArrayList<Alarm> alarms);

        void onSuccess(String title);

        void onError(Throwable throwable);
    }

}
