package com.example.usuario.chronotasker.data.db.model;

import android.media.Image;

import java.util.Date;

/**
 * Representa al usuario que accede a la app desde login
 * o desde token de sesión
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class User {

    String name;
    String password;
    String email;
    String profileName;
    Date birthDate;
    String storeDataPath;
    Image profileImg;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
