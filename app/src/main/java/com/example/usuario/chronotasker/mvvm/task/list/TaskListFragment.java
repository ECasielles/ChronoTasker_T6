package com.example.usuario.chronotasker.mvvm.task.list;

import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Task;
import com.example.usuario.chronotasker.databinding.FragmentTaskListBinding;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;
import com.example.usuario.chronotasker.mvvm.task.item.TaskItemFragment;

import java.util.Objects;


/**
 * Fragment que muestra la lista de tareas
 */
public class TaskListFragment extends BaseFragment<FragmentTaskListBinding, TaskListViewModel>
        implements TaskListNavigator {

    //CONSTANTES
    public static String TAG = TaskListFragment.class.getSimpleName();


    private TaskListAdapter mAdapter;


    public static TaskListFragment getInstance() {
        return new TaskListFragment();
    }

    /**
     * Inicializa los parámetros del Fragment
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mAdapter = new TaskListAdapter(R.layout.item_task, mViewModel.getTaskList(), mViewModel);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);

        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);

        mBinding.recyclerTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerTaskList.setAdapter(mAdapter);

        mBinding.executePendingBindings();

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mBinding.recyclerTaskList);

        setupDraggableAdapter();

        return mBinding.getRoot();
    }



    private void setupDraggableAdapter() {
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {}
            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {}
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                onTaskSwiped(viewHolder);
            }
            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorSecondaryDark));
            }
        });
    }



    private void onTaskSwiped(RecyclerView.ViewHolder viewHolder) {
        int index = viewHolder.getAdapterPosition();
        Task task = mAdapter.getItem(index);

        //Elimina el elemento de la vista
        mAdapter.remove(index);
        mViewModel.removeState(index);
        onTaskDeleteEvent(index, task);
    }


    /**
     * Notifica que va a eliminar y permite deshacer la acción
     * según el Snackbar que muestra.
     */
    public void onTaskDeleteEvent(final int position, final Task task) {
        Snackbar snackbar = Snackbar.make(mParent, task.getTitle() + " archivada", Snackbar.LENGTH_LONG);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT)
                    mViewModel.deleteTask(task);
            }
        });
        snackbar.setAction(Objects.requireNonNull(getContext()).getResources().getString(R.string.undo), view -> restoreTask(position, task));
        snackbar.setActionTextColor(getContext().getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }


    /**
     * Restaura la tarea en la vista y lo notifica
     */
    public void restoreTask(int position, Task task) {
        mAdapter.addData(position, task);
        mViewModel.restoreState();
        Snackbar.make(mParent, task.getTitle() + " " + getResources().getString(R.string.info_task_restored), Toast.LENGTH_SHORT).show();
    }

    /**
     * Notifica que la tarea ha sido eliminada
     */
    @Override
    public void onDeleteTaskInfo(String title) {
        Snackbar.make(mParent, title + " " + getResources().getString(R.string.info_task_deleted), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void openTask(Task task) {
        Bundle bundle = null;
        if(task != null) {
            bundle = new Bundle();
            bundle.putParcelable(Task.TAG, task);
        }
        mFragmentEventHandler.stackFragment(TaskItemFragment.getInstance(bundle));
    }


    @Override
    public void adapterNotifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }


    @Override
    public void adapterNotifyItemRemoved(int position) {
        mAdapter.notifyItemRemoved(position);
    }


    @Override
    public void adapterNotifyRangeChanged(int startPosition) {
        mAdapter.notifyItemRangeChanged(startPosition, mAdapter.getItemCount());
    }


    @Override
    public void adapterNotifySetChanged() {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_activity_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }


    @Override
    public TaskListViewModel makeViewModel() {
        return new TaskListViewModel();
    }


    @Override
    public String getFragmentTag() {
        return TAG;
    }


    @Override
    public String getViewModelTag() {
        return TaskListViewModel.TAG;
    }


}
