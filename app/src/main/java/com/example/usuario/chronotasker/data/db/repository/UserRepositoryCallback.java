package com.example.usuario.chronotasker.data.db.repository;

/**
 * Created by icenri on 2/26/18.
 */

public interface UserRepositoryCallback {
    void onSuccess();

    void onError(Throwable throwable);

}
