package com.example.usuario.chronotasker.ui.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.model.Alarm;
import com.example.usuario.chronotasker.ui.home.HomeActivity;

/**
 * Created by icenri on 2/26/18.
 */

public class AlarmBroadcast extends BroadcastReceiver {
    public static final int ALARM_NOTIFICATION = 1;
    public static final String CHANNEL_ID = "Notification";
    private Alarm alarm;

    public AlarmBroadcast() {
    }

    public AlarmBroadcast(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, ALARM_NOTIFICATION, newIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID);

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            builder.setContentTitle(context.getResources()
                    .getString(R.string.alarm_title));
            builder.setContentText(context.getResources().getString(R.string.alarm_content));
        } else {
            builder.setContentTitle(intent.getStringExtra("Title"));
            builder.setContentText(intent.getStringExtra("Content"));
        }

        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        android.R.drawable.ic_lock_idle_alarm))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ALARM_NOTIFICATION, builder.build());
    }

}
