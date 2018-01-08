package com.example.usuario.chronotasker.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Representa una tarea creada por el usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Task implements Parcelable {

    public static final String TAG = "Task";
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

    public Task(String title, Date startDate, Date endDate, String description, String location) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.location = location;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        title = in.readString();
        iconId = in.readInt();
        ownerId = in.readInt();
        description = in.readString();
        location = in.readString();
        alarmId = in.readInt();
        reminder = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeInt(iconId);
        parcel.writeInt(ownerId);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeInt(alarmId);
        parcel.writeString(reminder);
    }

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
