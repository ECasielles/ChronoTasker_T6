package com.example.usuario.chronotasker.data.repository;

import com.example.usuario.chronotasker.data.db.dao.TaskDao;
import com.example.usuario.chronotasker.data.model.Task;

import java.util.Collection;
import java.util.List;


/**
 * Contiene los elementos de la lista de tareas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Task
 */
public class TaskRepository {

    private static TaskRepository instance;
    private static TaskDao taskDao = TaskDao.getInstance();

    public static TaskRepository getInstance() {
        if (instance == null)
            instance = new TaskRepository();
        return instance;
    }

    public void addTaskCollection(Collection<Task> tasks) {
        taskDao.insertTasks(tasks);
    }

    public void upsertTask(Task task) {
        if (task != null)
            taskDao.insertTask(task);
    }

    public List<Task> getAllTasks() {
        return taskDao.getAllTasksOrderById();
    }

    public List<Task> findCurrentUserTaskList() {
        return taskDao.findCurrentUserTaskList();
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }

}
