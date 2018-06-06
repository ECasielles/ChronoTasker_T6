package com.example.usuario.chronotasker.mvvm.base.navigator;

import com.example.usuario.chronotasker.mvvm.base.BaseViewModel;

public abstract class NavigatorViewModel extends BaseViewModel implements INavigatorViewModel {

    protected INavigator mNavigator;

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = navigator;
    }

}