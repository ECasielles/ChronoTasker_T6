package com.example.usuario.chronotasker.mvvm.alarm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Alarm;

import java.util.ArrayList;

/**
 * Created by icenri on 2/26/18.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private ArrayList<Alarm> alarms;
    private Context context;
    private OnAlarmActionListener listener;

    public AlarmAdapter(Context context, OnAlarmActionListener listener) {
        this.context = context;
        this.listener = listener;
        this.alarms = new ArrayList<>();
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.txvTitle.setText(alarm.getTitle());
        holder.txvTime.setText(alarm.getTime().toString());
        holder.bind(alarm, listener);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void clear() {
        alarms.clear();
    }

    public void addAll(ArrayList<Alarm> newAlarms) {
        alarms.addAll(newAlarms);
        notifyItemRangeChanged(0, newAlarms.size());
    }

    public void remove(int deleteIndex) {
        alarms.remove(deleteIndex);
        notifyItemRemoved(deleteIndex);
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView txvTitle, txvTime;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            txvTitle = itemView.findViewById(R.id.txvTitle);
            txvTime = itemView.findViewById(R.id.txvTime);
        }

        private void bind(final Alarm alarm, final OnAlarmActionListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAlarmClick(alarm);
                }
            });
        }
    }

}
