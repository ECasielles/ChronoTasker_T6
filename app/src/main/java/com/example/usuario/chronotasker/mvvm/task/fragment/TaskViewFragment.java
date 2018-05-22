package com.example.usuario.chronotasker.mvvm.task.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.chronotasker.app.App;
import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Category;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;
import com.example.usuario.chronotasker.mvvm.home.HomeActivity;
import com.example.usuario.chronotasker.mvvm.task.contract.TaskViewContract;
import com.example.usuario.chronotasker.mvvm.task.presenter.TaskViewPresenter;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TaskViewFragment extends BaseFragment implements TaskViewContract.View {
    public static final String TAG = "TaskViewFragment";

    private TaskViewContract.Presenter presenter;
    private TextInputLayout tilTitle, tilDescription;
    private CheckBox ckbInformal, ckbImportant, ckbUrgent;
    private TextView txvDateTime;
    private ViewGroup parent;

    public static TaskViewFragment getInstance(AppCompatActivity appCompatActivity, Bundle bundle) {
        TaskViewFragment taskViewFragment = (TaskViewFragment)
                appCompatActivity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (taskViewFragment == null)
            taskViewFragment = new TaskViewFragment();
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
        presenter = new TaskViewPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_view, container, false);
        tilTitle = view.findViewById(R.id.tilTitle);
        tilDescription = view.findViewById(R.id.tilDescription);
        ckbInformal = view.findViewById(R.id.ckbInformal);
        ckbImportant = view.findViewById(R.id.ckbImportant);
        ckbUrgent = view.findViewById(R.id.ckbUrgent);
        txvDateTime = view.findViewById(R.id.txvDateTime);
        //Guarda vista para Snackbar
        parent = view.findViewById(android.R.id.content);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
        final DateTime dateTime = new DateTime();
        txvDateTime.setText(formatter.print(dateTime));

        //FloatingActionButton
        final int finalId = getArgumentsAndTaskId();
        ((HomeActivity) getActivity()).floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() == null)
                    presenter.addTask(createTask(finalId));
                else
                    presenter.updateTask(createTask(finalId));
            }
        });
    }

    /**
     * Devuelve el id de la tarea si se le han pasado argumentos al Fragment
     * y rellena los campos de edición o devuelve -1.
     */
    private int getArgumentsAndTaskId() {
        Bundle args = getArguments();
        int id = -1;
        if (args != null) {
            Task task = args.getParcelable(Task.TAG);
            id = task.getId();
            tilTitle.getEditText().setText(task.getTitle());
            tilDescription.getEditText().setText(task.getDescription());
            Category category = task.getPriority();
            ckbInformal.setChecked(category.isInformal());
            ckbImportant.setChecked(category.isImportant());
            ckbUrgent.setChecked(category.isUrgent());
            txvDateTime.setText(task.getStartDate().toString());
        }
        return id;
    }

    @NonNull
    private Task createTask(int id) {
        Category category = new Category();
        category.setDefault();
        if (ckbInformal.isChecked())
            category.setInformal();
        if (ckbImportant.isChecked())
            category.setImportant();
        if (ckbUrgent.isChecked())
            category.setUrgent();

        return new Task(
                id,
                tilTitle.getEditText().getText().toString(),
                App.getApp().getPreferencesHelper().getCurrentUserId(),
                -1,
                new DateTime(txvDateTime.getText().toString()),
                new DateTime(txvDateTime.getText().toString()),
                category,
                tilDescription.getEditText().getText().toString(),
                "",
                -1,
                new Period(0),
                ""
        );
    }

    /**
     * Indica a la Activity que debe sacar este Fragment de la pila
     */
    @Override
    public void reloadTaskList() {
        fragmentEventHandler.popBackStack();
    }

    @Override
    public void onDatabaseError(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void taskUpdatedInfo(String title) {
        Toast.makeText(getContext(), title + " " + getResources().getString(R.string.info_task_updated), Toast.LENGTH_SHORT).show();
    }

    /**
     * Al pulsar Back, envía a la Activity la orden de cerrar este Fragment
     */
    @Override
    public boolean onBackPressed() {
        fragmentEventHandler.popBackStack();
        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentEventHandler = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
