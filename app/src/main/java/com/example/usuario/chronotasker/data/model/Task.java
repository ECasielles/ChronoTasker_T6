package com.example.usuario.chronotasker.data.model;

import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.mvvm.base.BaseModel;
import com.example.usuario.chronotasker.data.db.converter.DateTimeConverter;

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
 * @version 2.0
 */
@Entity
public class Task extends BaseModel implements Parcelable, Comparable {

    //CONSTANTES
    public static final String TAG = "TaskEntries";
    /**
     * Comparator que devuelve la tarea más antigua
     */
    public static final Comparator<Task> COMPARATOR_ID = Task::compareTo;
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
     * Comparator que devuelve la tarea más prioritaria
     */
    public static final Comparator<Task> COMPARATOR_PRIORITY = (task, otherTask) -> {
        int res = task.getPriority() - otherTask.getPriority();
        if (res == 0)
            res = task.compareTo(otherTask);
        return (res > 0) ? 1 : -1;
    };
    /**
     * Comparator que devuelve la tarea más urgente.
     * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_URGENT = (task, otherTask) -> {
        int priorityComparer = Category.comparer(task.getPriority(), otherTask.getPriority(),
                Category.CATEGORY_URGENT);
        if(priorityComparer != 0) {
            int res = task.getStartDate().compareTo(otherTask.getStartDate());
            if (res == 0)
                res = task.compareTo(otherTask);
            return res;
        }
        return priorityComparer;
    };
    /**
     * Comparator que devuelve la tarea más importante.
     * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_IMPORTANT = (task, otherTask) -> {
        int priorityComparer = Category.comparer(task.getPriority(), otherTask.getPriority(),
                Category.CATEGORY_IMPORTANT);
        if (priorityComparer != 0) {
            int res = task.getStartDate().compareTo(otherTask.getStartDate());
            if (res == 0)
                res = task.compareTo(otherTask);
            return res;
        }
        return priorityComparer;
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


    //PARAMETROS
    @Id
    private long id;
    private String title;
    private int iconId;
    private ToOne<User> owner;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private DateTime startDate;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private DateTime endDate;
    private int priority;
    private String description;
    private String location;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private Period repetition;
    private String reminder;
    private String alarm;


    //CONSTRUCTOR
    public Task() { }
    public Task(long id, String title, int iconId, @Nullable DateTime startDate,
                @Nullable DateTime endDate, int priority, @Nullable String description,
                @Nullable String location, @Nullable String alarm, @Nullable Period repetition,
                @Nullable String reminder) {
        this.id = id;
        this.iconId = iconId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.description = description;
        this.location = location;
        this.alarm = alarm;
        this.repetition = repetition;
        this.reminder = reminder;
    }
    public Task(long id, String title, int iconId, @Nullable DateTime startDate,
                 @Nullable DateTime endDate, int priority, long ownerId, @Nullable String description,
                 @Nullable String location, @Nullable String alarm, @Nullable Period repetition,
                 @Nullable String reminder) {
        this.id = id;
        this.iconId = iconId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.owner.setTargetId(ownerId);
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
        priority = in.readInt();
        description = in.readString();
        location = in.readString();
        alarm = in.readString();
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
        dest.writeInt(priority);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(alarm);
        dest.writeString(reminder);
    }

    //GETTERS Y SETTERS
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Bindable
    public String getTitle() {
        return title;
    }
    @Bindable
    public void setTitle(String title) {
        this.title = title;
    }
    @Bindable
    public int getIconId() {
        return iconId;
    }
    @Bindable
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    @Bindable
    public DateTime getStartDate() {
        return startDate;
    }
    @Bindable
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }
    @Bindable
    public DateTime getEndDate() {
        return endDate;
    }
    @Bindable
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
    @Bindable
    public int getPriority() {
        return priority;
    }
    @Bindable
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public ToOne<User> getOwner() {
        return owner;
    }
    public void setOwner(ToOne<User> owner) {
        this.owner = owner;
    }
    @Bindable
    public String getDescription() {
        return description;
    }
    @Bindable
    public void setDescription(String description) {
        this.description = description;
    }
    @Bindable
    public String getLocation() {
        return location;
    }
    @Bindable
    public void setLocation(String location) {
        this.location = location;
    }
    @Bindable
    public String getAlarm() {
        return alarm;
    }
    @Bindable
    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
    @Bindable
    public Period getRepetition() {
        return repetition;
    }
    @Bindable
    public void setRepetition(Period repetition) {
        this.repetition = repetition;
    }
    @Bindable
    public String getReminder() {
        return reminder;
    }
    @Bindable
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
            return (int) (getId() - ((Task) other).getId());
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
        return (int) id;
    }

    @Override
    public String toString() {
        return title;
    }
}
