package com.example.usuario.chronotasker.ui.signup;

import com.example.usuario.chronotasker.data.db.repository.UserRepository;
import com.example.usuario.chronotasker.data.model.User;

/**
 * Created by icenri on 2/26/18.
 */

public class SignupInteractorImpl implements SignupInteractor {
    private OnUserAddedListener listener;

    public SignupInteractorImpl(SignupInteractor.OnUserAddedListener listener) {
        this.listener = listener;
    }

    @Override
    public void addUser(User user) {
        UserRepository.getInstance().addUser(user, this);
    }

    @Override
    public void onSuccess() {
        listener.onUserAdded();
    }

    @Override
    public void onError(Throwable throwable) {
        listener.onDatabaseError(throwable);
    }

}
