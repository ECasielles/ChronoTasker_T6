package com.example.usuario.chronotasker.mvvm.login;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

public class SignupViewModel extends NavigatorViewModel {
    private static final String TAG = SignupViewModel.class.getSimpleName();
    private SignupNavigator mNavigator;

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (SignupNavigator) navigator;
    }
}
