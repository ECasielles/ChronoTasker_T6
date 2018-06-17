package com.example.usuario.chronotasker.mvvm.base;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.chronotasker.utils.ActivityUtils;

import static android.support.v4.util.Preconditions.checkNotNull;


/**
 * Clase base de la que heredan los Fragment. Maneja la
 * comunicación más habitual con la Activity.
 *
 * @author Enrique Casielles
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel>
        extends Fragment implements OnFragmentActionListener {

    //PRIVATE FIELDS
    //private View mRootView;

    //INHERITABLE FIELDS
    protected FragmentEventHandler fragmentEventHandler;
    protected DB mBinding;
    protected VM mViewModel;

    //CONSTRUCTOR
    public BaseFragment() {
        setArguments(new Bundle());
    }


    /**
     * Instancia el objeto callback que maneja la comunicación entre
     * Fragment y Activity para los eventos de botón Back.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof FragmentEventHandler)) {
            throw new ClassCastException(getActivity() + " must implement OnFragmentActionListener.FragmentEventHandler");
        } else {
            fragmentEventHandler = (FragmentEventHandler) getActivity();
        }
    }

    public abstract VM makeViewModel();

    public VM getViewModel() {
        return mViewModel;
    }

    @SuppressLint("RestrictedApi")
    public void setViewModel(@NonNull VM viewModel) {
        mViewModel = checkNotNull(viewModel);
    }


    /**
     * Marca el Fragment actual como el que la Activity tiene
     * seleccionado para la correcta propagación del onBackPressed
     * desde el Fragment hasta la Activity.
     *
     * @see OnFragmentActionListener
     */
    @Override
    public void onStart() {
        super.onStart();
        fragmentEventHandler.setSelectedFragment(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
        mViewModel = null;
    }


    public static BaseFragment getInstance(AppCompatActivity appCompatActivity,
                                           Class<? extends BaseFragment> fragmentClass,
                                           Class<? extends BaseViewModel> viewModelClass) {

        FragmentManager manager = appCompatActivity.getSupportFragmentManager();

        BaseFragment fragmentByTag = (BaseFragment) manager.findFragmentByTag(fragmentClass.getSimpleName());

        try {

            fragmentByTag = fragmentByTag == null ? fragmentClass.newInstance() : fragmentByTag;

            fragmentByTag.fetchViewModel(manager, fragmentByTag, viewModelClass.getSimpleName());

            return fragmentByTag;

        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void fetchViewModel(FragmentManager manager, BaseFragment baseFragment, String VmTag) {
        Fragment fragmentByTag = manager.findFragmentByTag(VmTag);


        if ((fragmentByTag != null) && !(fragmentByTag instanceof ViewModelHolder<?>))
            throw new ClassCastException(fragmentByTag + " not an instance of ViewModelHolder<?>");

        ViewModelHolder<VM> retainedViewModel = (ViewModelHolder<VM>) fragmentByTag;

        // Returns retained ViewModel
        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            setViewModel(retainedViewModel.getViewmodel());
        } else {
            // Creates a new ViewModel and binds it to this Activity's lifecycle
            BaseViewModel viewModel = baseFragment.makeViewModel();
            ActivityUtils.addFragmentToActivity(
                    manager,
                    ViewModelHolder.createContainer(viewModel),
                    VmTag
            );
            setViewModel((VM) viewModel);
        }
    }

}
