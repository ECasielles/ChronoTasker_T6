package com.example.usuario.chronotasker.data.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.usuario.chronotasker.data.db.ChronoTaskerContract;
import com.example.usuario.chronotasker.data.db.ChronoTaskerOpenHelper;
import com.example.usuario.chronotasker.data.db.model.User;

import java.util.ArrayList;

/**
 * Clase que maneja los cursores que recorren la tabla User de la BD
 * según la consulta indicada por cada función
 */
public class UserDao {

    public ArrayList<User> loadAll() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(ChronoTaskerContract.UserEntries.TABLE_NAME,
                ChronoTaskerContract.UserEntries.ALL_COLUMNS,
                null, null, null, null,
                ChronoTaskerContract.UserEntries.DEFAULT_SORT, null
        );
        //El cursor siempre se coloca antes del primer elemento.
        if (cursor.moveToFirst())
            do {
                //Acceder a las columnas en el mismo orden
                User user = new User(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                users.add(user);
            } while (cursor.moveToNext());
        cursor.close();
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return users;
    }

    /**
     * Devuelve el id del elemento añadido a la BD.
     *
     * @param name     Nombre del usuario
     * @param email    Email del usuario
     * @param password Contraseña del usuario
     * @return Id del tipo long del usuario en la BD o -1 si hubo algún error.
     */
    public long save(String name, String email, String password) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        long updatedRows = sqLiteDatabase.insert(
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                null,
                createContent(name, email, password)
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return updatedRows;
    }

    /**
     * Elimina un usuario de la BD.
     *
     * @param id Id del usuario.
     * @return True si lo ha borrado. Falso si no.
     */
    public int delete(int id) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        int deletedRows = sqLiteDatabase.delete(
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                ChronoTaskerContract.UserEntries.WHERE_ID,
                new String[]{String.valueOf(id)}
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return deletedRows;
    }

    /**
     * Comprueba si el usuario existe en la BD.
     *
     * @param name     Nombre del usuario
     * @param password Contraseña del usuario
     * @return True si el usuario existe y false si no.
     */
    public long exists(String name, String password) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        long numEntries = DatabaseUtils.queryNumEntries(sqLiteDatabase,
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                ChronoTaskerContract.UserEntries.WHERE_NAME_AND_PASSWORD,
                new String[]{name, password}
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return numEntries;
    }

    @NonNull
    private ContentValues createContent(String name, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_NAME, name);
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_EMAIL, email);
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_PASSWORD, password);
        return contentValues;
    }

}
