package com.example.usuario.chronotasker.data.db.model;

import java.util.Date;

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
    Date startDate;
    Date endDate;
    String description;
    String location;
    int alarmId;
    Date repeat;
    String reminder;
}
