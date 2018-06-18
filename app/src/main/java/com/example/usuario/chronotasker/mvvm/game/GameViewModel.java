package com.example.usuario.chronotasker.mvvm.game;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

public class GameViewModel extends NavigatorViewModel {

    public static final String TAG = GameViewModel.class.getSimpleName();

    private GameNavigator mNavigator;

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (GameNavigator) navigator;
    }


    @Override
    public void reset() {

    }
}
