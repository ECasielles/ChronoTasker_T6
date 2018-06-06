package com.example.usuario.chronotasker.mvvm.home;

import android.databinding.ObservableField;

import com.example.usuario.chronotasker.mvvm.base.BaseViewModel;

public class HomeViewModel extends BaseViewModel {

    public ObservableField<Integer> image = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> email = new ObservableField<>();

}
