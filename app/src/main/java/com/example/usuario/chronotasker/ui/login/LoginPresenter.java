package com.example.usuario.chronotasker.ui.login;

import com.example.usuario.chronotasker.data.db.repository.UserRepository;
import com.example.usuario.chronotasker.utils.CommonUtils;

/**
 * Presenter de LoginActivity
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void validate(String name, String password, Boolean remember) {
        if (isNameEmpty(name) || isPasswordEmpty(password))
            view.errorEmptyField();
        else if (!isNameLengthValid(name))
            view.errorNameLengthInvalid();
        else if (!isPasswordLengthValid(password))
            view.errorPasswordLengthInvalid();
        else if (!isPasswordFormatValid(password))
            view.errorPasswordFormatInvalid();

        if (UserRepository.getInstance().exists(name, password)) {
            view.addUserPreferences(name, password, remember);
            view.navigateToHome();
        }
    }

    @Override
    public boolean isNameEmpty(String name) {
        return name.isEmpty();
    }
    @Override
    public boolean isPasswordEmpty(String password) {
        return password.isEmpty();
    }
    @Override
    public boolean isNameLengthValid(String name) {
        return CommonUtils.isValidFieldLength(name);
    }
    @Override
    public boolean isPasswordLengthValid(String password) {
        return CommonUtils.isValidFieldLength(password);
    }
    @Override
    public boolean isPasswordFormatValid(String password) {
        return CommonUtils.isValidPasswordFormat(password);
    }

}
