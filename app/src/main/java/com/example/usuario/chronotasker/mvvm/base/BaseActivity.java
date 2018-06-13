package com.example.usuario.chronotasker.mvvm.base;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel>
        extends AppCompatActivity implements OnFragmentActionListener.FragmentEventHandler {
    public final String TAG = this.getClass().getName();

    protected DB mBinding;

    protected VM mViewModel;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBinding = null;

        mViewModel = null;
    }

}
