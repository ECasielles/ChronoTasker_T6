package com.example.usuario.chronotasker.ui.login;

import com.example.usuario.chronotasker.data.db.repository.UserRepositoryCallback;
import com.example.usuario.chronotasker.data.model.User;

/**
 * Created by icenri on 2/26/18.
 */

public interface LoginInteractor extends UserRepositoryCallback {
    void findUser(User user);

    interface OnUserFoundListener {
        void onUserFound();

        void onError(Throwable throwable);
    }

}
