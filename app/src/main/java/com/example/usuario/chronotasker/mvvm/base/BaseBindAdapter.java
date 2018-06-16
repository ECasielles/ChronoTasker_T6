package com.example.usuario.chronotasker.mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.example.usuario.chronotasker.R;

import java.util.List;


public abstract class BaseBindAdapter<T, DB extends ViewDataBinding>
        extends BaseItemDraggableAdapter<T, BaseBindHolder> {

    protected DB mBinding;

    public BaseBindAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        mBinding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);

        if (mBinding == null)
            return super.getItemView(layoutResId, parent);

        View view = mBinding.getRoot();
        //view.setTag(R.id.BaseQuickAdapter_dragging_support, true);
        view.setTag(R.id.BaseQuickAdapter_swiping_support, true);
        view.setTag(R.id.BaseQuickAdapter_databinding_support, mBinding);
        return view;
    }

}
