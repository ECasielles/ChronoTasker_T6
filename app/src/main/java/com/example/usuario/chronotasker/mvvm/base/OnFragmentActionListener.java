package com.example.usuario.chronotasker.mvvm.base;

/**
 * Interfaz de comunicación entre Fragment y Activity cuyos
 * métodos se llaman cuando se pulsa el botón back.
 * Debe ser heredada por los Fragment que hereden de BaseFragment
 */
public interface OnFragmentActionListener {
    boolean onBackPressed();

    interface FragmentEventHandler {
        void setSelectedFragment(OnFragmentActionListener listener);

        void launchFragment(OnFragmentActionListener listener);

        void addFragment(OnFragmentActionListener listener);

        void popBackStack();
    }
}
