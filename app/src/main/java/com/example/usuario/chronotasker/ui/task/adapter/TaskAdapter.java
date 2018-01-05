package com.example.usuario.chronotasker.ui.task.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.data.db.repository.TaskRepository;

import java.util.ArrayList;

/**
 * Adaptador de la lista de tareas de TaskActivity
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private ArrayList<Task> tasks;

    public TaskAdapter() {
        this.tasks = TaskRepository.getInstance();
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_task, parent, false);
        TaskHolder holder = new TaskHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.txvTitle.setText(tasks.get(position).getTitle());
        holder.txvDescription.setText(tasks.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txvTitle;
        TextView txvDescription;

        public TaskHolder(View itemView) {
            super(itemView);
            txvTitle = itemView.findViewById(R.id.txvTitle);
            txvDescription = itemView.findViewById(R.id.txvDescription);
        }

        //TODO: Swipe callback methods

        @Override
        public void onClick(View view) {

        }

    }
}
