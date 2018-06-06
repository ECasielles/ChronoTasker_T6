package com.example.usuario.chronotasker.mvvm.binding;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

public class BindingAdapter {

    @android.databinding.BindingAdapter("bind:srcCompat")
    public static void bindSrcCompat(ImageView view, int resourceId) {
        view.setImageResource(resourceId);
    }

    @android.databinding.BindingAdapter({"bind:onClick"})
    public static void bindOnClick(View view, View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
        view.setClickable(true);
    }

    @android.databinding.BindingAdapter("bind:text")
    public static void bindText(EditText view, String text) {
        String content = text == null ? "" : text;
        view.setText(content);
    }

    @android.databinding.BindingAdapter({"bind:checked"})
    public static void bindChecked(CompoundButton view, boolean checked) {
        view.setChecked(checked);
    }

}
