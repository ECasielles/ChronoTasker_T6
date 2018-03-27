package com.example.usuario.chronotasker.ui.signup;

import com.example.usuario.chronotasker.data.db.model.User;
import com.example.usuario.chronotasker.data.db.repository.UserRepositoryCallback;

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