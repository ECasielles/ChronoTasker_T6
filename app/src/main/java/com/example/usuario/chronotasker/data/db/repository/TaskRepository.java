package com.example.usuario.chronotasker.data.db.repository;

import android.support.annotation.Nullable;

import com.example.usuario.chronotasker.data.db.ChronoTaskerApplication;
import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.model.Task;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
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
    private static TaskRepository taskRepository;
    private static ArrayList<Task> tasks;

    private TaskRepository() {
        init();
    }

    private void init() {
        tasks = new ArrayList<>();
        addTask("No perder el bus", 0, new DateTime(), null, new Category(Category.CATEGORY_IMPORTANT),
                "Salida a las 8:05", null, 0, null, null);
        addTask("Recoger la ropa tendida", 0, new DateTime(), null, new Category(Category.CATEGORY_URGENT | Category.CATEGORY_IMPORTANT),
                null, null, 0, null, null);
        addTask("Siesta después de comer", 0, new DateTime(), null, new Category(Category.CATEGORY_INFORMAL),
                "De 26 minutos!!", null, 0, null, null);
        addTask("Partida de Lol", 0, new DateTime(), null, new Category(Category.CATEGORY_NONE),
                "Avisar a Rodri al móvil", null, 0, null, null);
        addTask("Llamar a mamá", 0, new DateTime(), null, new Category(Category.CATEGORY_URGENT),
                "Al móvil antiguo", null, 0, null, null);
        addTask("Llamar a papá", 0, new DateTime(), null, new Category(Category.CATEGORY_URGENT),
                null, null, 0, null, null);
    }

    public static TaskRepository getInstance() {
        if (taskRepository == null)
            taskRepository = new TaskRepository();
        return taskRepository;
    }

    public void addTask(String title, int iconId, @Nullable DateTime startDate,
                        @Nullable Date endDate, Category categoryFlags, @Nullable String description,
                        @Nullable String location, int alarmId, @Nullable Date repeat,
                        @Nullable String reminder) {
        tasks.add(new Task(
                        tasks.size(), title,
                        UserRepository.getUserId(ChronoTaskerApplication.getContext().getPreferencesHelper().getCurrentUserName()),
                        -1, startDate, null, categoryFlags, description, null,
                        -1, null, null
                )
        );
    }

    //TODO: Do not return archived Tasks
    public ArrayList<Task> getTasks() {
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

    public static void deleteTask(Task task) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == task.getId()) {
                iterator.remove();
                break;
            }
        }
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

}
