package com.example.usuario.chronotasker.data.db.model;

/**
 * Representa las categorías que caracterizan las tareas.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Category {
    int id;
    String title;
    //value flags: 1,2,4,8,16...
    int value;
}
