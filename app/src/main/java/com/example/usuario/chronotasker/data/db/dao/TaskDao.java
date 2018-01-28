package com.example.usuario.chronotasker.data.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.usuario.chronotasker.data.db.ChronoTaskerContract;
import com.example.usuario.chronotasker.data.db.ChronoTaskerOpenHelper;
import com.example.usuario.chronotasker.data.db.model.Category;
import com.example.usuario.chronotasker.data.db.model.Task;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;

/**
 * Clase que maneja los cursores que recorren la tabla Task de la BD
 * según la consulta indicada por cada función
 */
public class TaskDao {

    public ArrayList<Task> loadAll() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(ChronoTaskerContract.TaskEntries.TABLE_NAME,
                ChronoTaskerContract.TaskEntries.ALL_COLUMNS,
                null, null, null, null,
                ChronoTaskerContract.TaskEntries.DEFAULT_SORT, null
        );
        if (cursor.moveToFirst()) {
            do {
                tasks.add(new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        new DateTime(cursor.getString(4)),
                        new DateTime(cursor.getString(5)),
                        new Category(cursor.getInt(6)),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        new Period(cursor.getString(10).equals("") ? null : new Period(cursor.getString(10)).toString()),
                        cursor.getString(11)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return tasks;
    }

    public ArrayList<Task> loadAllActive() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(ChronoTaskerContract.TaskEntries.TABLE_NAME,
                ChronoTaskerContract.TaskEntries.ALL_COLUMNS,
                ChronoTaskerContract.TaskEntries.WHERE_CATEGORY_NOT,
                new String[]{String.valueOf(Category.CATEGORY_ARCHIVED)},
                null,
                null,
                ChronoTaskerContract.TaskEntries.DEFAULT_SORT,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                tasks.add(new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        new DateTime(cursor.getString(4)),
                        new DateTime(cursor.getString(5)),
                        new Category(cursor.getInt(6)),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        new Period(cursor.getString(10).equals("") ? null : new Period(cursor.getString(10)).toString()),
                        cursor.getString(11)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return tasks;
    }

    /**
     * Devuelve el id del elemento añadido a la BD.
     *
     * @param title         Título
     * @param iconId        Id del icono
     * @param startDate     Fecha de inicio
     * @param endDate       Fecha de fin
     * @param categoryFlags Categoría
     * @param description   Descripción
     * @param location      Ubicación
     * @param alarmId       Id de la alarma
     * @param repeat        Fecha de repetición
     * @param reminder      Objeto JSON con la configuración de recordatorios
     * @return Id del tipo long de la Tarea en la BD o -1 si hubo algún error.
     */
    public long save(String title, int iconId, DateTime startDate, DateTime endDate,
                     Category categoryFlags, String description, String location,
                     int alarmId, DateTime repeat, String reminder) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        long id = sqLiteDatabase.insert(
                ChronoTaskerContract.UserEntries.TABLE_NAME,
                null,
                createContent(title, iconId, startDate, endDate, categoryFlags, description,
                        location, alarmId, repeat, reminder)
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return id;
    }

    public int update(int id, String title, int iconId, DateTime startDate,
                      DateTime endDate, Category categoryFlags, String description,
                      String location, int alarmId, DateTime repeat, String reminder) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        String[] whereArgs = new String[]{String.valueOf(id)};
        int updatedRows = sqLiteDatabase.update(
                ChronoTaskerContract.TaskEntries.TABLE_NAME,
                createContent(
                        title, iconId, startDate, endDate, categoryFlags,
                        description, location, alarmId, repeat, reminder
                ),
                ChronoTaskerContract.TaskEntries.WHERE_ID,
                whereArgs
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return updatedRows;
    }

    /**
     * Devuelve true si ha borrado la tarea de la BD.
     *
     * @return True si la ha borrado. Falso si no.
     */
    public int delete(int id) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        int deletedRows = sqLiteDatabase.delete(
                ChronoTaskerContract.TaskEntries.TABLE_NAME,
                ChronoTaskerContract.TaskEntries.WHERE_ID,
                new String[]{String.valueOf(id)}
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return deletedRows;
    }

    /**
     * Comprueba si la tarea existe en la BD.
     *
     * @return True si la tarea existe y false si no.
     */
    public long exists(int id, int ownerId) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        long numEntries = DatabaseUtils.queryNumEntries(sqLiteDatabase,
                ChronoTaskerContract.TaskEntries.TABLE_NAME,
                ChronoTaskerContract.TaskEntries.WHERE_ID_AND_OWNER,
                new String[]{String.valueOf(id), String.valueOf(ownerId)}
        );
        return numEntries;
    }

    @NonNull
    private ContentValues createContent(String title, int iconId, DateTime startDate, DateTime endDate,
                                        Category categoryFlags, String description, String location,
                                        int alarmId, DateTime repeat, String reminder) {
        //ContentValues funciona como un mapa
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_TITLE, title);
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_ICON_ID, iconId);
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_START_DATE, startDate.toString());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_END_DATE, endDate.toString());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_CATEGORY_FLAGS, String.valueOf(categoryFlags));
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_DESCRIPTION, description);
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_LOCATION, location);
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_ALARM_ID, String.valueOf(alarmId));
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_REPEAT, repeat.toString());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_REMINDER, reminder);
        return contentValues;
    }

}
