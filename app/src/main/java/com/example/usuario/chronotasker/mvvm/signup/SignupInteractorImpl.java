package com.example.usuario.chronotasker.mvvm.signup;

import com.example.usuario.chronotasker.data.repository.UserRepository;
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
        UserRepository.getInstance().updateUser(user, this);
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
