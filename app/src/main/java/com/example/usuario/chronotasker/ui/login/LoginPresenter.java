package com.example.usuario.chronotasker.ui.login;

import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.utils.CommonUtils;

/**
 * Presenter de LoginActivity
 */
public class LoginPresenter implements LoginContract.Presenter, LoginInteractor.OnUserFoundListener {

    private final LoginInteractor interactor;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void loginUser(User user) {
        interactor.findUser(user);
    }

    @Override
    public void validateFields(String name, String password) {
        if (onNameEmptyError(name) || onPasswordEmptyError(password))
            view.errorEmptyField();
        else if (!onNameLengthValidError(name))
            view.errorNameLengthInvalid();
        else if (!onPasswordLengthValidError(password))
            view.errorPasswordLengthInvalid();
        else if (!onPasswordFormatValidError(password))
            view.errorPasswordFormatInvalid();
        else
            view.loginUser();
    }

    @Override
    public boolean onNameEmptyError(String name) {
        return name.isEmpty();
    }

    @Override
    public boolean onPasswordEmptyError(String password) {
        return password.isEmpty();
    }

    @Override
    public boolean onNameLengthValidError(String name) {
        return CommonUtils.isValidFieldLength(name);
    }

    @Override
    public boolean onPasswordLengthValidError(String password) {
        return CommonUtils.isValidFieldLength(password);
    }

    @Override
    public boolean onPasswordFormatValidError(String password) {
        return CommonUtils.isValidPasswordFormat(password);
    }

    @Override
    public void onUserFound() {
        view.onUserFound();
    }

    @Override
    public void onError(Throwable throwable) {
        view.onDatabaseError(throwable.getMessage());
    }

}
