package com.example.usuario.chronotasker.ui.task.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.task.adapter.TaskAdapter;
import com.example.usuario.chronotasker.ui.task.contract.TaskListContract;

import java.util.ArrayList;


public class TaskListFragment extends Fragment implements TaskListContract.View {

    public static final String TAG = "TaskListFragment";
    private TaskListListener callback;
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TaskListContract.Presenter presenter;


    //CONTRATO CON LA ACTIVITY
    public interface TaskListListener {
        void addTask(Bundle bundle);
    }

    //CONSTRUCTOR
    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    //CICLO DE VIDA: CREACION DE LA VISTA
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (TaskListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Error: " + activity + " must implement TaskListListener.");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Menu
        setHasOptionsMenu(true);

        //Retener fragment
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflar vista + Crear elementos
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        //Adapter
        adapter = new TaskAdapter();
        presenter.importTasks();

        //RecyclerView
        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    //TODO: Multiselección
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //RecyclerView y Adapter
        recyclerView.setAdapter(adapter);

        //Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    //CONTEXT MENU


    //COMUNICACION CON EL PRESENTER
    @Override
    public void setPresenter(TaskListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loadTasks(ArrayList<Task> tasks) {
        adapter.clear();
        adapter.addAll(tasks);
    }
}
