package com.example.usuario.chronotasker.ui.alarm;

import com.example.usuario.chronotasker.data.db.model.Alarm;

import java.util.ArrayList;

/**
 * Created by icenri on 2/26/18.
 */

public class AlarmListPresenter implements AlarmListContract.Presenter, AlarmListInteractor.OnAlarmViewListener {

    private AlarmListInteractor interactor;
    private AlarmListContract.View view;

    public AlarmListPresenter(AlarmListContract.View view) {
        this.view = view;
        this.interactor = new AlarmListInteractorImpl(this);
    }

    @Override
    public void loadAlarms() {
        interactor.loadAlarms();
    }

    @Override
    public void editAlarm(Alarm alarm) {
        interactor.editAlarm(alarm);
    }

    @Override
    public void addAlarm(Alarm alarm) {
        interactor.addAlarm(alarm);
    }

    @Override
    public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        view.onAlarmsLoaded(alarms);
    }

    @Override
    public void onSuccess(String title) {
        view.onAlarmUpdated(title);
    }

    @Override
    public void onError(Throwable throwable) {
        view.onDatabaseError(throwable.getMessage());
    }

}
