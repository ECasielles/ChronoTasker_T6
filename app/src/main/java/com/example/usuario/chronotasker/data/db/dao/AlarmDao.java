package com.example.usuario.chronotasker.data.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.chronotasker.data.db.ChronoTaskerContract;
import com.example.usuario.chronotasker.data.db.ChronoTaskerOpenHelper;
import com.example.usuario.chronotasker.data.db.model.Alarm;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by icenri on 2/26/18.
 */

public class AlarmDao {

    public ArrayList<Alarm> loadAll() {
        ArrayList<Alarm> alarms = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(
                ChronoTaskerContract.AlarmEntries.TABLE_NAME,
                ChronoTaskerContract.AlarmEntries.ALL_COLUMNS,
                null,
                null,
                null,
                null,
                ChronoTaskerContract.AlarmEntries.ORDER_BY_ID,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                alarms.add(new Alarm(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        new DateTime(cursor.getString(3))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return alarms;
    }

    public long save(Alarm alarm) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        long id = sqLiteDatabase.insert(
                ChronoTaskerContract.AlarmEntries.TABLE_NAME,
                null,
                createContent(alarm)
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return id;
    }

    public long update(Alarm alarm) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        String whereClause = ChronoTaskerContract.AlarmEntries.WHERE_ID;
        String[] whereArgs = new String[]{String.valueOf(alarm.getId())};
        int updatedRows = sqLiteDatabase.update(
                ChronoTaskerContract.AlarmEntries.TABLE_NAME,
                createContent(alarm),
                whereClause,
                whereArgs
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return updatedRows;
    }

    public int delete(Alarm alarm) {
        SQLiteDatabase sqLiteDatabase = ChronoTaskerOpenHelper.getInstance().openDatabase();
        String whereClause = ChronoTaskerContract.AlarmEntries.WHERE_ID;
        String[] whereArgs = new String[]{String.valueOf(alarm.getId())};
        int deletedRows = sqLiteDatabase.delete(
                ChronoTaskerContract.AlarmEntries.TABLE_NAME,
                whereClause,
                whereArgs
        );
        ChronoTaskerOpenHelper.getInstance().closeDatabase();
        return deletedRows;
    }

    private ContentValues createContent(Alarm alarm) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChronoTaskerContract.AlarmEntries.COLUMN_TITLE, alarm.getTitle());
        contentValues.put(ChronoTaskerContract.AlarmEntries.COLUMN_CONTENT, alarm.getContent());
        contentValues.put(ChronoTaskerContract.AlarmEntries.COLUMN_TIME, alarm.getTime().toString());
        return contentValues;
    }
}
