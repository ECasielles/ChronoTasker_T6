package com.example.usuario.chronotasker.mvvm.task.item;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;

import org.joda.time.DateTime;

public interface TaskItemNavigator extends INavigator {

    void onTaskSaved();

    DateTime onStartDateClick();
    DateTime onEndDateClick();

    void onDateError();
    void onEmptyFieldError();
}
