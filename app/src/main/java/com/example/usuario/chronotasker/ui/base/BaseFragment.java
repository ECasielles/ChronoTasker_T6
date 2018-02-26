package com.example.usuario.chronotasker.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Clase base de la que heredan los Fragment. Maneja la
 * comunicación más habitual con la Activity.
 *
 * @author Enrique Casielles
 */
public abstract class BaseFragment extends Fragment implements OnFragmentActionListener {
    protected FragmentEventHandler fragmentEventHandler;

    /**
     * Instancia el objeto callback que maneja la comunicación entre
     * Fragment y Activity para los eventos de botón Back.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof FragmentEventHandler))
            throw new ClassCastException(getActivity() + " must implement OnFragmentActionListener.FragmentEventHandler");
        else
            fragmentEventHandler = (FragmentEventHandler) getActivity();
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

}
