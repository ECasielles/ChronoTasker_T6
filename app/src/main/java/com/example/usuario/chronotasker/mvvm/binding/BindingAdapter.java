package com.example.usuario.chronotasker.mvvm.binding;

import android.view.View;
import android.widget.ImageView;

public class BindingAdapter {

    @android.databinding.BindingAdapter("bind:srcCompat")
    public static void bindImage(ImageView view, int r) {
        view.setImageResource(r);
    }

    @android.databinding.BindingAdapter({"bind:onClick"})
    public static void setOnClick(View view, View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
        view.setClickable(true);
    }

}
