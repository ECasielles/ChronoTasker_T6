package com.example.usuario.chronotasker.mvvm.login;

import com.example.usuario.chronotasker.mvvm.base.INavigator;

public interface LoginNavigator extends INavigator {

    void errorEmptyField();

    void errorNameLengthInvalid();

    void errorPasswordLengthInvalid();

    void errorPasswordFormatInvalid();

    void onUserFound();

    void onUserNotFound();

}
