package com.example.usuario.chronotasker.data.db.model;

import android.content.res.Resources;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.example.usuario.chronotasker.R;

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
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Category implements Comparable {

    //CONSTANTES
    @IntDef(flag = true,
            value = {CATEGORY_NONE, CATEGORY_INFORMAL, CATEGORY_DEFAULT, CATEGORY_IMPORTANT, CATEGORY_URGENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayOptions {
    }

    public static final int NONE_NAME = R.string.category_none;
    public static final int INFORMAL_NAME = R.string.category_informal;
    public static final int DEFAULT_NAME = R.string.category_default;
    public static final int IMPORTANT_NAME = R.string.category_important;
    public static final int URGENT_NAME = R.string.category_urgent;
    public static final int CATEGORY_NONE = 0;
    public static final int CATEGORY_INFORMAL = 1;
    public static final int CATEGORY_DEFAULT = 2;
    public static final int CATEGORY_IMPORTANT = 4;
    public static final int CATEGORY_URGENT = 8;
    /**
     * La unión de todos los flags es 2^5 - 1
     */
    public static final int MAX_FLAGS = 31;

    //PARAMETROS
    /**
     * Combinación de categorías en forma de flags.
     */
    private int flags;
    private int priority;

    /**
     * Pide las opciones que se han declarado arriba
     *
     * @param flag Unión de múltiples flags para categoría compuesta
     */
    public Category(@DisplayOptions int flag) {
        if (flag > MAX_FLAGS)
            throw new IllegalArgumentException(Resources.getSystem().getString(R.string.error_category_argument));
        this.flags = flag;
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
    public boolean isNone() {
        return CATEGORY_NONE == flags;
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
    public void setNone() {
        flags = CATEGORY_NONE;
    }
    public void setInformal() {
        flags = flags | CATEGORY_INFORMAL;
    }
    public void setDefault() {
        flags = flags | CATEGORY_DEFAULT;
    }
    public void setImportant() {
        flags = flags | CATEGORY_IMPORTANT;
    }
    public void setUrgent() {
        flags = flags | CATEGORY_DEFAULT;
    }
    public void unSetInformal() {
        flags = flags ^ CATEGORY_INFORMAL;
    }
    public void unSetDefault() {
        flags = flags ^ CATEGORY_DEFAULT;
    }
    public void unSetImportant() {
        flags = flags ^ CATEGORY_IMPORTANT;
    }

    public void unSetUrgent() {
        flags = flags ^ CATEGORY_DEFAULT;
    }

    /**
     * Devuelve el nombre de la categoría más característica
     *
     * @return
     */
    public int getCategoryNameId() {
        if (this.isUrgent())
            return URGENT_NAME;
        else if (this.isImportant())
            return IMPORTANT_NAME;
        else if (this.isDefault())
            return DEFAULT_NAME;
        else if (this.isInformal())
            return INFORMAL_NAME;
        else return NONE_NAME;
    }

    /**
     * Permiten editar los valores de texto cuando se cambia el idioma
     */

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
            throw new ClassCastException("Parameter must be of Task class");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getFlags());
    }

}
