package com.example.usuario.chronotasker.data.db;

import android.provider.BaseColumns;

import com.example.usuario.chronotasker.data.db.model.Category;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Clase contrato necesaria para construir los comandos a Base de Datos.
 * No se puede heredar de ella ni instanciarla.
 */

/**
 * CHANGELOG:
 * <p>
 * 2018/01/20 - 1: El atributo reminder de la entidad TASK pasa a guardar los objetos Alarm
 * como un texto en formato JSON.
 */
public final class ChronoTaskerContract {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "TaskEntries.db";

    private ChronoTaskerContract() {
    }

    public static class UserEntries implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";

        public static final String[] ALL_COLUMNS = {
                BaseColumns._ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PASSWORD
        };

        public static final String DEFAULT_SORT = BaseColumns._ID;

        public static final String SQL_DROP_TABLE = String.format(
                "DROP TABLE IF EXISTS %s", TABLE_NAME);

        public static final String SQL_CREATE_TABLE = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL)",
                TABLE_NAME,
                BaseColumns._ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        );

        public static final String SQL_INSERT_VALUES = String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES ",
                TABLE_NAME,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        ) + String.format("('%s', '%s', '%s'),",
                "Enrique", "Pwd123", "enrique@gmail.com"
        ) + String.format("('%s', '%s', '%s')",
                "Lourdes", "Pwd123", "lourdes@gmail.com"
        );

    }

    public static class TaskEntries implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_OWNER_ID = "ownerID";
        public static final String COLUMN_ICON_ID = "iconId";
        public static final String COLUMN_START_DATE = "startDate";
        public static final String COLUMN_END_DATE = "endDate";
        public static final String COLUMN_CATEGORY_FLAGS = "categoryFlags";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_ALARM_ID = "alarmId";
        public static final String COLUMN_REPEAT = "repeat";
        public static final String COLUMN_REMINDER = "reminder";

        public static final String[] ALL_COLUMNS = {
                BaseColumns._ID, COLUMN_NAME, COLUMN_OWNER_ID, COLUMN_ICON_ID,
                COLUMN_START_DATE, COLUMN_END_DATE, COLUMN_CATEGORY_FLAGS, COLUMN_DESCRIPTION,
                COLUMN_LOCATION, COLUMN_ALARM_ID, COLUMN_REPEAT, COLUMN_REMINDER
        };

        public static final String REFERENCES_USER_ID = String.format(
                "FOREIGN KEY (%s) REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT",
                COLUMN_OWNER_ID, UserEntries.TABLE_NAME, BaseColumns._ID
        );

        public static final String DEFAULT_SORT = BaseColumns._ID;

        public static final String SQL_DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);

        public static final String SQL_CREATE_TABLE = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s)",
                TABLE_NAME,
                BaseColumns._ID,
                COLUMN_NAME,
                COLUMN_OWNER_ID,
                COLUMN_ICON_ID,
                COLUMN_START_DATE,
                COLUMN_END_DATE,
                COLUMN_CATEGORY_FLAGS,
                COLUMN_DESCRIPTION,
                COLUMN_LOCATION,
                COLUMN_ALARM_ID,
                COLUMN_REPEAT,
                COLUMN_REMINDER,
                REFERENCES_USER_ID
        );

        public static final String SQL_INSERT_VALUES = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES ",
                TABLE_NAME,
                COLUMN_NAME,
                COLUMN_OWNER_ID,
                COLUMN_ICON_ID,
                COLUMN_START_DATE,
                COLUMN_END_DATE,
                COLUMN_CATEGORY_FLAGS,
                COLUMN_DESCRIPTION,
                COLUMN_LOCATION,
                COLUMN_ALARM_ID,
                COLUMN_REPEAT,
                COLUMN_REMINDER
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s'), ",
                "No perder el bus",
                1,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_IMPORTANT),
                "Salida a las 8:05",
                "",
                0,
                "",
                ""
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s'), ",
                "Recoger la ropa tendida",
                1,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_URGENT | Category.CATEGORY_IMPORTANT),
                "",
                "",
                0,
                "",
                ""
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s'), ",
                "Siesta después de comer",
                1,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_INFORMAL),
                "",
                "",
                0,
                "",
                ""
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s'), ",
                "Partida de Lol",
                1,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_NONE),
                "Avisar a Rodri al móvil",
                "",
                0,
                "",
                ""
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s'), ",
                "Llamar a mamá",
                1,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_URGENT),
                "Al móvil antiguo",
                "",
                0,
                "",
                ""
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s')",
                "Llamar a papá",
                1,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_URGENT),
                "",
                "",
                0,
                "",
                ""
        );

    }

}
