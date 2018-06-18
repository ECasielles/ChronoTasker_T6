package com.example.usuario.chronotasker.mvvm.binding;

import android.view.View;
import android.widget.ImageView;

public class BindingAdapter {

    @android.databinding.BindingAdapter({"bind:onClick"})
    public static void bindOnClick(View view, View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
        view.setClickable(true);
    }

    @android.databinding.BindingAdapter("bind:setSrcCompat")
    public static void bindSrcCompat(ImageView view, int resourceId) {
        view.setImageResource(resourceId);
    }

    @android.databinding.BindingAdapter({"bind:onLongClick"})
    public static void bindOnLongClick(View view, View.OnLongClickListener clickListener) {
        view.setOnLongClickListener(clickListener);
        view.setClickable(true);
    }

}
