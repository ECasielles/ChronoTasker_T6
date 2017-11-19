package com.example.usuario.chronotasker.data.prefs;

/**
 * Maneja las preferencias en un archivo local y las guarda
 * en una propiedad estática UserPreferencesHolder
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class UserPreferences {
    /*
    private static final String FILE_READ_ERROR = "Error de lectura del archivo de preferencias";
    private static final String USER_PREFERENCES_FILE_PATH = "userprefs.data";
    private static UserPreferencesHolder userPreferencesHolder = null;
    private static UserPreferences userPreferences;

    private UserPreferences(Context appContext) {
        readPrefs(appContext);
    }

    //MÉTODOS
    *//**
     * Lee el archivo preferencias y lo importa a la clase UserPreferences como un objeto
     * público y estático UserPreferencesHolder.
     * @param appContext Contexto de la aplicación.
     *//*
    private void readPrefs(Context appContext){
        FileInputStream fileInputStream = null;
        int readChar;
        StringBuilder content = new StringBuilder();
        try {
            fileInputStream = appContext.openFileInput(appContext.getPackageResourcePath() + "/" + USER_PREFERENCES_FILE_PATH);
            while ((readChar = fileInputStream.read()) != -1)
                content.append((char) readChar);
            userPreferencesHolder = new UserPreferencesHolder(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    *//**
     * Guarda los cambios en el archivo preferencias y recarga userPreferencesHolder.
     * @param appContext Contexto de la aplicación
     * @param prefsContent Contenido del archivo preferencias
     * @return
     *//*
    private boolean writePrefs(Context appContext, String prefsContent){
        BufferedWriter out = null;
        boolean correcto = false;
        //TODO: Verificar la integridad con métodos dedicados
        String newPrefsContent = prefsContent;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(USER_PREFERENCES_FILE_PATH), "UTF-8")
            );
            out.write(newPrefsContent);
            correcto = true;
            readPrefs(appContext);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return correcto;
    }
    *//**
     * Singleton que garantiza que sólo haya una instancia de preferencias de usuario.
     * @param appContext
     * @return Objeto UserPreferencesHolder estático con las preferencias del archivo.
     *//*
    public static UserPreferencesHolder getUserPreferencesHolder(Context appContext) {
        if(userPreferences == null)
            userPreferences = new UserPreferences(appContext);
        return userPreferencesHolder;
    }

    //CLASE INTERNA
    *//**
     * Guarda la lectura del archivo de preferencias. Es lo que ven las actividades que buscan las preferencias.
     *
     * @author Enrique Casielles Lapeira
     * @version 1.0
     *//*
    public final class UserPreferencesHolder {
        boolean rememberUserData;
        String localUser;
        String localEmail;
        String localPassword;

        private UserPreferencesHolder(String fileContent) {
            String elements[] = fileContent.split(";");
            this.rememberUserData = Boolean.getBoolean(elements[0]);
            this.localUser = elements[1];
            this.localEmail = elements[2];
            this.localPassword = elements[3];
        }

        public boolean isRememberUserData() {
            return rememberUserData;
        }
        public void setRememberUserData(boolean rememberUserData) {
            this.rememberUserData = rememberUserData;
        }
        public String getLocalUser() {
            return localUser;
        }
        public void setLocalUser(String localUser) {
            this.localUser = localUser;
        }
        public String getLocalEmail() {
            return localEmail;
        }
        public void setLocalEmail(String localEmail) {
            this.localEmail = localEmail;
        }
        public String getLocalPassword() {
            return localPassword;
        }
        public void setLocalPassword(String localPassword) {
            this.localPassword = localPassword;
        }


    }
    */
}
