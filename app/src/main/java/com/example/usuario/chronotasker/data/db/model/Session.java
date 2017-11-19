package com.example.usuario.chronotasker.data.db.model;

import java.util.Date;

/**
 * Representa una sesión creada por el usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Session {
    int id;
    String title;
    int iconId;
    int ownerId;
    String sessionDataPath;
    Date repeat;
}
