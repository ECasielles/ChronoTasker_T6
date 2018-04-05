package com.example.usuario.chronotasker.data.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.usuario.chronotasker.App;
import com.example.usuario.chronotasker.data.model.Category;
import com.example.usuario.chronotasker.data.model.Task;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;

/**
 * Clase que maneja los cursores que recorren la tabla Task de la BD
 * según la consulta indicada por cada función
 */
public class TaskDao {

    public ArrayList<Task> loadAllActive() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        String whereClause = ChronoTaskerContract.TaskEntries.WHERE_USER_AND_CATEGORY_NOT;
        String[] whereArgs = new String[]{
                String.valueOf(App.getApp().getmPreferencesHelper().getCurrentUserId()),
                String.valueOf(Category.CATEGORY_ARCHIVED)
        };
        Cursor cursor = sqLiteDatabase.query(
                ChronoTaskerContract.TaskEntries.TABLE_NAME,
                ChronoTaskerContract.TaskEntries.ALL_COLUMNS,
                whereClause,
                whereArgs,
                null,
                null,
                ChronoTaskerContract.TaskEntries.ORDER_BY_ID,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                tasks.add(new Task(
                        cursor.getLong(0),
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
     * @return Id del tipo long de la Tarea en la BD o -1 si hubo algún error.
     */
    public long save(Task task) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        long id = sqLiteDatabase.insert(
                ChronoTaskerContract.TaskEntries.TABLE_NAME,
                null,
                createContent(task)
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return id;
    }

    public int update(Task task) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        String whereClause = ChronoTaskerContract.TaskEntries.WHERE_ID;
        String[] whereArgs = new String[]{String.valueOf(task.getId())};
        int updatedRows = sqLiteDatabase.update(
                ChronoTaskerContract.TaskEntries.TABLE_NAME,
                createContent(task),
                whereClause,
                whereArgs
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return updatedRows;
    }

    public int delete(Task task) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        String whereClause = ChronoTaskerContract.TaskEntries.WHERE_ID;
        String[] whereArgs = new String[]{String.valueOf(task.getId())};
        int deletedRows = sqLiteDatabase.delete(
                ChronoTaskerContract.TaskEntries.TABLE_NAME,
                whereClause,
                whereArgs
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return deletedRows;
    }

    @NonNull
    private ContentValues createContent(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_TITLE, task.getTitle());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_USER, task.getUserId());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_ICON_ID, task.getIconId());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_START_DATE, task.getStartDate().toString());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_END_DATE, task.getEndDate().toString());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_CATEGORY_FLAGS, String.valueOf(task.getCategory().getFlags()));
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_DESCRIPTION, task.getDescription());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_LOCATION, task.getLocation());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_ALARM_ID, String.valueOf(task.getAlarmId()));
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_REPEAT, task.getRepetition().toString());
        contentValues.put(ChronoTaskerContract.TaskEntries.COLUMN_REMINDER, task.getReminder());
        return contentValues;
    }

}
