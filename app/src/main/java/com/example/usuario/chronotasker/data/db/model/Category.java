package com.example.usuario.chronotasker.data.db.model;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.ChronoTaskerApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Representa las categorías que caracterizan las tareas.
 * Se comportan como flags y el IDE las reconoce de forma
 * que avisa al programador si no le da un valor de entre
 * los indicados.
 * Cada categoría tiene una prioridad múltiplo de 2, siendo
 * 1 la categoría por defecto. Al ser flags, la prioridad total
 * se obtiene sumando las prioridades de todas las categorías.
 * Cuando se archiva una tarea, su categoría pasa a ser 0.
 * Por defecto una categoría siempre es normal hasta que es archivada.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Category implements Comparable {

    public static final int ARCHIVED_NAME = R.string.category_archived;
    public static final int INFORMAL_NAME = R.string.category_informal;
    public static final int DEFAULT_NAME = R.string.category_default;
    public static final int IMPORTANT_NAME = R.string.category_important;
    public static final int URGENT_NAME = R.string.category_urgent;
    public static final int CATEGORY_ARCHIVED = 0;
    public static final int CATEGORY_INFORMAL = 1;
    public static final int CATEGORY_DEFAULT = 2;
    public static final int CATEGORY_IMPORTANT = 4;
    public static final int CATEGORY_URGENT = 8;
    /**
     * La unión de todos los flags es 2^5 - 1
     */
    public static final int MAX_FLAGS = 31;

    //PARAMETROS
    private int priority;
    private int flags;

    /**
     * Pide las opciones que se han declarado arriba
     * @param flag Unión de múltiples flags para categoría compuesta
     */
    public Category(@DisplayOptions int flag) {
        if (flag > MAX_FLAGS)
            throw new IllegalArgumentException(ChronoTaskerApplication.getContext().getResources().getString(R.string.error_category_argument));
        this.flags = flag;
        setPriority();
    }

    public Category() {
        setDefault();
        setPriority();
    }

    public int getPriority() {
        return priority;
    }

    public int getFlags() {
        return flags;
    }

    /**
     * Las categorías permiten ordenar por prioridad. Cada categoría tiene una
     * prioridad propia, múltiplo de 2, de forma que cada combinación de categorías
     * tiene una prioridad única.
     *
     * @return Prioridad combinada de las categorías.
     */
    private void setPriority() {
        int priority = 0;
        if (isInformal())
            priority = 1;
        if (isDefault())
            priority += 2;
        if (isImportant())
            priority += 4;
        if (isUrgent())
            priority += 8;
        this.priority = priority;
    }

    /**
     * Devuelven booleano en función de si la categoría contiene
     * o no el flag por el que se pregunta
     */
    public boolean isArchived() {
        return CATEGORY_ARCHIVED == flags;
    }
    public boolean isInformal() {
        return (flags | CATEGORY_INFORMAL) == flags;
    }
    public boolean isDefault() {
        return (flags | CATEGORY_DEFAULT) == flags;
    }
    public boolean isImportant() {
        return (flags | CATEGORY_IMPORTANT) == flags;
    }
    public boolean isUrgent() {
        return (flags | CATEGORY_URGENT) == flags;
    }

    /**
     * Permiten editar los flags de la categoría.
     */
    public void setArchived() {
        flags = CATEGORY_ARCHIVED;
        priority = 0;
    }
    public void setInformal() {
        flags = flags | CATEGORY_INFORMAL;
        setPriority();
    }
    public void setDefault() {
        flags = flags | CATEGORY_DEFAULT;
        setPriority();
    }
    public void setImportant() {
        flags = flags | CATEGORY_IMPORTANT;
        setPriority();
    }
    public void setUrgent() {
        flags = flags | CATEGORY_URGENT;
        setPriority();
    }
    public void unSetInformal() {
        flags = flags ^ CATEGORY_INFORMAL;
        setPriority();
    }
    public void unSetDefault() {
        flags = flags ^ CATEGORY_DEFAULT;
        setPriority();
    }
    public void unSetImportant() {
        flags = flags ^ CATEGORY_IMPORTANT;
        setPriority();
    }

    public void unSetUrgent() {
        flags = flags ^ CATEGORY_URGENT;
        setPriority();
    }

    /**
     * Devuelve el nombre de la categoría más característica
     * @return
     */
    public int getCategoryNameId() {
        if (isUrgent())
            return URGENT_NAME;
        else if (isImportant())
            return IMPORTANT_NAME;
        else if (isInformal())
            return INFORMAL_NAME;
        else if (isDefault())
            return DEFAULT_NAME;
        else
            return ARCHIVED_NAME;
    }

    /**
     * Implementación de la interfaz Comparable
     *
     * @param other Categoría con la que compara
     * @return Devuelve positivo si esta categoría es más prioritaria,
     * negativo si el parámetro lo es, y 0 si son de igual prioridad.
     */
    @Override
    public int compareTo(@NonNull Object other) {
        try {
            return ((Category) other).getPriority() - this.getPriority();
        } catch (ClassCastException e) {
            throw new ClassCastException("Parameter must be of TaskEntries class");
        }
    }

    @Override
    public String toString() {
        return ChronoTaskerApplication.getContext().getResources().getString(getCategoryNameId());
    }


    @IntDef(flag = true,
            value = {CATEGORY_ARCHIVED, CATEGORY_INFORMAL, CATEGORY_DEFAULT, CATEGORY_IMPORTANT, CATEGORY_URGENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayOptions {
    }

}
