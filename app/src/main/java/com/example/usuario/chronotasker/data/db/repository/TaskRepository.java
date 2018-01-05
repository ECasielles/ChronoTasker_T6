package com.example.usuario.chronotasker.data.db.repository;

import com.example.usuario.chronotasker.data.db.model.Task;

import java.util.ArrayList;

/**
 * Contiene los elementos de la lista de tareas.
 */

public class TaskRepository extends ArrayList<Task> {

    private static TaskRepository taskRepository;

    private TaskRepository() {
        init();
    }

    private void init() {
        //TODO: Populate tasks
    }

    public static ArrayList<Task> getInstance() {
        if(taskRepository != null)
            taskRepository = new TaskRepository();
        return taskRepository;
    }

}
