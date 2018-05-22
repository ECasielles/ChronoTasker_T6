package com.example.usuario.chronotasker.mvvm.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;

import static android.support.v4.util.Preconditions.checkNotNull;

public class LoginFragment extends BaseFragment implements LoginNavigator {

    //CONSTANTS
    public static String TAG = LoginFragment.class.getSimpleName();

    private ViewGroup parent;

    public static LoginFragment newInstance() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Guarda vista para Snackbar
        parent = (ViewGroup) view.getParent();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.login_title);

        mViewModel.setNavigator(this);
    }

    @SuppressLint("RestrictedApi")
    public void setViewModel(@NonNull LoginViewModel viewModel) {
        mViewModel = checkNotNull(viewModel);
    }

    @Override
    public void errorEmptyField() {
        Snackbar.make(parent, getResources().getString(R.string.error_name_empty), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void errorNameLengthInvalid() {
        Snackbar.make(parent, getResources().getString(R.string.error_name_length_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void errorPasswordLengthInvalid() {
        Snackbar.make(parent, getResources().getString(R.string.error_password_length_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void errorPasswordFormatInvalid() {
        Snackbar.make(parent, getResources().getString(R.string.error_password_format_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotFound() {
        Snackbar.make(parent, getResources().getString(R.string.error_user_not_found), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onUserFound() {
        ((LoginActivity)fragmentEventHandler).navigateToHome();
    }

    @Override
    public boolean onBackPressed() {
        fragmentEventHandler.setSelectedFragment(null);
        return false;
    }
}
