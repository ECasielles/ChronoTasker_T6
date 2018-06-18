package com.example.usuario.chronotasker.mvvm.login;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;

public interface LoginNavigator extends INavigator {

    void openSignup();

    void errorEmptyField();

    void errorNameLengthInvalid();

    void errorEmailLengthInvalid();

    void errorPasswordLengthInvalid();

    void errorPasswordFormatInvalid();

    void onUserFound();

    void onUserNotFound();

    void errorEmailFormatInvalid();

}
