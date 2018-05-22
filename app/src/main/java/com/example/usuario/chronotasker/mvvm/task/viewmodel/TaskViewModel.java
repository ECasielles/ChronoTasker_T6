package com.example.usuario.chronotasker.mvvm.task.viewmodel;

import android.arch.lifecycle.LiveData;

import com.example.usuario.chronotasker.mvvm.base.BaseViewModel;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.data.repository.TaskRepository;

import java.util.List;

import io.objectbox.android.ObjectBoxLiveData;

public class TaskViewModel extends BaseViewModel {

    //private MutableLiveData<List<Model>> listMutableLiveData;
    private ObjectBoxLiveData<Task> modelLiveData;

    public TaskViewModel() {
        //listMutableLiveData = new MutableLiveData<>();
        //listMutableLiveData.setValue(ModelRepository.getModelsLiveData().getValue());
        //modelLiveData = ModelRepository.getModelsLiveData();
        //addSubscription(ModelRepository.subscribeToModelList(this::refreshList));
    }


    public LiveData<List<Task>> getTasks() {
        return TaskRepository.getTasksLiveData();
    }

}
