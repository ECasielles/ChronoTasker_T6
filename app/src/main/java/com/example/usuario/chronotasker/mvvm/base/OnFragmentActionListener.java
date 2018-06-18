package com.example.usuario.chronotasker.mvvm.base;

import com.example.usuario.chronotasker.mvvm.base.navigator.NavigatorViewModel;

/**
 * Interfaz de comunicación entre Fragment y Activity cuyos
 * métodos se llaman cuando se pulsa el botón back.
 * Debe ser heredada por los Fragment que hereden de BaseFragment
 */
public interface OnFragmentActionListener {

    String getFragmentTag();

    String getViewModelTag();

    NavigatorViewModel makeViewModel();


    interface FragmentEventHandler {

        void launchFragment(BaseFragment instance);

        void stackFragment(BaseFragment instance);

    }

}
