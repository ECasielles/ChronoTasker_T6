package com.example.usuario.chronotasker.mvvm.base;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import io.objectbox.annotation.BaseEntity;

@BaseEntity
public abstract class BaseModel extends BaseObservable {

    public final String TAG = this.getClass().getSimpleName();

    public BaseModel() {
        super();
    }

    @Override
    public void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        super.addOnPropertyChangedCallback(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        super.removeOnPropertyChangedCallback(callback);
    }

    @Override
    public void notifyChange() {
        super.notifyChange();
    }

    @Override
    public void notifyPropertyChanged(int fieldId) {
        super.notifyPropertyChanged(fieldId);
    }

}
