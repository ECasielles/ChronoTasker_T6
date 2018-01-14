package com.example.usuario.chronotasker.data.db.model;

import android.os.Parcel;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * Representa una tarea importada por el usuario
 * desde un proyecto
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class ProjectTask extends Task {
    int projectId;
    int id;
    String title;
    int iconId;
    DateTime startDate;
    DateTime endDate;
    String description;
    String location;
    int alarmId;
    Interval repetitionInterval;
    String reminder;

    public ProjectTask(int id, String title, int ownerId, int iconId, @Nullable DateTime startDate,
                       @Nullable DateTime endDate, Category categoryFlags, @Nullable String description,
                       @Nullable String location, int alarmId, @Nullable Interval repetitionInterval, @Nullable String reminder) {
        super(id, title, ownerId, iconId, startDate, endDate, categoryFlags, description, location, alarmId, repetitionInterval, reminder);
    }

    protected ProjectTask(Parcel in) {
        super(in);
    }
}
