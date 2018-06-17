package com.example.usuario.chronotasker.mvvm.calendar;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;

import org.joda.time.DateTime;

public interface CalendarNavigator extends INavigator {

    void onStartDateSelected(DateTime startDate);
    void onEndDateSelected(DateTime endDate);
    void onDateRangeSelected(DateTime startDate, DateTime endDate);

}
