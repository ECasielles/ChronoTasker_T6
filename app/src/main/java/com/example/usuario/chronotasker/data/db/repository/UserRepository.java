package com.example.usuario.chronotasker.data.db.repository;

import com.example.usuario.chronotasker.data.db.model.User;

import java.util.ArrayList;

/**
 * Almacena los datos de usuario
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 * @see User
 */
public class UserRepository {
    /*DECLARACIÓN*/
    private ArrayList<User> users;
    private static UserRepository userRepository;
    static { userRepository = new UserRepository(); }

    /*INICIALIZACIÓN*/
    //De esta forma inicializa los atributos de ámbito estático o de clase
    //Es la forma alternativa aunque puede ser costoso y se puede evitar
    /**
     * Constructor privado que garantiza una instancia única de la clase
     */
    private UserRepository() {
        this.users = new ArrayList<>();
        initialize();
    }

    /*MÉTODOS*/
    /**
     * Inicializa la estructura de datos DependencyRepository
     */
    private void initialize() {
        saveUser(new User(0, "", "",""));
        saveUser(new User(1, "user", "password","email"));
        saveUser(new User(2, "Enrique", "123","enrique@gmail.com"));
        saveUser(new User(3, "Lourdes", "123","lourdes@gmail.com"));
    }
    /**
     * Accesor de la clase SectorRepository
     * @return Devuelve la instancia de la clase como objeto SectorRepository
     */
    public static UserRepository getInstance(){
        if (userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }
    /**
     * Método que añade un nuevo sector
     * @param user Objeto de clase Sector
     */
    private void saveUser(User user) { users.add(user); }
    /**
     * Devuelve la referencia al objeto
     * @return referencia al objeto ArrayList
     */
    public ArrayList<User> getUsers() {
        return users;
    }
    /**
     * Comprueba si un usuario existe en la base de datos.
     * @param data Nombre de usuario o correo que se quiere buscar en la BD
     * @return índice si existe en la BD ó -1.
     */
    public int userExists(String data) {
        int result = -1;
        int counter = 0;
        User tempUser;
        while (counter < users.size()) {
            tempUser = users.get(counter);
            if (tempUser.getName().equals(data) || tempUser.getEmail().equals(data)) {
                result = counter;
                break;
            }
            counter++;
        }
        return result;
    }
    /**
     * Confirma si las credenciales son correctas.
     * @param data Nombre de usuario o correo electrónico
     * @param password Contraseña
     * @return true si existe en la base de datos
     */
    public boolean validateCredentials(String data, String password) {
        int index = userExists(data);
        if(index > -1)
            return users.get(index).getPassword().equals(password);
        else
            return false;
    }

    public boolean addUser(String name, String email, String password){
        boolean result = false;
        if(userExists(name) == -1 && userExists(email) == -1) {
            users.add(new User(users.size(), name, password, email));
            result = true;
        }
        return result;
    }

}
