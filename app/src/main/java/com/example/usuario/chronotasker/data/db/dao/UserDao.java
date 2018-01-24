package com.example.usuario.chronotasker.data.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.chronotasker.data.db.ChronoTaskerContract;
import com.example.usuario.chronotasker.data.db.ChronoTaskerOpenHelper;
import com.example.usuario.chronotasker.data.db.model.User;

import java.util.ArrayList;


/**
 * Clase que maneja los cursores que recorren la tabla User de la BD
 * según la consulta indicada por cada función
 */
public class UserDao {

    public ArrayList<User> users;

    public UserDao() {
        this.users = new ArrayList<>();
    }

    public ArrayList<User> loadAll() {
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
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_NAME, name);
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_EMAIL, email);
        contentValues.put(ChronoTaskerContract.UserEntries.COLUMN_PASSWORD, password);
        return sqLiteDatabase.insert(
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                null,
                contentValues
        );
    }

    /**
     * Devuelve true si ha borrado el usuario de la BD.
     *
     * @param name Nombre del usuario.
     * @return True si lo ha borrado. Falso si no.
     */
    public boolean delete(String name) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        return sqLiteDatabase.delete(
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                ChronoTaskerContract.UserEntries.WHERE_NAME,
                new String[]{name}
        ) > 0;
    }

    /**
     * Comprueba si el usuario existe en la BD.
     *
     * @param name     Nombre del usuario
     * @param password Contraseña del usuario
     * @return True si el usuario existe y false si no.
     */
    public boolean exists(String name, String password) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        return DatabaseUtils.queryNumEntries(sqLiteDatabase,
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                ChronoTaskerContract.UserEntries.WHERE_NAME_AND_PASSWORD,
                new String[]{name, password}
        ) > 0;
    }


}
