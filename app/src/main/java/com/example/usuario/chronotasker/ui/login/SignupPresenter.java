package com.example.usuario.chronotasker.ui.login;


import com.example.usuario.chronotasker.data.db.repository.UserRepository;

/**
 * Presenter de la clase SignupActivity
 */
public class SignupPresenter {

    //TODO: MVP Signup

    public void newUser(String name, String email, String password) {
        //TODO: Validar usuario
        UserRepository.getInstance().addUser(name, email, password);
    }

}
