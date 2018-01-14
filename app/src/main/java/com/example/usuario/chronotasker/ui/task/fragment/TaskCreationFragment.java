package com.example.usuario.chronotasker.ui.task.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.ui.task.TaskActivity;
import com.example.usuario.chronotasker.ui.task.contract.TaskCreationContract;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TaskCreationFragment extends Fragment implements TaskCreationContract.View {

    public static final String TAG = "TaskCreationFragment";

    private TaskCreationListener callback;
    private TaskCreationContract.Presenter presenter;
    private TextInputLayout tilTitle, tilDescription;
    private CheckBox ckbInformal, ckbDefault, ckbImportant, ckbUrgent;
    private TextView txvDateTime;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creation, container, false);
        tilTitle = view.findViewById(R.id.tilTitle);
        tilDescription = view.findViewById(R.id.tilDescription);
        ckbInformal = view.findViewById(R.id.ckbInformal);
        ckbDefault = view.findViewById(R.id.ckbDefault);
        ckbImportant = view.findViewById(R.id.ckbImportant);
        ckbUrgent = view.findViewById(R.id.ckbUrgent);
        txvDateTime = view.findViewById(R.id.txvDateTime);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm DD/MM/YYYY");
        final DateTime dateTime = new DateTime();
        txvDateTime.setText(formatter.print(dateTime));
        super.onViewCreated(view, savedInstanceState);

        //TODO: Implement the rest of the features
        //FloatingActionButton
        ((TaskActivity) getActivity()).floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addTask(
                        tilTitle.getEditText().getText().toString(),
                        tilDescription.getEditText().getText().toString(),
                        ckbInformal.isChecked(),
                        ckbDefault.isChecked(),
                        ckbImportant.isChecked(),
                        ckbUrgent.isChecked(),
                        dateTime
                );
            }
        });

    }

    //COMUNICACION CON EL PRESENTER
    @Override
    public void setPresenter(TaskCreationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void reloadTaskList() {
        callback.reloadTasks();
    }


}
