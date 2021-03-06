package com.example.usuario.chronotasker.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.data.db.converter.DateTimeConverter;
import com.example.usuario.chronotasker.data.db.converter.PeriodConverter;
import com.example.usuario.chronotasker.mvvm.base.BaseModel;

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
 * <link>http://objectbox.io/documentation/relations/</link>
 */
@Entity
public class Task extends BaseModel implements Parcelable, Comparable {

    //CONSTANTES
    public static final String TAG = "TaskEntries";


    //PARAMETROS
    @Id(assignable = true)
    public long id;
    public ToOne<User> user;

    private String title;
    private int iconId;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private DateTime startDate;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private DateTime endDate;
    private int priority;
    private String description;
    private String location;
    @Convert(converter = PeriodConverter.class, dbType = Integer.class)
    private Period repetition;
    private String reminder;
    private String alarm;


    //CONSTRUCTOR
    public Task() { }
    public Task(String title, int iconId, @Nullable DateTime startDate,
                @Nullable DateTime endDate, int priority, @Nullable String description,
                @Nullable String location, @Nullable String alarm, @Nullable Period repetition,
                @Nullable String reminder) {
        this.title = title;
        this.iconId = iconId;
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



    //GETTERS Y SETTERS
    public long getId() {
        return id;
    }
    public void setId(long id) {
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
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
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
    public String getAlarm() {
        return alarm;
    }
    public void setAlarm(String alarm) {
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


    //PARCELABLE
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


    //COMPARATOR
    /**
     * Devuelve la tarea más antigua
     */
    public static final Comparator<Task> COMPARATOR_ID = Task::compareTo;
    /**
     * Devuelve las tarea más próxima. Si empatan, la que termine antes.
     * Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_START_DATE = Task::compareTaskStartDate;
    /**
     * Devuelve la tarea más urgente. * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_URGENT = (task, otherTask) ->
            compareTaskCategory(task, otherTask, Category.CATEGORY_URGENT);
    /**
     * Comparator que devuelve la tarea más importante.
     * Si empatan, la más próxima. Si empatan, la de id inferior.
     */
    public static final Comparator<Task> COMPARATOR_IMPORTANT = (task, otherTask) ->
            compareTaskCategory(task, otherTask, Category.CATEGORY_IMPORTANT);
    /**
     * Devuelve la tarea con más prioridad.
     */
    public static final Comparator<Task> COMPARATOR_PRIORITY = Task::compareTaskPriority;


    //METODOS DE COMPARATOR
    private static int compareTaskStartDate(Task task, Task otherTask) {
        int comparison = task.getStartDate().compareTo(otherTask.getStartDate());
        return compareIdsIfNotEqual(task, otherTask, comparison);
    }
    private static int compareTaskCategory(Task task, Task otherTask, int category) {
        int comparison = Category.compareFlagPriority(task.getPriority(), otherTask.getPriority(), category);
        return compareStartDatesIfNotEqual(task, otherTask, comparison);
    }
    private static int compareTaskPriority(Task task, Task otherTask) {
        int comparison = task.getPriority() - otherTask.getPriority();
        return compareStartDatesIfNotEqual(task, otherTask, comparison);
    }
    private static int compareStartDatesIfNotEqual(Task task, Task otherTask, int comparison) {
        return comparison != 0 ? comparison : compareTaskStartDate(task, otherTask);
    }
    private static int compareIdsIfNotEqual(Task task, Task otherTask, int comparison) {
        return comparison != 0 ? comparison : task.compareTo(otherTask);
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
