package com.example.usuario.chronotasker.ui.login;

/**
 * Presenter de LoginActivity
 */
public interface LoginContract {

    interface View {
        void emptyFieldError();

        void nameLengthInvalidError();

        void passwordLengthInvalidError();

        void navigateToHome();

        void passwordFormatInvalidError();

        void addUserPreferences(String name, String password, Boolean remember);
    }

    interface Presenter {
        void validate(String name, String password, Boolean remember);

        boolean isNameEmpty(String name);

        boolean isPasswordEmpty(String password);

        boolean isNameLengthValid(String name);

        boolean isPasswordLengthValid(String password);

        boolean isPasswordFormatValid(String password);
    }

}