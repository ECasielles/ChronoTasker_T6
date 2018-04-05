package com.example.usuario.chronotasker.ui.task.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Task;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Adaptador de la lista de tareas de TaskActivity
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private Context context;
    private ArrayList<Task> tasks;
    private OnTaskActionListener listener;

    public TaskAdapter(Context context, OnTaskActionListener listener) {
        this.context = context;
        this.listener = listener;
        this.tasks = new ArrayList<>();
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {

        ViewDataBinding viewDataBinding = customViewHolder.getViewDataBinding();
        viewDataBinding.setVariable(se.jayway.databinding.BR.mymodel, mMyModels.get(i));


        Task tempTask = tasks.get(position);
        holder.txvTitle.setText(tempTask.getTitle());

        //Usa Joda-Time para dar formato a las fechas
        DateTime dateTime = tempTask.getStartDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
        holder.txvStartDate.setText(formatter.print(dateTime));

        //Toma el valor de la descripción de la tarea u oculta el campo
        String description = tempTask.getDescription();
        if (description == null) {
            holder.txvDescription.setVisibility(View.GONE);
        } else {
            holder.txvDescription.setText(description);
            holder.txvDescription.setVisibility(View.VISIBLE);
        }

        holder.txvCategory.setText(tempTask.getCategory().toString());

        holder.bind(tempTask, listener);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void remove(int deleteIndex) {
        tasks.remove(deleteIndex);
        notifyItemRemoved(deleteIndex);
    }

    public void restoreItem(int restoredIndex, Task restoredTask) {
        tasks.add(restoredIndex, restoredTask);
        notifyItemInserted(restoredIndex);
    }

    public void clear() {
        tasks.clear();
    }

    public void addAll(ArrayList<Task> newTasks) {
        tasks.addAll(newTasks);
        notifyItemRangeChanged(0, newTasks.size());
    }

    public Task getItem(int adapterPosition) {
        return tasks.get(adapterPosition);
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        TextView txvTitle, txvStartDate, txvDescription, txvCategory;

        public TaskHolder(View itemView) {
            super(itemView);
            txvTitle = itemView.findViewById(R.id.txvTitle);
            txvStartDate = itemView.findViewById(R.id.txvDate);
            txvDescription = itemView.findViewById(R.id.txvDescription);
            txvCategory = itemView.findViewById(R.id.txvCategory);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public <T extends ViewDataBinding> TaskHolder(T inflate) {
            super(inflate);
        }

        public void bind(final Task task, final OnTaskActionListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTaskClick(task);
                }
            });
        }

    }

}
