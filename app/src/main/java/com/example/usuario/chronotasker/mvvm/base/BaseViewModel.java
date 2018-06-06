package com.example.usuario.chronotasker.mvvm.base;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.reactive.DataSubscription;


public abstract class BaseViewModel extends ViewModel {

    // Using ObjectBox Data Subscription for observable items
    private final List<DataSubscription> mSubscriptions;

    public BaseViewModel() {
        mSubscriptions = new ArrayList<>();
    }

    protected final void addSubscription(@NonNull DataSubscription subscription) {
        mSubscriptions.add(subscription);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        for (DataSubscription subscription : mSubscriptions) {
            if (!subscription.isCanceled()) {
                subscription.cancel();
            }
        }
    }

/*
    public static Class<? extends ViewModel> getSubClass(ViewModel viewModel) {
        return viewModel.getClass();
    }
*/

}
