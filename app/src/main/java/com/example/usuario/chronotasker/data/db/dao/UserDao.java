package com.example.usuario.chronotasker.data.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.chronotasker.data.db.ChronoTaskerContract;
import com.example.usuario.chronotasker.data.db.ChronoTaskerOpenHelper;


/**
 * Clase que maneja los cursores que recorren la tabla User de la BD
 * según la consulta indicada por cada función
 */
public class UserDao {

    public Cursor loadAll() {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(ChronoTaskerContract.UserEntries.TABLE_NAME,
                ChronoTaskerContract.UserEntries.ALL_COLUMNS,
                null, null, null, null,
                ChronoTaskerContract.UserEntries.DEFAULT_SORT, null
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return cursor;
    }
}
