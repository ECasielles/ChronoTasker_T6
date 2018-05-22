package com.example.usuario.chronotasker.mvvm.base;

import android.os.Bundle;

public interface IActivity {

    int initView(Bundle savedInstanceState);

    void initData(Bundle savedInstanceState);

    boolean usesFragment();
}
