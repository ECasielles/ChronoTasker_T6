package com.example.usuario.chronotasker.data.db.repository;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.data.db.dao.TaskDao;
import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.model.Task;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Contiene los elementos de la lista de tareas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Task
 */
public class TaskRepository {
    private static TaskRepository taskRepository;
    private static TaskDao taskDao;

    static {
        taskRepository = new TaskRepository();
    }

    private ArrayList<Task> tasks;

    private TaskRepository() {
        this.tasks = new ArrayList<>();
        taskDao = new TaskDao();
    }


    public static TaskRepository getInstance() {
        if (taskRepository == null)
            taskRepository = new TaskRepository();
        return taskRepository;
    }

    public static void deleteTask(Task task) {

    }

    //TODO: Add more fields for edition
    public static void editTask(Task task, String title) {

    }

    public void addTask(String title, int iconId, @Nullable DateTime startDate,
                        @Nullable Date endDate, Category categoryFlags, @Nullable String description,
                        @Nullable String location, int alarmId, @Nullable Date repeat,
                        @Nullable String reminder) {

    }

    /**
     * Devolviendo un ArrayList.
     */
    public ArrayList<Task> getTasks() {
        tasks.clear();
        Cursor cursor = getTasksCursor();
        //El cursor siempre se coloca antes del primer elemento.
        if (cursor.moveToFirst())
            do {
                //Acceder a las columnas en el mismo orden
                Task task = new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        new DateTime(cursor.getString(4)),
                        new DateTime(cursor.getString(5)),
                        new Category(cursor.getInt(6)),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        new Period(cursor.getString(10).equals("") ? null : new Period(cursor.getString(10)).toString()),
                        cursor.getString(11)
                );
                tasks.add(task);
            } while (cursor.moveToNext());
        return tasks;
    }

    /**
     * Devolviendo Cursor
     *
     * @return
     */
    public Cursor getTasksCursor() {
        return taskDao.loadAll();
    }

    //TODO: Do not return archived Tasks
    public ArrayList<Task> getTasksOrderById() {
        if (tasks.size() == 0)
            tasks = getTasks();
        Collections.sort(tasks, Task.COMPARATOR_ID);
        return tasks;
    }

    public ArrayList<Task> getTasksOrderByPriority() {
        Collections.sort(tasks, Task.COMPARATOR_PRIORITY);
        return tasks;
    }

    public ArrayList<Task> getTasksOrderByStartDate() {
        Collections.sort(tasks, Task.COMPARATOR_START_DATE);
        return tasks;
    }

    public ArrayList<Task> getTasksOrderByUrgency() {
        Collections.sort(tasks, Task.COMPARATOR_URGENT);
        return tasks;
    }

    public ArrayList<Task> getTasksOrderByImportance() {
        Collections.sort(tasks, Task.COMPARATOR_IMPORTANT);
        return tasks;
    }

}
