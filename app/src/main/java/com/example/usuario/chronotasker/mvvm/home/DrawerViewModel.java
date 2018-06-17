package com.example.usuario.chronotasker.mvvm.home;

import android.arch.lifecycle.MutableLiveData;

import com.example.usuario.chronotasker.data.model.User;
import com.example.usuario.chronotasker.data.repository.UserRepository;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

public class DrawerViewModel extends NavigatorViewModel {

    private static final String TAG = DrawerViewModel.class.getSimpleName();

    public final MutableLiveData<User> user = new MutableLiveData<>();

    public final MutableLiveData<String> name = new MutableLiveData<>();

    public final MutableLiveData<String> email = new MutableLiveData<>();

    public final MutableLiveData<Integer> imageId = new MutableLiveData<>();

    public HomeNavigator mNavigator;

    public DrawerViewModel() {
        User currentUser = UserRepository.getInstance().findCurrentUser();
        if (currentUser == null)
            currentUser = UserRepository.getInstance().findFirstUser();

        if (currentUser != null) {
            user.setValue(currentUser);
            name.setValue(currentUser.getName());
            email.setValue(currentUser.getEmail());
            imageId.setValue(currentUser.getImgProfile());
        }
    }

    public User getUser() {
        return user.getValue();
    }
    public String getName() {
        return name.getValue();
    }
    public String getEmail() {
        return email.getValue();
    }
    public int getImageId() {
        Integer val = imageId.getValue();
        return (val == null) ?
                0 :
                val.intValue();
    }

    @Override
    public void setNavigator(INavigator navigator) {
        mNavigator = (HomeNavigator) navigator;
    }


    @Override
    public String getTag() {
        return TAG;
    }
}
