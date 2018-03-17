package com.example.usuario.chronotasker.data.db.model;

import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Representa al usuario que accede a la app desde login
 * o desde token de sesión
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
@Entity
public class User {

    @Id
    long id;
    String name;
    String email;
    String password;
    @Backlink
    List<Task> tasks;

    public User(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
