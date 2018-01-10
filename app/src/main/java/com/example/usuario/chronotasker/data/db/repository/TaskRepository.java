package com.example.usuario.chronotasker.data.db.repository;

import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.ui.ChronoTaskerApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Contiene los elementos de la lista de tareas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see Task
 */

public class TaskRepository {

    public static TaskRepository taskRepository;
    private static ArrayList<Task> tasks;

    private TaskRepository() {
        init();
    }

    private void init() {
        //TODO: Populate tasks
    }

    public static TaskRepository getInstance() {
        if(taskRepository != null)
            taskRepository = new TaskRepository();
        return taskRepository;
    }

    public static boolean addTask(int id, String title, int ownerId, int iconId, @Nullable Date startDate,
                                  @Nullable Date endDate, Category categoryFlags, @Nullable String description,
                                  @Nullable String location, int alarmId, @Nullable Date repeat,
                                  @Nullable String reminder) {
        boolean newTask = true;
        for (Task temp : tasks) {
            if (temp.getId() == id) {
                newTask = false;
                break;
            }
        }
        if (newTask) {
            tasks.add(new Task(tasks.size(), title,
                    UserRepository.getUserId(ChronoTaskerApplication.getContext().getPreferencesHelper().getCurrentUserName()),
                    -1, startDate, null, categoryFlags, null, null,
                    -1, null, null)
            );
        }
        return newTask;
    }

    //TODO: Add more fields for edition
    public static void editTask(Task task, String title) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task oldTask = iterator.next();
            if (oldTask.getId() == task.getId()) {
                oldTask.setTitle(title);
                break;
            }
        }
    }

    public static boolean deleteTask(Task task) {
        boolean taskDeleted = false;
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == task.getId()) {
                iterator.remove();
                taskDeleted = true;
                break;
            }
        }
        return taskDeleted;
    }

}
