package com.example.usuario.chronotasker.ui.login;

import com.example.usuario.chronotasker.data.db.repository.UserRepository;
import com.example.usuario.chronotasker.data.model.User;

/**
 * Created by icenri on 2/26/18.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private OnUserFoundListener listener;

    public LoginInteractorImpl(LoginInteractor.OnUserFoundListener listener) {
        this.listener = listener;
    }

    @Override
    public void findUser(User user) {
        UserRepository.getInstance().search(user, this);
    }

    @Override
    public void onSuccess() {
        listener.onUserFound();
    }

    @Override
    public void onError(Throwable throwable) {
        listener.onError(throwable);
    }

}
