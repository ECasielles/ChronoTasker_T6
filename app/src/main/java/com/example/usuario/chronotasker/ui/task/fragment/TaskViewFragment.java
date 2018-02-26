package com.example.usuario.chronotasker.ui.task.fragment;

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
import com.example.usuario.chronotasker.data.db.ChronoTaskerApplication;
import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.home.HomeActivity;
import com.example.usuario.chronotasker.ui.task.contract.TaskCreationContract;
import com.example.usuario.chronotasker.ui.task.presenter.TaskCreationPresenter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TaskViewFragment extends Fragment implements TaskCreationContract.View {
    public static final String TAG = "TaskViewFragment";

    private TaskCreationListener callback;
    private TaskCreationContract.Presenter presenter;
    private TextInputLayout tilTitle, tilDescription;
    private CheckBox ckbInformal, ckbDefault, ckbImportant, ckbUrgent;
    private TextView txvDateTime;

    //CONSTRUCTOR
    public static TaskViewFragment getInstance(Bundle bundle, TaskCreationListener taskCreationListener) {
        TaskViewFragment taskViewFragment = new TaskViewFragment();
        taskViewFragment.callback = taskCreationListener;
        if (bundle != null)
            taskViewFragment.setArguments(bundle);
        return taskViewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Mantener los datos en cambio de contexto
        setRetainInstance(true);
        //Inicializar presenter
        presenter = new TaskCreationPresenter(this);
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

        //FloatingActionButton
        ((HomeActivity) getActivity()).floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category category = new Category(Category.CATEGORY_ARCHIVED);
                if (ckbInformal.isChecked())
                    category.setInformal();
                if (ckbDefault.isChecked())
                    category.setDefault();
                if (ckbImportant.isChecked())
                    category.setImportant();
                if (ckbUrgent.isChecked())
                    category.setUrgent();

                presenter.addTask(new Task(
                        -1,
                        tilTitle.getEditText().getText().toString(),
                        ChronoTaskerApplication.getContext().getPreferencesHelper().getCurrentUserId(),
                        -1,
                        dateTime,
                        dateTime,
                        category,
                        tilDescription.getEditText().getText().toString(),
                        null,
                        -1,
                        null,
                        null
                ));
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

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    //CONTRATO CON LA ACTIVITY
    public interface TaskCreationListener {
        void reloadTasks();
    }
}
