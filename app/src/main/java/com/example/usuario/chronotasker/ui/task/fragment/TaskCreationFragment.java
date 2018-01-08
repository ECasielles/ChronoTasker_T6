package com.example.usuario.chronotasker.ui.task.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.task.contract.TaskCreationContract;

import java.util.Date;


public class TaskCreationFragment extends Fragment implements TaskCreationContract.View {

    public static final String TAG = "TaskCreationFragment";
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    TaskCreationListener callback;
    private TaskCreationContract.Presenter presenter;

    //CONTRATO CON LA ACTIVITY
    public interface TaskCreationListener {
        void reloadTasks();
    }

    //CONSTRUCTOR
    public static TaskCreationFragment getInstance(Bundle bundle) {
        TaskCreationFragment taskCreationFragment = new TaskCreationFragment();
        if (bundle != null)
            taskCreationFragment.setArguments(bundle);
        return taskCreationFragment;
    }

    //CICLO DE VIDA DEL FRAGMENT: INICIO
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (TaskCreationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement TaskCreationListener interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Mantener los datos en cambio de contexto
        setRetainInstance(true);

        //TODO: Estudiar si se incluye menú contextual
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creation, container, false);

        //Toolbar
        toolbar = view.findViewById(R.id.toolbar);

        //Fab
        floatingActionButton = view.findViewById(R.id.floationActionButton);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //Fab
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addTask(new Task("", new Date(), new Date(), "", ""));
            }
        });

    }

    //COMUNICACION CON EL PRESENTER
    @Override
    public void setPresenter(TaskCreationContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
