package com.example.usuario.chronotasker.mvvm.base;

import io.objectbox.annotation.BaseEntity;

@BaseEntity
public abstract class BaseModel {

    public final String TAG = this.getClass().getSimpleName();

    public BaseModel() {
        super();
    }

}
