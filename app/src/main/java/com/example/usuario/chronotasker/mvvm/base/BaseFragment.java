package com.example.usuario.chronotasker.mvvm.base;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.mvvm.base.navigator.INavigator;
import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

import static android.support.v4.util.Preconditions.checkNotNull;


/**
 * Clase base de la que heredan los Fragment. Maneja la
 * comunicación más habitual con la Activity.
 *
 * @author Enrique Casielles
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends NavigatorViewModel>
        extends Fragment implements OnFragmentActionListener, INavigator {


    //INHERITABLE FIELDS
    protected FragmentEventHandler mFragmentEventHandler;
    protected ViewGroup mParent;
    protected DB mBinding;
    protected VM mViewModel;


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
            mFragmentEventHandler = (FragmentEventHandler) getActivity();

            setRetainInstance(true);
            mViewModel.setNavigator(this);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Guarda vista para Snackbar
        mParent = (ViewGroup) view.getParent();
    }


    public abstract VM makeViewModel();


    public VM getViewModel() {
        return mViewModel;
    }


    @SuppressLint("RestrictedApi")
    public void setViewModel(@NonNull VM viewModel) {
        mViewModel = checkNotNull(viewModel);
    }


}
