package com.example.usuario.chronotasker.data.db.repository;

import android.content.res.Resources;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.dao.TaskDao;
import com.example.usuario.chronotasker.data.db.model.Task;

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

    public ArrayList<Task> getActiveTasks() {
        return taskDao.loadAllActive();
    }

    //TODO: Check if is still necessary
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

    public void addTask(Task task) {
        taskDao.save(task);
    }

    //TODO: Handle error cases
    public void deleteTask(Task task, TaskRepositoryCallback callback) {
        if (taskDao.delete(task) > 0)
            callback.onSuccess(task.getTitle());
        else
            callback.onError(new Throwable(Resources.getSystem().getString(R.string.error_database_delete)));
    }

    public void updateTask(Task task, TaskRepositoryCallback callback) {
        if (taskDao.update(task) > 0)
            callback.onSuccess(task.getTitle());
        else
            callback.onError(new Throwable(Resources.getSystem().getString(R.string.error_database_update)));
    }

}
