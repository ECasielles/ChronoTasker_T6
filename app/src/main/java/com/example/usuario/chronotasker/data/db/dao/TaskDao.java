package com.example.usuario.chronotasker.data.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.chronotasker.data.db.ChronoTaskerContract;
import com.example.usuario.chronotasker.data.db.ChronoTaskerOpenHelper;


/**
 * Clase que maneja los cursores que recorren la tabla Task de la BD
 * según la consulta indicada por cada función
 */
public class TaskDao {

    public Cursor loadAll() {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(ChronoTaskerContract.TaskEntries.TABLE_NAME,
                ChronoTaskerContract.TaskEntries.ALL_COLUMNS,
                null, null, null, null,
                ChronoTaskerContract.TaskEntries.DEFAULT_SORT, null
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return cursor;
    }

    //TODO: Implementar los métodos de UserRepository en Task.
    //TODO: ¿Es necesario guardar el ID en el modelo?

}
