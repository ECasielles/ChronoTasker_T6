package com.example.usuario.chronotasker.mvvm.alarm;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

public class AlarmViewModel extends NavigatorViewModel {

    private AlarmNavigator mNavigator;

    public AlarmViewModel() {

    }

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (AlarmNavigator) navigator;
    }

}
