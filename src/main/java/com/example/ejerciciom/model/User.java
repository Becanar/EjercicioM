package com.example.ejerciciom.model;

import java.util.Objects;

/**
 * La clase {@code User} representa un usuario en el sistema,
 * con atributos para el nombre de usuario y la contraseña.
 */
public class User {

    /** El nombre de usuario. */
    private String usuario;

    /** La contraseña del usuario. */
    private String password;

    /**
     * Constructor completo para crear una instancia de {@code User}.
     *
     * @param usuario el nombre de usuario
     * @param password la contraseña del usuario
     */
    public User(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Constructor vacío para crear una instancia de {@code User} sin inicializar los atributos.
     */
    public User() {}

    /**
     * Obtiene el nombre de usuario.
     *
     * @return el nombre de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param usuario el nombre de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password la contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code User}.
     *
     * @return una cadena que representa al usuario
     */
    @Override
    public String toString() {
        return "User{" +
                "usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Compara este usuario con otro objeto para verificar si son iguales.
     * La comparación se realiza en función del nombre de usuario.
     *
     * @param o el objeto a comparar con este usuario
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User u = (User) o;
        return Objects.equals(usuario, u.usuario);
    }

    /**
     * Devuelve el código hash para este usuario.
     *
     * @return el código hash calculado en función del nombre de usuario
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(usuario);
    }
}
