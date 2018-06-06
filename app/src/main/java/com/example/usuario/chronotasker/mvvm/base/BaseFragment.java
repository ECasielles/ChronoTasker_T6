package com.example.usuario.chronotasker.mvvm.base;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

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
        //addObserver();
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
        //mRootView = null;
        //removeObserver();
        mViewModel = null;
    }

/*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    private void addObserver() {
        if (mViewModel != null)  getLifecycle().addObserver((LifecycleObserver) mViewModel);
    }


    private void removeObserver() {
        if (mViewModel != null) getLifecycle().removeObserver((LifecycleObserver) mViewModel);
    }
*/

}
