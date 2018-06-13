package com.example.usuario.chronotasker.mvvm.task.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.databinding.FragmentTaskListBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;
import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;
import com.example.usuario.chronotasker.mvvm.task.adapter.OnTaskActionListener;
import com.example.usuario.chronotasker.mvvm.task.adapter.RecyclerItemTouchHelper;
import com.example.usuario.chronotasker.mvvm.task.adapter.TaskAdapter;
import com.example.usuario.chronotasker.mvvm.task.view.TaskViewFragment;

import java.util.Objects;

/**
 * Fragment que muestra la lista de tareas
 */
public class TaskListFragment extends BaseFragment<FragmentTaskListBinding, TaskListViewModel>
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, OnTaskActionListener, OnFragmentActionListener {

    //CONSTANTS
    public static String TAG = TaskListFragment.class.getSimpleName();

    private TaskAdapter adapter;
    private ViewGroup parent;


    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }


    /**
     * Devuelve la instancia guardada de este Fragment o crea una nueva
     * si no existe.
     *
     * @param appCompatActivity
     * @return
     */
    public static TaskListFragment getInstance(AppCompatActivity appCompatActivity) {
        TaskListFragment taskListFragment = (TaskListFragment)
                appCompatActivity.getSupportFragmentManager().findFragmentByTag(TAG);
        return taskListFragment == null ?
                new TaskListFragment() :
                taskListFragment;
    }


    /**
     * Inicializa los parámetros del Fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentEventHandler = (FragmentEventHandler) getActivity();
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        adapter = new TaskAdapter(R.layout.item_task, mViewModel.getTaskList());
        mBinding.recyclerTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerTaskList.setAdapter(adapter);

        if(mBinding.hasPendingBindings())
            mBinding.executePendingBindings();

        //setRetainInstance(false);
        //mViewModel.setNavigator(this);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Guarda vista para Snackbar
        parent = (ViewGroup) view.getParent();

        //setupRecyclerView(view);

        //ItemTouchCallbackHelper controla la acción Swipe
        /*ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(
                0, ItemTouchHelper.START | ItemTouchHelper.END, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);*/
    }

    /**
     * Lanza TaskViewFragment
     * @param bundle
     */
    private void openTask(Bundle bundle) {
        fragmentEventHandler.launchFragment(
                TaskViewFragment.getInstance(
                        (AppCompatActivity) Objects.requireNonNull(getActivity()),
                        bundle
                ));
    }

    /**
     * Método callback de la interfaz OnTaskActionListener que permite
     * editar una tarea.
     * @param task
     */
    @Override
    public void onTaskClick(Task task) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Task.TAG, task);
        openTask(bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_activity_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Elimina definitivamente o restaura una tarea según el Snackbar que muestra
     *
     * @param position
     * @param task
     */
    public void onTaskDeleteEvent(final int position, final Task task) {
        //Notifica y permite deshacer la acción
        Snackbar snackbar = Snackbar.make(getView(), task.getTitle() + " archivada", Snackbar.LENGTH_LONG);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                //if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) presenter.deleteTask(task);
            }
        });
        snackbar.setAction(getContext().getResources().getString(R.string.undo), view -> {
            //presenter.restoreTask(position, task);
        });
        snackbar.setActionTextColor(getContext().getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    /**
     * Restaura la tarea en la vista y lo notifica
     *
     * @param position
     * @param task
     */
    public void onTaskRestored(int position, Task task) {
        //adapter.restoreItem(position, task);
        Toast.makeText(getContext(), task.getTitle() + " " + getResources().getString(R.string.info_task_restored), Toast.LENGTH_SHORT).show();
    }

    /**
     * Notifica que la tarea ha sido eliminada
     *
     * @param title
     */
    public void onDeleteTaskInfo(String title) {
        Toast.makeText(getContext(), title + " " + getResources().getString(R.string.info_task_deleted), Toast.LENGTH_SHORT).show();
    }

    public void onDatabaseError(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Método callback de la vista que elimina un elemento de la vista y envía al presenter
     * la intención de eliminarlo del repositorio.
     *
     * @param viewHolder
     * @param direction
     * @param position
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        int index = viewHolder.getAdapterPosition();
        Task task = adapter.getItem(index);

        //Elimina el elemento de la vista
        adapter.remove(index);
        //presenter.deleteTaskEvent(index, task);
    }

    /**
     * Al pulsar Back, envía a la Activity la orden de cerrar
     */
    @Override
    public boolean onBackPressed() {
        fragmentEventHandler.setSelectedFragment(null);
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }
}
