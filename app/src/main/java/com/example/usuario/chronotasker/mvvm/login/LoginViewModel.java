package com.example.usuario.chronotasker.mvvm.login;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.R;
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
        validateLoginFields();
        return true;
    }
    public boolean onClickSignup() {
        mNavigator.openSignup();
        return true;
    }
    public boolean onClickNewUser() {
        validateSignupFields();
        return true;
    }


    private void loginUser() {
        User user = findUserLogin();
        if(user != null)
            onUserFound(user);
        else
            mNavigator.onUserNotFound();
    }
    private void signupUser() {
        User user = findUserExists();
        if(user == null) {
            addNewUser(new User(0, getName(), getEmail(), getPassword(), R.drawable.profile));
            onUserFound(findUserLogin());
        } else
            mNavigator.onUserNotFound();
    }

    private void addNewUser(User user) {
        UserRepository.getInstance().insertUser(user);
    }


    private User findUserLogin() {
        return UserRepository.getInstance().findUserLogin(getName(), getPassword());
    }
    private User findUserExists() {
        return UserRepository.getInstance().findUserExists(getName(), getEmail());
    }


    private void onUserFound(User user) {
        PreferencesHelper helper = App.getApp().getPreferencesHelper();
        helper.setCurrentUserId(user.getId());
        helper.setCurrentUserName(user.getName());
        helper.setCurrentUserPassword(user.getPassword());
        helper.setCurrentUserRemember(checked.getValue());
        mNavigator.onUserFound();
    }


    //Validacion
    private void validateLoginFields() {
        if (areLoginFieldsEmpty())                          mNavigator.errorEmptyField();
        else if (isInvalidFieldLength(getName()))           mNavigator.errorNameLengthInvalid();
        else if (isInvalidFieldLength(getPassword()))       mNavigator.errorPasswordLengthInvalid();
        else if (isInvalidPasswordFormat())                 mNavigator.errorPasswordFormatInvalid();
        else
            loginUser();
    }
    private void validateSignupFields() {
        if(areSignupFieldsEmpty())                          mNavigator.errorEmptyField();
        else if (isInvalidFieldLength(getEmail()))          mNavigator.errorEmailLengthInvalid();
        else if (isInvalidFieldLength(getName()))           mNavigator.errorNameLengthInvalid();
        else if (isInvalidFieldLength(getPassword()))       mNavigator.errorPasswordLengthInvalid();
        else if (isInvalidPasswordFormat())                 mNavigator.errorPasswordFormatInvalid();
        else if (isInvalidEmailFormat())                    mNavigator.errorEmailFormatInvalid();
        else
            signupUser();
    }


    private boolean areLoginFieldsEmpty() {
        return isNameFieldEmpty() || isPasswordFieldEmpty();
    }
    private boolean areSignupFieldsEmpty() {
        return isEmailFieldEmpty() || areLoginFieldsEmpty();
    }


    private boolean isNameFieldEmpty() {
        return getName() == null || getName().isEmpty();
    }
    private boolean isPasswordFieldEmpty() {
        return getPassword() == null || getPassword().isEmpty();
    }
    private boolean isEmailFieldEmpty() {
        return getEmail() == null || getEmail().isEmpty();
    }


    private boolean isInvalidFieldLength(String text) {
        return !Common.isValidFieldLength(text);
    }
    private boolean isInvalidPasswordFormat() {
        return !Common.isValidPasswordFormat(getPassword());
    }
    private boolean isInvalidEmailFormat() {
        return !Common.isValidEmailFormat(getEmail());
    }


    @Override
    public void reset() {

    }
}
