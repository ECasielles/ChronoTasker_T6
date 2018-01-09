package com.example.usuario.chronotasker.data.db.repository;

import com.example.usuario.chronotasker.data.db.model.Task;
import com.example.usuario.chronotasker.data.db.model.User;
import com.example.usuario.chronotasker.ui.ChronoTaskerApplication;

import java.util.ArrayList;
import java.util.Date;

/**
 * Contiene los elementos de la lista de tareas.
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

    //TODO: Add, Edit, Delete Tasks
    public static boolean addTask(String title, Date startDate, Date endDate, String email, String password) {
        boolean newTask = true;
        for (Task temp : tasks) {
            if (temp.getName().equals(name) || temp.getEmail().equals(email)) {
                newTask = false;
                break;
            }
        }
        if (newTask) {
            Task task = new Task(
                    tasks.size(),
                    UserRepository.getUserId(ChronoTaskerApplication.getContext().getPreferencesHelper().getCurrentUserName()),
                    title,
                    startDate,;
        }
        users.add(new User(users.size(), name, email, password));
        return newTask;
    }

    public static boolean editTask(String name, String email, String password) {
        boolean isNewUser = true;
        for (User temp : users) {
            if (temp.getName().equals(name) || temp.getEmail().equals(email)) {
                isNewUser = false;
                break;
            }
        }
        if (isNewUser)
            users.add(new User(users.size(), name, email, password));
        return isNewUser;
    }

    public static boolean deleteTask(String name, String email, String password) {
        boolean isNewUser = true;
        for (User temp : users) {
            if (temp.getName().equals(name) || temp.getEmail().equals(email)) {
                isNewUser = false;
                break;
            }
        }
        if (isNewUser)
            users.add(new User(users.size(), name, email, password));
        return isNewUser;
    }
}
