package com.example.usuario.chronotasker.mvvm.task.adapter;

import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.databinding.ItemTaskBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseBindAdapter;
import com.example.usuario.chronotasker.mvvm.base.BaseBindHolder;
import com.example.usuario.chronotasker.mvvm.base.IMessageListener;

import java.util.List;


/**
 * Adaptador de la lista de tareas de TaskActivity
 */
public class TaskAdapter extends BaseBindAdapter<Task, ItemTaskBinding> {

    public TaskAdapter(int layoutResId, @Nullable List<Task> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, Task task) {
        //Set model to item_model layout
        ItemTaskBinding binding = (ItemTaskBinding) helper.getBinding();
        binding.setTask(task);
        binding.setListener((IMessageListener) binding.getRoot().getContext());

        //Force binding update
        if(binding.hasPendingBindings())
            binding.executePendingBindings();
    }

}