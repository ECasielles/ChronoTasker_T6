package com.example.usuario.chronotasker.mvvm.base;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.usuario.chronotasker.R;


public class BaseBindHolder extends BaseViewHolder {

    public BaseBindHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }

}
