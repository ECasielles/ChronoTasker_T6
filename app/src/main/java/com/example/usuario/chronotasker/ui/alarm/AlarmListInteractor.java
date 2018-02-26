package com.example.usuario.chronotasker.ui.alarm;

import com.example.usuario.chronotasker.data.db.model.Alarm;
import com.example.usuario.chronotasker.data.db.repository.AlarmRepositoryCallback;

import java.util.ArrayList;

/**
 * Created by icenri on 2/26/18.
 */

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
