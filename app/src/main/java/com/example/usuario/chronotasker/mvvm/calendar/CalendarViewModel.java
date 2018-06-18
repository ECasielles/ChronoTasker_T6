package com.example.usuario.chronotasker.mvvm.calendar;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

import org.joda.time.DateTime;

import java.util.Date;

public class CalendarViewModel extends NavigatorViewModel {

    //CONSTANTS
    public static String TAG = CalendarViewModel.class.getSimpleName();

    public final MutableLiveData<DateTime> startDate = new MutableLiveData<>();
    public final MutableLiveData<DateTime> endDate = new MutableLiveData<>();

    private CalendarNavigator mNavigator;


    public CalendarViewModel() {
        startDate.setValue(new DateTime());
        endDate.setValue(new DateTime());
    }

    public Date getStartDate() {
        return startDate.getValue().toDate();
    }
    public void setStartDate(Date date) {
        startDate.setValue(new DateTime(date));
    }


    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (CalendarNavigator) navigator;
    }


    @Override
    public void reset() {

    }
}
