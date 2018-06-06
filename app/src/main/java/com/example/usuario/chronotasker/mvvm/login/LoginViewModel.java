package com.example.usuario.chronotasker.mvvm.login;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.app.prefs.PreferencesHelper;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.repository.UserRepository;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;
import com.example.usuario.chronotasker.utils.Common;


public class LoginViewModel extends NavigatorViewModel {

    //CONSTANTS
    public static String TAG = LoginViewModel.class.getSimpleName();

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> checked = new MutableLiveData<>();

    private LoginNavigator mNavigator;

    public LoginViewModel() {
        checked.setValue(Boolean.FALSE);
        name.setValue("Usuario");
        password.setValue("Pwd123");
        email.setValue("example@test.com");
    }

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (LoginNavigator) navigator;
    }

    //TODO: Move these two to LoginFragment to use DataBinding???
    public void onClickLogin() {
        validateFields(name.getValue(), password.getValue());
    }
    public void onClickSignup() {
        //Load fragment from View
    }


    public void validateFields(String name, String password) {
        if (areFieldsEmpty(name, password))             mNavigator.errorEmptyField();
        else if (isInvalidFieldLength(name))            mNavigator.errorNameLengthInvalid();
        else if (isInvalidFieldLength(password))        mNavigator.errorPasswordLengthInvalid();
        else if (isInvalidPasswordFormat(password))     mNavigator.errorPasswordFormatInvalid();
        else                                            loginUser(name, password);
    }

    private void loginUser(String name, String password) {
        User user = UserRepository.getInstance().findUser(name, password);
        if(user != null)
            onUserFound(user);
        else
            mNavigator.onUserNotFound();
    }

    private void onUserFound(User user) {
        PreferencesHelper helper = App.getApp().getPreferencesHelper();
        helper.setCurrentUserId(user.getId());
        helper.setCurrentUserName(user.getName());
        helper.setCurrentUserPassword(user.getPassword());
        helper.setCurrentUserRemember(checked.getValue());
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
