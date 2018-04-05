package com.example.usuario.chronotasker.ui.alarm;

import com.example.usuario.chronotasker.data.db.repository.AlarmRepository;
import com.example.usuario.chronotasker.data.model.Alarm;

/**
 * Created by icenri on 2/26/18.
 */

public class AlarmListInteractorImpl implements AlarmListInteractor {
    private OnAlarmViewListener listener;

    public AlarmListInteractorImpl(AlarmListInteractor.OnAlarmViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadAlarms() {
        listener.onAlarmsLoaded(AlarmRepository.getInstance().loadAll());
    }

    @Override
    public void addAlarm(Alarm alarm) {
        AlarmRepository.getInstance().save(alarm, this);
    }

    @Override
    public void editAlarm(Alarm alarm) {
        AlarmRepository.getInstance().update(alarm, this);
    }

    @Override
    public void onSuccess(String title) {
        listener.onSuccess(title);
    }

    @Override
    public void onError(Throwable throwable) {
        listener.onError(throwable);
    }

}
