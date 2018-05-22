package com.example.usuario.chronotasker.mvvm.login;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.app.prefs.PreferencesHelper;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.repository.UserRepository;
import com.example.usuario.chronotasker.mvvm.base.BaseViewModel;
import com.example.usuario.chronotasker.mvvm.base.INavigator;
import com.example.usuario.chronotasker.mvvm.base.IViewModel;
import com.example.usuario.chronotasker.utils.Common;

public class LoginViewModel extends BaseViewModel implements IViewModel {

    //CONSTANTS
    public static String TAG = LoginFragment.class.getSimpleName();

    @Bindable
    public ObservableField<String> name = new ObservableField<>();
    @Bindable
    public ObservableField<String> password = new ObservableField<>();
    @Bindable
    public ObservableBoolean checked = new ObservableBoolean(false);

    private LoginNavigator mNavigator;

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (LoginNavigator) navigator;
    }

    public void onClick() {
        validateFields(name.get(), password.get());
    }

    public void validateFields(String name, String password) {
        if (areFieldsEmpty(name, password))             mNavigator.errorEmptyField();
        else if (isInvalidFieldLength(name))            mNavigator.errorNameLengthInvalid();
        else if (isInvalidFieldLength(password))        mNavigator.errorPasswordLengthInvalid();
        else if (isInvalidPasswordFormat(password))     mNavigator.errorPasswordFormatInvalid();
        else                                            loginUser(name, password);
    }

    private void loginUser(String name, String password) {
        if(UserRepository.getInstance().findUser(new User(name, password)))
            onUserFound();
        else
            mNavigator.onUserNotFound();
    }

    private void onUserFound() {
        PreferencesHelper helper = App.getApp().getPreferencesHelper();
        helper.setCurrentUserName(name.get());
        helper.setCurrentUserPassword(password.get());
        helper.setCurrentUserRemember(checked.get());
        mNavigator.onUserFound();
    }

    private boolean areFieldsEmpty(String name, String password) {
        return name.isEmpty() || password.isEmpty();
    }

    private boolean isInvalidFieldLength(String name) {
        return !Common.isValidFieldLength(name);
    }

    private boolean isInvalidPasswordFormat(String password) {
        return !Common.isValidPasswordFormat(password);
    }

}
