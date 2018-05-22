package com.example.usuario.chronotasker.mvvm.signup;

import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.repository.callback.UserRepositoryCallback;

/**
 * Created by icenri on 2/26/18.
 */

public interface SignupInteractor extends UserRepositoryCallback {
    void addUser(User user);

    interface OnUserAddedListener {
        void onUserAdded();

        void onDatabaseError(Throwable throwable);
    }

}
