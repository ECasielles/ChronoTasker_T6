package com.example.usuario.chronotasker.mvvm.base;

import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;
import com.example.usuario.chronotasker.utils.ActivityUtils;

public abstract class BaseActivity extends AppCompatActivity
        implements OnFragmentActionListener.FragmentEventHandler, INavigator {

    protected OnFragmentActionListener selectedFragment;
    protected NavigatorViewModel localViewModel;


    /**
     * Initialises the Toolbar
     */
    public void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }


    @Override
    public void launchFragment(BaseFragment instance) {
        selectedFragment = (OnFragmentActionListener)
                getSupportFragmentManager().findFragmentById(R.id.frame_content);

        if(selectedFragment == null) {
            selectedFragment = instance;

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    (Fragment) selectedFragment,
                    R.id.frame_content,
                    selectedFragment.getFragmentTag()
            );
        }

        findOrCreateViewModel();
    }


    @Override
    public void stackFragment(BaseFragment instance) {
        selectedFragment = (OnFragmentActionListener)
                getSupportFragmentManager().findFragmentByTag(instance.getFragmentTag());

        if(selectedFragment == null) {
            selectedFragment = instance;

            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(),
                    (Fragment) selectedFragment,
                    R.id.frame_content,
                    selectedFragment.getFragmentTag()
            );
        }

        findOrCreateViewModel();
    }



    public void findOrCreateViewModel() {
        Fragment viewModelHolder =
                getSupportFragmentManager().findFragmentByTag(selectedFragment.getViewModelTag());

        ViewModelHolder<?> retainedViewModel = (ViewModelHolder<?>) viewModelHolder;

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            localViewModel = (NavigatorViewModel) retainedViewModel.getViewmodel();
        } else {
            localViewModel = selectedFragment.makeViewModel();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(localViewModel),
                    selectedFragment.getViewModelTag()
            );
        }

        ((BaseFragment<ViewDataBinding, NavigatorViewModel>)selectedFragment)
                .setViewModel(localViewModel);
    }

}
