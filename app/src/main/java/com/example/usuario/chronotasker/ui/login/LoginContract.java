package com.example.usuario.chronotasker.ui.login;

import com.example.usuario.chronotasker.data.db.model.User;

/**
 * Presenter de LoginActivity
 */
public interface LoginContract {

    interface View {
        void errorEmptyField();
        void errorNameLengthInvalid();
        void errorPasswordLengthInvalid();
        void errorPasswordFormatInvalid();

        void loginUser();

        void onUserFound();

        void onDatabaseError(String message);
    }

    interface Presenter {
        void loginUser(User user);

        void validateFields(String name, String password);

        boolean onNameEmptyError(String name);

        boolean onPasswordEmptyError(String password);

        boolean onNameLengthValidError(String name);

        boolean onPasswordLengthValidError(String password);

        boolean onPasswordFormatValidError(String password);
    }

}
