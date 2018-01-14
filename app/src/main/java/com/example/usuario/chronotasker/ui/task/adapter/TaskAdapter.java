package com.example.usuario.chronotasker.ui.task.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.model.Task;

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

    public TaskAdapter(Context context) {
        this.context = context;
        this.tasks = new ArrayList<>();
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

        //Usa Joda-Time para dar formato a las fechas
        DateTime dateTime = tasks.get(position).getStartDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("DD/MM/YYYY");
        holder.txvStartDate.setText(formatter.print(dateTime));

        //Toma el valor de la descripción de la tarea u oculta el campo
        String description = tasks.get(position).getDescription();
        if (description == null) {
            holder.txvDescription.setVisibility(View.GONE);
        } else {
            holder.txvDescription.setText(description);
            holder.txvDescription.setVisibility(View.VISIBLE);
        }

        //Toma el valor de la categoría u oculta el campo
        Category category = tasks.get(position).getCategoryFlags();
        if (category.isNone()) {
            holder.txvCategory.setVisibility(View.GONE);
        } else {
            holder.txvCategory.setVisibility(View.VISIBLE);
            holder.txvCategory.setText(context.getResources()
                    .getString(tasks.get(position).getCategoryFlags().getCategoryNameId()));
        }
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
        TextView txvTitle, txvStartDate, txvDescription, txvCategory;
        public CardView cardView;

        public TaskHolder(View itemView) {
            super(itemView);
            txvTitle = itemView.findViewById(R.id.txvTitle);
            txvStartDate = itemView.findViewById(R.id.txvDate);
            txvDescription = itemView.findViewById(R.id.txvDescription);
            txvCategory = itemView.findViewById(R.id.txvCategory);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
