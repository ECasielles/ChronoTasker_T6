package com.example.usuario.chronotasker.data.db.model;

import android.content.res.Resources;
import android.support.annotation.IntDef;

import com.example.usuario.chronotasker.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

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
public class Category {

    @IntDef(flag = true, value = {
            CATEGORY_INFORMAL, CATEGORY_DEFAULT, CATEGORY_IMPORTANT, CATEGORY_URGENT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayOptions {
    }

    public static final int CATEGORY_INFORMAL = R.string.category_informal;
    public static final int CATEGORY_DEFAULT = R.string.category_default;
    public static final int CATEGORY_IMPORTANT = R.string.category_important;
    public static final int CATEGORY_URGENT = R.string.category_urgent;

    static final int[] FLAG_ARRAY = {
            CATEGORY_IMPORTANT, CATEGORY_DEFAULT, CATEGORY_IMPORTANT, CATEGORY_URGENT
    };
    private static final ArrayList<Integer> allowedFlags;

    /**
     * Calcula las combinaciones posibles de flags usando operaciones de control de bit.
     * Hay 2^n combinaciones donde n es el número de flags disponibles, como se indica
     * en el bucle inicial.
     * El bucle interno convierte a binario el número de cada combinación y añade al flag
     * el valor de cada 1 obtenido. Por ejemplo:
     *              i = 13  ->  1 + 2 + 8 = (1,1,0,1,0)
     *              tempFlag = FLAG_ARRAY[0]|FLAG_ARRAY[2]|FLAG_ARRAY[4];
     *
     * No es la solución definitiva.
     * TODO: Crear listado y guardarlo para ahorrar tiempo de ejecución.
     */
    static {
        allowedFlags = new ArrayList<>();
        int combinations = (int) (Math.pow(2, FLAG_ARRAY.length) - 1);
        for (int i = 1; i <= combinations; i++) {
            int tempFlag = 0;
            for (int j = 0; j < FLAG_ARRAY.length; j *= 2) {
                if ((i & j) != 0)
                    tempFlag |= FLAG_ARRAY[j];
            }
            allowedFlags.add(tempFlag);
        }
    }

    /**
     * Combinación de categorías en forma de flags.
     */
    int flags;
    int priority;

    public Category(int flag) {
        if (!allowedFlags.contains(flag))
            throw new IllegalArgumentException(Resources.getSystem().getString(R.string.error_category_argument));
        this.flags = flag;
        setPriority();
    }

    public int getPriority() {
        return priority;
    }

    /**
     * Las categorías permiten ordenar por prioridad. Cada categoría tiene una
     * prioridad propia, múltiplo de 2, de forma que cada combinación de categorías
     * tiene una prioridad única.
     *
     * @return Prioridad combinada de las categorías.
     */
    void setPriority() {
        int priority = -1;
        if (isInformal())
            priority = 0;
        if (isDefault())
            priority += 1;
        if (isImportant())
            priority += 2;
        if (isUrgent())
            priority += 4;
        this.priority = priority;
    }

    /**
     * Devuelven true si contiene la categoría por la que pregunta
     *
     * @return
     */
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
        return (flags | CATEGORY_DEFAULT) == flags;
    }

    /**
     * Permiten editar los flags de la categoría.
     */
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



}
