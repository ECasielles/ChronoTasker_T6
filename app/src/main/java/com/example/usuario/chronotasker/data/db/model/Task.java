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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getIconId() {
        return iconId;
    }
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    public int getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Category getCategoryFlags() {
        return categoryFlags;
    }
    public void setCategoryFlags(Category categoryFlags) {
        this.categoryFlags = categoryFlags;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }
    public Date getRepeat() {
        return repeat;
    }
    public void setRepeat(Date repeat) {
        this.repeat = repeat;
    }
    public String getReminder() {
        return reminder;
    }
    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

}
