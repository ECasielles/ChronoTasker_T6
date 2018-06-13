package com.example.usuario.chronotasker.data.model;

import com.example.usuario.chronotasker.mvvm.base.BaseModel;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Representa al usuario que accede a la app desde login
 * o desde token de sesión
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
@Entity
public class User extends BaseModel {

    @Id(assignable = true)
    public long id;
    @Backlink
    public ToMany<Task> tasks;

    private String name;
    private String email;
    private String password;
    private int imgProfile;

    public User() { }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public User(int id, String name, String email, String password, int imgProfile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imgProfile = imgProfile;
    }
    public User(long id, String name, String email, String password, int imgProfile, ToMany<Task> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imgProfile = imgProfile;
        this.tasks = tasks;
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
    public int getImgProfile() {
        return imgProfile;
    }
    public void setImgProfile(int imgProfile) {
        this.imgProfile = imgProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id || name.equals(user.name) && password.equals(user.password);
    }

}
