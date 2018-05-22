package com.example.usuario.chronotasker.utils;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que maneja herramientas de uso general.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Common {

    /**
     * ^               # Inicio de la cadena
     * (?=.*[0-9])       # Al menos un dígito
     * (?=.*[a-z])       # Al menos una minúscula
     * (?=.*[A-Z])       # Al menos una mayúscula
     * .{6,20}           # Longitud mínima 6 y máxima 20
     * $                 # Fin de la cadena
     */
    private static final String LENGTH_PATTERN = "^().{6,20}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
    private static final String EMAIL_PATTERN = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    public static boolean isValidFieldLength(String field) {
        Pattern pattern = Pattern.compile(LENGTH_PATTERN);
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }

    public static boolean isValidPasswordFormat(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidEmailFormat(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String formatJodaTime(DateTime dateTime) {
        return DateTimeFormat.forPattern("HH:mm dd/MM/yyyy").print(dateTime);
    }

}
