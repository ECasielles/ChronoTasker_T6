package com.example.usuario.chronotasker.mvvm.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IFragment {

    View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void initData(Bundle savedInstanceState);

    void setData(Object data);

}
