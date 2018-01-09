package com.example.usuario.chronotasker.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.R;

import java.util.Comparator;
import java.util.Date;

/**
 * Representa una tarea creada por el usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Task implements Parcelable, Comparable {

    //CONSTANTES
    public static final String TAG = "Task";

    /**
     * Comparator que devuelve la tarea más prioritaria
     */
    public static final Comparator<Category> CATEGORY_COMPARATOR = new Comparator<Category>() {
        @Override
        public int compare(Category category, Category otherCategory) {
            return category.getPriority() - otherCategory.getPriority();
        }
    };
    /**
     * Comparator que devuelve las tarea más inminente
     */
    public static final Comparator<Date> START_DATE_COMPARATOR = new Comparator<Date>() {
        @Override
        public int compare(Date date, Date otherDate) {
            return date.compareTo(otherDate);
        }
    };

    //PARAMETROS
    private int id;
    private String title;
    private int ownerId;
    private int iconId;
    private Date startDate;
    private @Nullable
    Date endDate;
    private Category categoryFlags;
    private String description;
    private @Nullable
    String location;
    private int alarmId;
    private @Nullable
    Date repeat;
    private @Nullable
    String reminder;

    //CONSTRUCTOR
    public Task(int id, String title, int ownerId, int iconId, @Nullable Date startDate,
                @Nullable Date endDate, Category categoryFlags, @Nullable String description,
                @Nullable String location, int alarmId, @Nullable Date repeat,
                @Nullable String reminder) {
        this.id = id;
        this.ownerId = ownerId;
        //TODO: Add multiple task icons
        this.iconId = R.drawable.icon_add_task_default;
        this.title = title;
        if (startDate == null)
            this.startDate = new Date();
        else
            this.startDate = startDate;
        this.endDate = endDate;
        this.categoryFlags = categoryFlags;
        this.description = description;
        this.location = location;
        //TODO: Add multiple alarms
        this.alarmId = -1;
        this.repeat = repeat;
        this.reminder = reminder;
    }

    //IMPLEMENTACION DE PARCELABLE
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

    //GETTERS Y SETTERS
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

    /**
     * Implementación de la interfaz Comparable.
     *
     * @param other Tarea con la que se compara
     * @return Devuelve negativo si el parámetro
     * es una tarea creada posteriormente en la base de datos
     * y viceversa.
     */
    @Override
    public int compareTo(@NonNull Object other) {
        return this.getId() - ((Task) other).getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id == task.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return title;
    }

}
