package com.example.usuario.chronotasker.mvvm.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.databinding.FragmentLoginBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> implements LoginNavigator {

    //CONSTANTS
    public static String TAG = LoginFragment.class.getSimpleName();

    private ViewGroup parent;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        if(mBinding.hasPendingBindings())
            mBinding.executePendingBindings();

        //setRetainInstance(false);
        mViewModel.setNavigator(this);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Guarda vista para Snackbar
        parent = (ViewGroup) view.getParent();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.login_title);
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
