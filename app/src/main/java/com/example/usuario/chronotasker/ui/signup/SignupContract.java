package com.example.usuario.chronotasker.ui.signup;

import com.example.usuario.chronotasker.data.model.User;

/**
 * Created by icenri on 2/26/18.
 */

public interface SignupContract {
    interface View {
        void onUserAdded();

        void onDatabaseError(String message);

        void errorEmptyField();

        void errorNameLengthInvalid();

        void errorEmailFormatInvalid();

        void errorPasswordLengthInvalid();

        void errorPasswordFormatInvalid();

        void addUser();
    }

    interface Presenter {
        void addUser(User user);

        void validateFields(String name, String password, String email);

        boolean onNameEmptyError(String name);

        boolean onPasswordEmptyError(String password);

        boolean onEmailEmptyError(String email);

        boolean isNameLengthValid(String name);

        boolean isEmailFormatValid(String email);

        boolean isPasswordLengthValid(String password);

        boolean isPasswordFormatValid(String password);
    }

}
