package com.example.usuario.chronotasker.data.db;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase Helper que maneja la conexión a base de datos de la aplicación.
 * Permite que varios hilos accedan de forma concurrente a ella.
 */
public class ChronoTaskerOpenHelper extends SQLiteOpenHelper {

    private static ChronoTaskerOpenHelper helper;
    private AtomicInteger openCounter = new AtomicInteger(0);
    private SQLiteDatabase sqLiteDatabase;

    public ChronoTaskerOpenHelper() {
        super(ChronoTaskerApplication.getContext(), ChronoTaskerContract.DATABASE_NAME,
                null, ChronoTaskerContract.DATABASE_VERSION);
    }

    public static synchronized ChronoTaskerOpenHelper getInstance() {
        if (helper == null)
            helper = new ChronoTaskerOpenHelper();
        return helper;
    }

    //CREATION OF TABLES + INITIAL POPULATION
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creo en orden directo de referencias
        db.execSQL(ChronoTaskerContract.UserEntries.CREATE_TABLE);
        db.execSQL(ChronoTaskerContract.TaskEntries.CREATE_TABLE);
        db.execSQL(ChronoTaskerContract.UserEntries.INSERT_VALUES);
        db.execSQL(ChronoTaskerContract.TaskEntries.INSERT_VALUES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borro en orden inverso de referencias
        db.execSQL(ChronoTaskerContract.TaskEntries.DROP_TABLE);
        db.execSQL(ChronoTaskerContract.UserEntries.DROP_TABLE);
        onCreate(db);
    }

    //CONFIGURACION ANTES DE CAMBIOS
    @Override
    public void onConfigure(SQLiteDatabase db) {
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                db.setForeignKeyConstraintsEnabled(true);
            else
                db.execSQL("PRAGMA foreign_keys=1");
        }
        super.onConfigure(db);
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (openCounter.incrementAndGet() == 1)
            sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase;
    }

    public synchronized void closeDatabase() {
        if (openCounter.decrementAndGet() == 0)
            sqLiteDatabase.close();
    }

}
