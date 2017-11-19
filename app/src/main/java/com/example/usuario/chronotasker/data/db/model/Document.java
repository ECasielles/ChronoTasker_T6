package com.example.usuario.chronotasker.data.db.model;

import java.util.Date;

/**
 * Representa un document creado por el usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Document {
    int id;
    String title;
    int iconId;
    int ownerId;
    String sessionDataPath;
    Date creationDate;
    Category categoryFlags;
    DocumentType documentType;
    String filePath;
}
