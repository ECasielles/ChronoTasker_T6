package com.example.usuario.chronotasker.data.db;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Clase Helper que maneja la conexión a base de datos de la aplicación
 */
public class ChronoTaskerOpenHelper extends SQLiteOpenHelper {

    private static ChronoTaskerOpenHelper helper;
    private SQLiteDatabase sqLiteDatabase;

    public ChronoTaskerOpenHelper() {
        super(ChronoTaskerApplication.getContext(), ChronoTaskerContract.DATABASE_NAME,
                null, ChronoTaskerContract.DATABASE_VERSION);
    }

    //CREATION OF TABLES + INITIAL POPULATION
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creo en orden directo de referencias
        db.execSQL(ChronoTaskerContract.Task.CREATE_TABLE);
        db.execSQL(ChronoTaskerContract.Task.INSERT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if(oldVersion == 2)
            db.execSQL(ChronoTaskerContract.VERSION_1_2_UPDATE);
        if(oldVersion == 3)
            db.execSQL(ChronoTaskerContract.VERSION_2_3_UPDATE);*/
        //...
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    //CONFIGURACION ANTES DE CAMBIOS
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                db.setForeignKeyConstraintsEnabled(true);
            else
                db.execSQL("PRAGMA foreign_keys=1");
        }
    }

    public static ChronoTaskerOpenHelper getInstance() {
        if (helper == null)
            helper = new ChronoTaskerOpenHelper();
        return helper;
    }

    public void openDatabase() {
        sqLiteDatabase = getWritableDatabase();
    }

}
