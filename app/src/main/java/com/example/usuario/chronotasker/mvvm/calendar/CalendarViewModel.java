package com.example.usuario.chronotasker.mvvm.calendar;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

public class CalendarViewModel extends NavigatorViewModel {


    private CalendarNavigator mNavigator;

    public CalendarViewModel() {
    }

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (CalendarNavigator) navigator;
    }

}
