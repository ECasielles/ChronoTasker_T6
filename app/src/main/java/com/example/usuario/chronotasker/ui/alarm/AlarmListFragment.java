package com.example.usuario.chronotasker.ui.alarm;

import android.support.v7.app.AppCompatActivity;

import com.example.usuario.chronotasker.ui.base.BaseFragment;

/**
 * Created by icenri on 2/25/18.
 */

public class AlarmListFragment extends BaseFragment {
    private static final String TAG = "AlarmListFragment";

    public static AlarmListFragment newInstance(AppCompatActivity appCompatActivity) {
        AlarmListFragment alarmListFragment = (AlarmListFragment)
                appCompatActivity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (alarmListFragment == null)
            alarmListFragment = new AlarmListFragment();
        return alarmListFragment;
    }

    /**
     * Al pulsar Back, envía a la Activity la orden de cerrar
     */
    @Override
    public boolean onBackPressed() {
        fragmentEventHandler.setSelectedFragment(null);
        return false;
    }

}
