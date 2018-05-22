package com.example.usuario.chronotasker.mvvm.signup;


import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.utils.Common;

/**
 * Presenter de la clase SignupActivity
 */
public class SignupPresenter implements SignupContract.Presenter, SignupInteractor.OnUserAddedListener {
    private final SignupInteractor interactor;
    private SignupContract.View view;

    public SignupPresenter(SignupContract.View view) {
        this.view = view;
        this.interactor = new SignupInteractorImpl(this);
    }

    public void addUser(User user) {
        interactor.addUser(user);
    }

    @Override
    public void onUserAdded() {
        view.onUserAdded();
    }

    @Override
    public void validateFields(String name, String email, String password) {
        if (onNameEmptyError(name) || onPasswordEmptyError(password) || onEmailEmptyError(email))
            view.errorEmptyField();
        else if (!isNameLengthValid(name))
            view.errorNameLengthInvalid();
        else if (!isPasswordLengthValid(password))
            view.errorPasswordLengthInvalid();
        else if (!isPasswordFormatValid(password))
            view.errorPasswordFormatInvalid();
        else if (!isEmailFormatValid(email))
            view.errorEmailFormatInvalid();
        else
            view.addUser();
    }

    @Override
    public boolean onNameEmptyError(String name) {
        return name.isEmpty();
    }

    @Override
    public boolean onEmailEmptyError(String email) {
        return email.isEmpty();
    }

    @Override
    public boolean onPasswordEmptyError(String password) {
        return password.isEmpty();
    }

    @Override
    public boolean isNameLengthValid(String name) {
        return Common.isValidFieldLength(name);
    }

    @Override
    public boolean isEmailFormatValid(String email) {
        return Common.isValidEmailFormat(email);
    }

    @Override
    public boolean isPasswordLengthValid(String password) {
        return Common.isValidFieldLength(password);
    }

    @Override
    public boolean isPasswordFormatValid(String password) {
        return Common.isValidPasswordFormat(password);
    }

    @Override
    public void onDatabaseError(Throwable throwable) {
        view.onDatabaseError(throwable.getMessage());
    }

}
