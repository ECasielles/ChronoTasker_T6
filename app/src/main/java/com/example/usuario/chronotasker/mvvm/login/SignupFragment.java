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
import com.example.usuario.chronotasker.databinding.FragmentSignupBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;


public class SignupFragment extends BaseFragment<FragmentSignupBinding, LoginViewModel> implements LoginNavigator {

    //CONSTANTS
    public static String TAG = SignupFragment.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);

        mBinding.setViewModel(mViewModel);

        mBinding.executePendingBindings();

        return mBinding.getRoot();
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


    @Override
    public void openSignup() { }

    @Override
    public void errorEmptyField() {
        Snackbar.make(mParent, getResources().getString(R.string.error_name_empty), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void errorNameLengthInvalid() {
        Snackbar.make(mParent, getResources().getString(R.string.error_name_length_invalid), Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void errorEmailLengthInvalid() {
        Snackbar.make(mParent, getResources().getString(R.string.error_email_length_invalid), Snackbar.LENGTH_SHORT).show();
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
        Snackbar.make(mParent, getResources().getString(R.string.error_email_format_invalid), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotFound() {
        Snackbar.make(mParent, getResources().getString(R.string.error_user_not_found), Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onUserFound() {
        ((LoginActivity) mFragmentEventHandler).navigateToHome();
    }



}
