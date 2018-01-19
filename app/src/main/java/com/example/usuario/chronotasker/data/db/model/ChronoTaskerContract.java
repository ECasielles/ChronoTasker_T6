package com.example.usuario.chronotasker.data.db.model;

import android.provider.BaseColumns;

/**
 * Clase contrato necesaria para construir los comandos a Base de Datos.
 * No se puede heredar de ella ni instanciarla.
 */

public final class ChronoTaskerContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Task.db";

    private ChronoTaskerContract() {
    }

    public static class Task implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_TASK_ID = "taskId";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ICON_ID = "iconId";


    }

}
