package com.example.usuario.chronotasker.mvvm.task.list;

import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.databinding.ItemTaskBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseBindAdapter;
import com.example.usuario.chronotasker.mvvm.base.BaseBindHolder;

import java.util.List;


/**
 * Adaptador de la lista de tareas de TaskActivity
 * @see BaseBindAdapter
 */
public class TaskListAdapter extends BaseBindAdapter<Task, ItemTaskBinding> {

    private TaskListViewModel mViewModel;

    public TaskListAdapter(int layoutResId, @Nullable List<Task> data) {
        super(layoutResId, data);
    }

    public void setViewModel(TaskListViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    protected void convert(BaseBindHolder helper, Task task) {
        mBinding = (ItemTaskBinding) helper.getBinding();
        mBinding.setPosition(getData().indexOf(task));
        mBinding.setTask(task);
        mBinding.setViewModel(mViewModel);

        //Force binding update
        mBinding.executePendingBindings();
    }

}