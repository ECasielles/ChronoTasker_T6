package com.example.usuario.chronotasker.ui.task.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.home.HomeActivity;
import com.example.usuario.chronotasker.ui.task.adapter.OnItemActionListener;
import com.example.usuario.chronotasker.ui.task.adapter.RecyclerItemTouchHelper;
import com.example.usuario.chronotasker.ui.task.adapter.TaskAdapter;
import com.example.usuario.chronotasker.ui.task.contract.TaskListContract;
import com.example.usuario.chronotasker.ui.task.presenter.TaskListPresenter;

import java.util.ArrayList;

/**
 * Fragment que muestra la lista de tareas
 */
public class TaskListFragment extends Fragment implements TaskListContract.View,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, TaskViewFragment.TaskCreationListener, OnItemActionListener {
    public static final String TAG = "TaskListFragment";
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private TaskListContract.Presenter presenter;

    //CONSTRUCTOR
    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Menu
        setHasOptionsMenu(true);
        //Retener fragment
        setRetainInstance(true);
        //Inicializar presenter
        presenter = new TaskListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflar vista + Crear elementos
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        //RecyclerView
        recyclerView = view.findViewById(android.R.id.list);

        //Adapter + Presenter
        adapter = new TaskAdapter(getContext(), this);
        presenter.importTasks();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //ItemTouchCallbackHelper controla la acción Swipe
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(
                0, ItemTouchHelper.START | ItemTouchHelper.END, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        //FloatingActionButton
        ((HomeActivity) getActivity()).floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(null);
            }
        });
    }

    private void addTask(Bundle bundle) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        TaskViewFragment taskViewFragment = (TaskViewFragment) fragmentManager.findFragmentByTag(TaskViewFragment.TAG);
        if (taskViewFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            taskViewFragment = TaskViewFragment.getInstance(bundle, this);
            fragmentTransaction.addToBackStack(TaskViewFragment.TAG);
            fragmentTransaction.replace(R.id.frame_content, taskViewFragment, TaskViewFragment.TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_activity_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemClickListener(Task task) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Task.TAG, task);
        addTask(bundle);
    }

    //TODO: Check it is working
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_task_sort_default:
                presenter.importTasks();
                break;
            case R.id.action_menu_task_sort_priority:
                presenter.importTasksSortedByPriority();
                break;
            case R.id.action_menu_task_sort_start_date:
                presenter.importTasksSortedByStartDate();
                break;
            case R.id.action_menu_task_sort_urgent:
                presenter.importTasksSortedByUrgency();
                break;
            case R.id.action_menu_task_sort_important:
                presenter.importTasksSortedByImportance();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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

    /**
     * Elimina definitivamente o restaura una tarea según el Snackbar que muestra
     *
     * @param position
     * @param task
     */
    @Override
    public void onTaskDeleteEvent(final int position, final Task task) {
        //Notifica y permite deshacer la acción
        Snackbar snackbar = Snackbar.make(recyclerView, task.getTitle() + " archivada", Snackbar.LENGTH_LONG);
        snackbar.addCallback(new Snackbar.Callback() {
                                 @Override
                                 public void onDismissed(Snackbar transientBottomBar, int event) {
                                     super.onDismissed(transientBottomBar, event);
                                     if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT)
                                         presenter.deleteTask(task);
                                 }
                             }
        );
        snackbar.setAction(getContext().getResources().getString(R.string.undo), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.restoreTask(position, task);
            }
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
    @Override
    public void onTaskRestored(int position, Task task) {
        adapter.restoreItem(position, task);
        Toast.makeText(getContext(), task.getTitle() + " " + getResources().getString(R.string.info_task_restored), Toast.LENGTH_SHORT).show();
    }

    /**
     * Notifica que la tarea ha sido eliminada
     *
     * @param title
     */
    @Override
    public void onDeleteTaskInfo(String title) {
        Toast.makeText(getContext(), title + " " + getResources().getString(R.string.info_task_deleted), Toast.LENGTH_SHORT).show();
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
        if (viewHolder instanceof TaskAdapter.TaskHolder) {
            int index = viewHolder.getAdapterPosition();
            Task task = adapter.getItem(index);

            //Elimina el elemento de la vista
            adapter.remove(index);
            presenter.deleteTaskEvent(index, task);
        }
    }

    @Override
    public void reloadTasks() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        adapter = null;
    }

}
