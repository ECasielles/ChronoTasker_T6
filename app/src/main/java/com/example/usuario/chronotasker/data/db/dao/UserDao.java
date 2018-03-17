package com.example.usuario.chronotasker.data.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.usuario.chronotasker.data.db.model.User;

/**
 * Clase que maneja los cursores que recorren la tabla User de la BD
 * según la consulta indicada por cada función.
 */
public class UserDao {

    /**
     * Inserta teniendo en cuenta las restricciones como clave única o ajena.
     *
     * @return Devuelve el id de la fila modificada.
     */
    public long save(User user) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        long rows = 0;
        try {
            rows = sqLiteDatabase.insertWithOnConflict(
                    ChronoTaskerContract.UserEntries.TABLE_NAME,
                    null,
                    createContent(user),
                    SQLiteDatabase.CONFLICT_ABORT
            );
        } catch (Exception e) {
            Log.d("UserDao", e.getMessage());
        }
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return rows;
    }

    /**
     * Comprueba si el usuario existe en la BD devolviendo su id ó -1 si no existe.
     */
    public int search(User user) {
        int id = -1;
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        String whereClause = ChronoTaskerContract.UserEntries.WHERE_NAME_AND_PASSWORD;
        String[] whereArgs = new String[]{user.getName(), user.getPassword()};
        Cursor cursor = sqLiteDatabase.query(
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                ChronoTaskerContract.UserEntries.ALL_COLUMNS,
                whereClause,
                whereArgs,
                null,
                null,
                ChronoTaskerContract.UserEntries.DEFAULT_SORT,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return id;
    }

    private ContentValues createContent(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_NAME, user.getName());
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_EMAIL, user.getEmail());
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_PASSWORD, user.getPassword());
        return contentValues;
    }

}
