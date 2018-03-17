package com.example.usuario.chronotasker.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Comparator;

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
    public static final Comparator<Task> COMPARATOR_ID = new Comparator<Task>() {
        @Override
        public int compare(Task task, Task otherTask) {
            return task.compareTo(otherTask);
        }
    };
    /**
     * Comparator que devuelve la tarea más prioritaria
     */
    public static final Comparator<Task> COMPARATOR_PRIORITY = new Comparator<Task>() {
        @Override
        public int compare(Task task, Task otherTask) {
            int res = task.getCategory().compareTo(otherTask.getCategory());
            if (res == 0)
                res = task.compareTo(otherTask);
            return res;
        }
    };
    /**
     * Comparator que devuelve las tarea más próxima. Si empatan, la que termine antes.
     * Si empatan, la que tenga menor id.
     */
    public static final Comparator<Task> COMPARATOR_START_DATE = new Comparator<Task>() {
        @Override
        public int compare(Task task, Task otherTask) {
            int res = task.getStartDate().compareTo(otherTask.getStartDate());
            if (res == 0)
                res = task.compareTo(otherTask);
            return res;
        }
    };
    /**
     * Comparator que devuelve la tarea más urgente.
     * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_URGENT = new Comparator<Task>() {
        @Override
        public int compare(Task task, Task otherTask) {
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
        }
    };
    /**
     * Comparator que devuelve la tarea más importante.
     * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_IMPORTANT = new Comparator<Task>() {
        @Override
        public int compare(Task task, Task otherTask) {
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
        }
    };
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
    ToOne<User> user;
    //PARAMETROS
    @Id
    private long id;
    private String title;
    private int iconId;
    private DateTime startDate;
    private DateTime endDate;
    private Category category;
    private String description;
    private String location;
    private int alarmId;
    private Period repetition;
    private String reminder;

    //CONSTRUCTOR
    public Task(long id, String title, int iconId, @Nullable DateTime startDate,
                @Nullable DateTime endDate, Category category, @Nullable String description,
                @Nullable String location, int alarmId, @Nullable Period repetition,
                @Nullable String reminder) {
        this.id = id;
        this.iconId = iconId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.description = description;
        this.location = location;
        this.alarmId = alarmId;
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
        alarmId = in.readInt();
        reminder = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeInt(iconId);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeInt(alarmId);
        parcel.writeString(reminder);
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
    public int getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
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
     *
     * @param o
     * @return
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
