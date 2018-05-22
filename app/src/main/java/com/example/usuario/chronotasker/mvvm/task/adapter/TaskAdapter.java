package com.example.usuario.chronotasker.mvvm.task.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.BR;
import com.example.usuario.chronotasker.mvvm.base.BaseBindAdapter;
import com.example.usuario.chronotasker.mvvm.base.BaseBindHolder;
import com.example.usuario.chronotasker.data.model.Task;

import java.util.List;

/**
 * Adaptador de la lista de tareas de TaskActivity
 */
public class TaskAdapter extends BaseBindAdapter<Task> {

    public TaskAdapter(int layoutResId, @Nullable List<Task> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, Task item) {
        ViewDataBinding binding = helper.getBinding();

        //Set model to item_model layout
        binding.setVariable(BR.task, item);
        binding.setVariable(BR.listener, binding.getRoot().getContext());

        //Force binding update
        binding.executePendingBindings();
    }

}