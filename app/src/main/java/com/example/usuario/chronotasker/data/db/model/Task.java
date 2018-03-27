package com.example.usuario.chronotasker.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Comparator;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Representa una tarea creada por el usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
@Entity
public class Task implements Parcelable, Comparable {

    //CONSTANTES
    public static final String TAG = "TaskEntries";
    /**
     * Comparator que devuelve la tarea más antigua
     */
    public static final Comparator<Task> COMPARATOR_ID = Task::compareTo;
    /**
     * Comparator que devuelve la tarea más prioritaria
     */
    public static final Comparator<Task> COMPARATOR_PRIORITY = (task, otherTask) -> {
        int res = task.getCategory().compareTo(otherTask.getCategory());
        if (res == 0)
            res = task.compareTo(otherTask);
        return res;
    };
    /**
     * Comparator que devuelve las tarea más próxima. Si empatan, la que termine antes.
     * Si empatan, la que tenga menor id.
     */
    public static final Comparator<Task> COMPARATOR_START_DATE = (task, otherTask) -> {
        int res = task.getStartDate().compareTo(otherTask.getStartDate());
        if (res == 0)
            res = task.compareTo(otherTask);
        return res;
    };
    /**
     * Comparator que devuelve la tarea más urgente.
     * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_URGENT = (task, otherTask) -> {
        if (task.getCategory().isUrgent() && !otherTask.getCategory().isUrgent())
            return -1;
        else if (!task.getCategory().isUrgent() && otherTask.getCategory().isUrgent())
            return 1;
        else {
            int res = task.getStartDate().compareTo(otherTask.getStartDate());
            if (res == 0)
                res = task.compareTo(otherTask);
            return res;
        }
    };
    /**
     * Comparator que devuelve la tarea más importante.
     * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_IMPORTANT = (task, otherTask) -> {
        if (task.getCategory().isImportant() && !otherTask.getCategory().isImportant())
            return -1;
        else if (!task.getCategory().isImportant() && otherTask.getCategory().isImportant())
            return 1;
        else {
            int res = task.getStartDate().compareTo(otherTask.getStartDate());
            if (res == 0)
                res = task.compareTo(otherTask);
            return res;
        }
    };

    //PARAMETROS
    @Id
    private long id;
    private String title;
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
    private int iconId;
    ToOne<User> user;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private DateTime startDate;
    private Category category;
    private String description;
    private String location;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private DateTime endDate;
    private Period repetition;
    private String reminder;
    private Alarm alarm;

    //CONSTRUCTOR
    public Task(long id, String title, int iconId, @Nullable DateTime startDate,
                @Nullable DateTime endDate, Category category, @Nullable String description,
                @Nullable String location, Alarm alarm, @Nullable Period repetition,
                @Nullable String reminder) {
        this.id = id;
        this.iconId = iconId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.description = description;
        this.location = location;
        this.alarm = alarm;
        this.repetition = repetition;
        this.reminder = reminder;
    }

    //IMPLEMENTACION DE PARCELABLE
    protected Task(Parcel in) {
        id = in.readLong();
        title = in.readString();
        iconId = in.readInt();
        description = in.readString();
        location = in.readString();
        alarm = in.readParcelable(Alarm.class.getClassLoader());
        reminder = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeInt(iconId);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeParcelable(alarm, flags);
        dest.writeString(reminder);
    }

    //GETTERS Y SETTERS
    public long getId() {
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
    public DateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }
    public DateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
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

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
    public Period getRepetition() {
        return repetition;
    }
    public void setRepetition(Period repetition) {
        this.repetition = repetition;
    }
    public String getReminder() {
        return reminder;
    }
    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    //IMPLEMENTACION DE COMPARABLE
    /**
     * Implementación de la interfaz Comparable.
     * @param other Tarea con la que se compara
     * @return Devuelve positivo si el parámetro es una tarea creada posteriormente
     * en la base de datos y viceversa. Una tarea anterior es mayor que una posterior.
     */
    @Override
    public int compareTo(@NonNull Object other) {
        try {
            //TODO: FIX!!!
            return (int) (this.getId() - ((Task) other).getId());
        } catch (ClassCastException e) {
            throw new ClassCastException("Parameter must be of TaskEntries class");
        }
    }

    /**
     * Dos tareas son iguales si tienen el mismo id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        //TODO: FIX!!!
        return (int) id;
    }

    @Override
    public String toString() {
        return title;
    }

}
