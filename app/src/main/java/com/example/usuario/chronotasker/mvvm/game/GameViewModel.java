package com.example.usuario.chronotasker.mvvm.game;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

public class GameViewModel extends NavigatorViewModel {

    private GameNavigator mNavigator;

    public GameViewModel() {
    }

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (GameNavigator) navigator;
    }

}
