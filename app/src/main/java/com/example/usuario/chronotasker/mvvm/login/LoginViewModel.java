package com.example.usuario.chronotasker.mvvm.login;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.data.App;
import com.example.usuario.chronotasker.data.prefs.PreferencesHelper;
import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.repository.UserRepository;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;
import com.example.usuario.chronotasker.utils.Common;


public class LoginViewModel extends NavigatorViewModel {

    //CONSTANTS
    public static String TAG = LoginViewModel.class.getSimpleName();

    public final MutableLiveData<String> name = new MutableLiveData<>();
    public final MutableLiveData<String> email = new MutableLiveData<>();
    public final MutableLiveData<String> password = new MutableLiveData<>();
    public final MutableLiveData<Boolean> checked = new MutableLiveData<>();

    private LoginNavigator mNavigator;

    public LoginViewModel() {
        checked.setValue(Boolean.FALSE);

        User testUser = UserRepository.getInstance().findFirstUser();
        if(testUser != null) {
            name.setValue(testUser.getName());
            password.setValue(testUser.getPassword());
            //TODO: FIX
            email.setValue(testUser.getEmail());
        }
    }

    public String getName() {
        return name.getValue();
    }
    public String getPassword() {
        return password.getValue();
    }
    public String getEmail() {
        return email.getValue();
    }
    public boolean getChecked() {
        return checked.getValue();
    }
    public void setName(String name) {
        this.name.setValue(name);
    }
    public void setPassword(String password) {
        this.password.setValue(password);
    }
    public void setEmail(String email) {
        this.email.setValue(email);
    }
    public void setChecked(boolean checked) {
        this.checked.setValue(checked);
    }

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (LoginNavigator) navigator;
    }

    //OnClick methods must return true because of reasons.
    //See this: https://n8ebel.github.io/2017-07-27-bug-busting-databinding-onLongClick/
    public boolean onClickLogin() {
        validateFields(name.getValue(), password.getValue());
        return true;
    }
    public boolean onClickSignup() {
        //Load fragment from View
        //TODO
        return true;
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
        if (name != null && password != null)
            return name.isEmpty() || password.isEmpty();
        else
            return true;

    }

    private boolean isInvalidFieldLength(String name) {
        return !Common.isValidFieldLength(name);
    }

    private boolean isInvalidPasswordFormat(String password) {
        return !Common.isValidPasswordFormat(password);
    }



}
