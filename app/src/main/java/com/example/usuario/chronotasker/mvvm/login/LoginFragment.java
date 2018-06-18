package com.example.usuario.chronotasker.mvvm.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.databinding.FragmentLoginBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> implements LoginNavigator {

    //CONSTANTS
    public static String TAG = LoginFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        mBinding.setViewModel(mViewModel);

        mBinding.executePendingBindings();

        return mBinding.getRoot();
    }


    @Override
    public void errorEmptyField() {
        Snackbar.make(mParent, getResources().getString(R.string.error_name_empty), Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void errorNameLengthInvalid() {
        Snackbar.make(mParent, getResources().getString(R.string.error_name_length_invalid), Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void errorEmailLengthInvalid()  {

    }
    @Override
    public void errorPasswordLengthInvalid() {
        Snackbar.make(mParent, getResources().getString(R.string.error_password_length_invalid), Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void errorPasswordFormatInvalid() {
        Snackbar.make(mParent, getResources().getString(R.string.error_password_format_invalid), Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void errorEmailFormatInvalid() {

    }
    @Override
    public void onUserNotFound() {
        Snackbar.make(mParent, getResources().getString(R.string.error_user_not_found), Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onUserFound() {
        ((LoginActivity) mFragmentEventHandler).navigateToHome();
    }


    @Override
    public void openSignup() {
        mFragmentEventHandler.stackFragment(new SignupFragment());
    }


    @Override
    public LoginViewModel makeViewModel() {
        return new LoginViewModel();
    }


    @Override
    public String getFragmentTag() {
        return TAG;
    }


    @Override
    public String getViewModelTag() {
        return LoginViewModel.TAG;
    }

}
