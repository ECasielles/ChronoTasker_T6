package com.example.usuario.chronotasker.data.db.repository;

import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.data.db.dao.TaskDao;
import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.model.Task;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Contiene los elementos de la lista de tareas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Task
 */
public class TaskRepository {
    private static TaskRepository taskRepository;

    static {
        taskRepository = new TaskRepository();
    }

    private TaskDao taskDao;

    private TaskRepository() {
        taskDao = new TaskDao();
    }

    public static TaskRepository getInstance() {
        if (taskRepository == null)
            taskRepository = new TaskRepository();
        return taskRepository;
    }

    public void addTask(String title, int iconId, @Nullable DateTime startDate,
                        @Nullable DateTime endDate, Category categoryFlags, @Nullable String description,
                        @Nullable String location, int alarmId, @Nullable DateTime repeat,
                        @Nullable String reminder) {
        taskDao.save(title, iconId, startDate, endDate, categoryFlags,
                description, location, alarmId, repeat, reminder);
    }

    public ArrayList<Task> getAllTasks() {
        return taskDao.loadAll();
    }

    public ArrayList<Task> getActiveTasks() {
        return taskDao.loadAllActive();
    }

    //TODO: Handle the case where tasks.size() == 0
    public ArrayList<Task> getTasksOrderById() {
        ArrayList<Task> tasks = getActiveTasks();
        if (tasks.size() != 0)
            Collections.sort(tasks, Task.COMPARATOR_ID);
        return tasks;
    }
    public ArrayList<Task> getTasksOrderByPriority() {
        ArrayList<Task> tasks = getActiveTasks();
        if (tasks.size() != 0)
            Collections.sort(tasks, Task.COMPARATOR_PRIORITY);
        return tasks;
    }
    public ArrayList<Task> getTasksOrderByStartDate() {
        ArrayList<Task> tasks = getActiveTasks();
        if (tasks.size() != 0)
            Collections.sort(tasks, Task.COMPARATOR_START_DATE);
        return tasks;
    }
    public ArrayList<Task> getTasksOrderByUrgency() {
        ArrayList<Task> tasks = getActiveTasks();
        if (tasks.size() != 0)
            Collections.sort(tasks, Task.COMPARATOR_URGENT);
        return tasks;
    }
    public ArrayList<Task> getTasksOrderByImportance() {
        ArrayList<Task> tasks = getActiveTasks();
        if (tasks.size() != 0)
            Collections.sort(tasks, Task.COMPARATOR_IMPORTANT);
        return tasks;
    }

    //TODO: Handle error cases
    public void deleteTask(int id) {
        taskDao.delete(id);
    }

    public void editTask(int id, String title, int iconId, @Nullable DateTime startDate,
                         @Nullable DateTime endDate, Category categoryFlags, @Nullable String description,
                         @Nullable String location, int alarmId, @Nullable DateTime repeat,
                         @Nullable String reminder) {
        taskDao.update(id, title, iconId, startDate, endDate, categoryFlags,
                description, location, alarmId, repeat, reminder);
    }

}
