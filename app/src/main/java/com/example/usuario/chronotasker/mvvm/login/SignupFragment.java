package com.example.usuario.chronotasker.mvvm.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;

public class SignupFragment extends BaseFragment {

    //CONSTANTS
    public static String TAG = SignupFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public SignupViewModel makeViewModel() {
        return new SignupViewModel();
    }
}
