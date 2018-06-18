package com.example.usuario.chronotasker.mvvm.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Alarm;
import com.example.usuario.chronotasker.mvvm.base.BaseFragment;

import org.joda.time.DateTime;

import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by icenri on 2/25/18.
 */

public class AlarmListFragment extends BaseFragment<ViewDataBinding, AlarmViewModel> implements OnAlarmActionListener, AlarmNavigator {

    public static final String TAG = AlarmListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ViewGroup parent;
    private AlarmAdapter adapter;


    public static AlarmListFragment getInstance() {
        return new AlarmListFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public AlarmViewModel makeViewModel() {
        return new AlarmViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        parent = view.findViewById(android.R.id.content);
        recyclerView = view.findViewById(android.R.id.list);
        adapter = new AlarmAdapter(getContext(), this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAlarmClick(Alarm alarm) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(alarm.TAG, alarm);
        setAlarmText(bundle);
    }

    /**
     * Lanza el cuadro de diálogo para editar o crear alarma.
     *
     * @param bundle
     */
    private void setAlarmText(final Bundle bundle) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getContext().getResources().getString(R.string.alarm_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setAlarmTime(setAlarmDialog(bundle));
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

    /**
     * Configura los parámetros de texto de la alarma
     *
     * @param bundle
     */
    private Alarm setAlarmDialog(Bundle bundle) {
        EditText edtAlarmTitle = new EditText(getContext());
        EditText edtAlarmContent = new EditText(getContext());

        String title = edtAlarmTitle.getText().toString();
        String content = edtAlarmContent.getText().toString();

        Alarm alarm = new Alarm();
        if (bundle != null) {
            alarm = bundle.getParcelable(alarm.TAG);
            edtAlarmTitle.setText(alarm.getTitle());
            edtAlarmContent.setText(alarm.getRingtoneUri());
        } else {
            if (!title.isEmpty())
                alarm.setTitle(title);
            if (!content.isEmpty())
                alarm.setRingtoneUri(content);
        }
        return alarm;
    }

    /**
     * Prepara y lanza el cuadro de diálogo que selecciona la hora de la alarma.
     * También lanza el evento al receptor AlarmBroadcast.
     * Se recomienda poner 0 horas y 1 minuto para probarlo.
     *
     * @param alarm
     */
    private void setAlarmTime(final Alarm alarm) {
        TimePickerDialog timePickerDialog;
        final DateTime dateTime = DateTime.now();
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                setNotification(hourOfDay, minute, dateTime, alarm);
            }
        };
        timePickerDialog = new TimePickerDialog(
                getContext(), listener, dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), true
        );
        timePickerDialog.show();
    }

    /**
     * Una vez configurada la alarma, se añade o se actualiza en la BD.
     *
     * @param alarm
     * @param dateTime
     */
    private void setNotification(int hourOfDay, int minute, DateTime dateTime, Alarm alarm) {
        Intent intent = new Intent("com.example.usuario.chronotasker.alarm");
        intent.putExtra("Title", alarm.getTitle());
        intent.putExtra("Content", alarm.getRingtoneUri());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(), AlarmBroadcast.ALARM_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarm.setTime(dateTime.plusHours(hourOfDay).plusMinutes(minute));
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime().getMillis(), pendingIntent);
        Toast.makeText(getContext(), alarm.getTitle() + " sonará a las " + alarm.getTime().toString("HH:mm"), Toast.LENGTH_SHORT).show();
        /*
        if (getArguments() != null)
            presenter.editAlarm(alarm);
        else
            presenter.addAlarm(alarm);
        */
    }

    public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        adapter.clear();
        adapter.addAll(alarms);
    }



    public void onAlarmUpdated(String title) {
        Toast.makeText(getContext(), title + " " + getResources().getString(R.string.info_task_updated), Toast.LENGTH_SHORT).show();
    }



    public void onDatabaseError(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public String getFragmentTag() {
        return TAG;
    }


    @Override
    public String getViewModelTag() {
        return AlarmViewModel.TAG;
    }
}
