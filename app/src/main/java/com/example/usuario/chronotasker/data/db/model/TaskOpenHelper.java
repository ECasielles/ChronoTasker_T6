package com.example.usuario.chronotasker.data.db.model;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.usuario.chronotasker.data.db.ChronoTaskerApplication;

/**
 * Clase Helper que maneja la conexión a base de datos de Task
 */
public class TaskOpenHelper extends SQLiteOpenHelper {

    private static TaskOpenHelper helper;
    private SQLiteDatabase sqLiteDatabase;

    public TaskOpenHelper() {
        super(ChronoTaskerApplication.getContext(), ChronoTaskerContract.DATABASE_NAME,
                null, ChronoTaskerContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    public static TaskOpenHelper getInstance() {
        if (helper == null)
            helper = new TaskOpenHelper();
        return helper;
    }

    public void openDatabase() {
        sqLiteDatabase = getWritableDatabase();
    }

}
