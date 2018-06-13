package com.example.usuario.chronotasker.data.model;

import android.support.annotation.IntDef;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.App;

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
 * @version 2.0
 */
public class Category {

    //LINTING
    @IntDef(flag = true,
            value = {
            CATEGORY_ARCHIVED, CATEGORY_INFORMAL, CATEGORY_DEFAULT, CATEGORY_IMPORTANT, CATEGORY_URGENT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayOptions { }


    //CONSTANTES
    public static final int ARCHIVED_NAME = R.string.category_archived;
    public static final int INFORMAL_NAME = R.string.category_informal;
    public static final int DEFAULT_NAME = R.string.category_default;
    public static final int IMPORTANT_NAME = R.string.category_important;
    public static final int URGENT_NAME = R.string.category_urgent;

    public static final int CATEGORY_ARCHIVED = 0x01;
    public static final int CATEGORY_INFORMAL = 0x01 << 1;
    public static final int CATEGORY_DEFAULT = 0x01 << 2;
    public static final int CATEGORY_IMPORTANT = 0x01 << 3;
    public static final int CATEGORY_URGENT = 0x01 << 4;
    public static final int MAX_PRIORITY = (0x01 << 5) - 1;


    //PARAMETROS
    private int flags;


    //CONSTRUCTORES
    public Category() {
        setFlag(CATEGORY_DEFAULT);
    }
    /**
     * Pide las opciones que se han declarado arriba
     * @param priority Unión de múltiples flags para categoría compuesta
     */
    public Category(@DisplayOptions int priority) {
        checkValidPriority(priority);
    }


    //COMPARACION Y ANALISIS DE FLAGS
    /**
     * Comprueba si el objeto contiene una categoría concreta.
     * @param flag Categoría contenida en el objeto.
     * @return True si está contenida en el objeto o false si no lo está.
     */
    public static boolean hasFlag(int priority, @DisplayOptions int flag) {
        checkValidPriority(priority);
        return priority != CATEGORY_ARCHIVED && flag != CATEGORY_ARCHIVED ?
                priority == (priority | flag) :
                flag == CATEGORY_ARCHIVED;
    }
    public static void checkValidPriority(int priority) throws IllegalArgumentException {
        if(priority < CATEGORY_ARCHIVED || priority > MAX_PRIORITY)
            throw new IllegalArgumentException(App.getApp().getResources().getString(R.string.error_category_argument));
    }
    public static int compareFlagPriority(int priority, int otherPriority, @DisplayOptions int flag) {
        boolean firstHasFlag = hasFlag(priority, flag);
        boolean secondHasFlag = hasFlag(otherPriority, flag);

        return firstHasFlag == secondHasFlag ? 0 : firstHasFlag ? 1 : -1;
    }


    //MANEJO DE FLAGS
    public int getFlags() {
        return flags;
    }
    /**
     * Permite añadir los flags de la categoría.
     * @param flag Categoría que se añade al objeto.
     */
    public void setFlag(@DisplayOptions int flag) {
        flags = flag != CATEGORY_ARCHIVED ?
                flags | flag :
                CATEGORY_ARCHIVED;
    }
    /**
     * Permite editar los flags de la categoría.
     * @param flag Categoría que se elimina al objeto.
     */
    public void unSetFlag(@DisplayOptions int flag) {
        if(flag != CATEGORY_ARCHIVED)
            flags = flags ^ flag;
    }
    /**
     * Devuelve el nombre de la categoría más característica
     * @return Valor del recurso String correspondiente a dicha categoría.
     */
    public static int getFlagStringResId(int priority) {
        //TODO: Use Dictionary here
        if(hasFlag(priority, CATEGORY_URGENT))          return URGENT_NAME;
        if(hasFlag(priority, CATEGORY_IMPORTANT))       return IMPORTANT_NAME;
        if(hasFlag(priority, CATEGORY_DEFAULT))         return DEFAULT_NAME;
        if(hasFlag(priority, CATEGORY_INFORMAL))        return INFORMAL_NAME;
                                                        return ARCHIVED_NAME;
    }
    public static String getCategoryName(int priority) {
        return App.getApp().getResources().getString(getFlagStringResId(priority));
    }

}
