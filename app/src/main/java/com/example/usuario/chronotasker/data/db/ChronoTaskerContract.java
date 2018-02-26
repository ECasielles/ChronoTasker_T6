package com.example.usuario.chronotasker.data.db;

import android.provider.BaseColumns;

import com.example.usuario.chronotasker.data.db.model.Category;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Clase contrato necesaria para construir los comandos a Base de Datos.
 * No se puede heredar de ella ni instanciarla.

 * CHANGELOG:
 *
 * 2018/01/20 - 1: El atributo reminder de la entidad TASK pasa a guardar los objetos Alarm
 * como un texto en formato JSON.
 */
public final class ChronoTaskerContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "chronotasker.db";

    private ChronoTaskerContract() {
    }

    public static class UserEntries implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";

        public static final String[] ALL_COLUMNS = {
                BaseColumns._ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        };
        public static final String WHERE_NAME_AND_PASSWORD = String.format(
                "%s = ? AND %s = ?",
                COLUMN_NAME,
                COLUMN_PASSWORD
        );

        public static final String DEFAULT_SORT = BaseColumns._ID;

        public static final String DROP_TABLE = String.format(
                "DROP TABLE IF EXISTS %s", TABLE_NAME);

        public static final String CREATE_TABLE = String.format(
                "CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL UNIQUE, " +
                        "%s TEXT NOT NULL UNIQUE, " +
                        "%s TEXT NOT NULL)",
                TABLE_NAME,
                BaseColumns._ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        );

        public static final String INSERT_VALUES = String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES ",
                TABLE_NAME,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        ) + String.format("('%s', '%s', '%s'),",
                "Enrique", "enrique@gmail.com", "Pwd123"
        ) + String.format("('%s', '%s', '%s')",
                "Lourdes", "lourdes@gmail.com", "Pwd123"
        );

    }

    public static class TaskEntries implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USER = "user";
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
                BaseColumns._ID,
                COLUMN_TITLE,
                COLUMN_USER,
                COLUMN_ICON_ID,
                COLUMN_START_DATE,
                COLUMN_END_DATE,
                COLUMN_CATEGORY_FLAGS,
                COLUMN_DESCRIPTION,
                COLUMN_LOCATION,
                COLUMN_ALARM_ID,
                COLUMN_REPEAT,
                COLUMN_REMINDER
        };

        public static final String REFERENCES_USER_ID = String.format(
                "FOREIGN KEY (%s) REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT",
                COLUMN_USER, UserEntries.TABLE_NAME, BaseColumns._ID
        );

        public static final String WHERE_ID = String.format(
                "%s = ?",
                BaseColumns._ID
        );
        public static final String WHERE_USER_AND_CATEGORY_NOT = String.format(
                "%s = ? AND %s != ?",
                COLUMN_USER,
                COLUMN_CATEGORY_FLAGS
        );

        public static final String ORDER_BY_ID = BaseColumns._ID;

        public static final String DROP_TABLE = String.format(
                "DROP TABLE IF EXISTS %s",
                TABLE_NAME
        );

        public static final String CREATE_TABLE = String.format(
                "CREATE TABLE %s (" +
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
                COLUMN_TITLE,
                COLUMN_USER,
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

        public static final String INSERT_VALUES = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES ",
                TABLE_NAME,
                COLUMN_TITLE,
                COLUMN_USER,
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
                new Category(Category.CATEGORY_IMPORTANT |
                        Category.CATEGORY_DEFAULT).getFlags(),
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
                new Category(Category.CATEGORY_URGENT |
                        Category.CATEGORY_IMPORTANT | Category.CATEGORY_DEFAULT).getFlags(),
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
                new Category(Category.CATEGORY_INFORMAL | Category.CATEGORY_DEFAULT).getFlags(),
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
                new Category(Category.CATEGORY_ARCHIVED).getFlags(),
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
                new Category(Category.CATEGORY_URGENT | Category.CATEGORY_DEFAULT).getFlags(),
                "Al móvil antiguo",
                "",
                0,
                "",
                ""
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s'), ",
                "Llamar a papá",
                1,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_URGENT | Category.CATEGORY_DEFAULT).getFlags(),
                "",
                "",
                0,
                "",
                ""
        ) + String.format("('%s', %s, %s, '%s', '%s', %s, '%s', '%s', %s, '%s', '%s')",
                "Examen malvado",
                2,
                0,
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new DateTime().toString(ISODateTimeFormat.dateHourMinute()),
                new Category(Category.CATEGORY_URGENT |
                        Category.CATEGORY_IMPORTANT | Category.CATEGORY_DEFAULT).getFlags(),
                "",
                "",
                0,
                "",
                ""
        );

    }

    public static class AlarmEntries implements BaseColumns {
        public static final String TABLE_NAME = "alarm";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_TIME = "time";

        public static final String[] ALL_COLUMNS = {
                BaseColumns._ID,
                COLUMN_TITLE,
                COLUMN_CONTENT,
                COLUMN_TIME
        };

        public static final String WHERE_ID = String.format(
                "%s = ?",
                BaseColumns._ID
        );

        public static final String ORDER_BY_ID = BaseColumns._ID;

        public static final String DROP_TABLE = String.format(
                "DROP TABLE IF EXISTS %s",
                TABLE_NAME
        );

        public static final String CREATE_TABLE = String.format(
                "CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL)",
                TABLE_NAME,
                BaseColumns._ID,
                COLUMN_TITLE,
                COLUMN_CONTENT,
                COLUMN_TIME
        );

        public static final String INSERT_VALUES = String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES ",
                TABLE_NAME,
                COLUMN_TITLE,
                COLUMN_CONTENT,
                COLUMN_TIME
        ) + String.format("('%s', '%s', '%s')",
                "Examen malvado",
                "Cuando suene la alarma. ¡Todos suspensos!",
                new DateTime().plusMinutes(1).toString(ISODateTimeFormat.dateHourMinute())
        );

    }

}
