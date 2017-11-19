package com.example.usuario.chronotasker.data.db.model;

import java.util.Date;

/**
 * Representa una tarea creada por el usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Task {
    int id;
    String title;
    int iconId;
    int ownerId;
    Date startDate;
    Date endDate;
    Category categoryFlags;
    String description;
    String location;
    int alarmId;
    Date repeat;
    String reminder;
}
