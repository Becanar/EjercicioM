package com.example.ejerciciom.model;

import java.util.Objects;

/**
 * La clase {@code Direccion} representa una dirección geográfica,
 * que incluye información sobre el país, ciudad, calle y número.
 */
public class Direccion {

    /** El identificador único de la dirección. */
    private int id;

    /** El país donde se encuentra la dirección. */
    private String pais;

    /** La ciudad donde se localiza la dirección. */
    private String ciudad;

    /** La calle asociada a la dirección. */
    private String calle;

    /** El número de la dirección en la calle. */
    private int numero;

    /**
     * Constructor completo para crear una instancia de {@code Direccion}.
     *
     * @param id      el identificador único de la dirección
     * @param pais    el país donde se encuentra la dirección
     * @param ciudad  la ciudad donde se localiza la dirección
     * @param calle   la calle asociada a la dirección
     * @param numero  el número de la dirección en la calle
     */
    public Direccion(int id, String pais, String ciudad, String calle, int numero) {
        this.id = id;
        this.pais = pais;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    /**
     * Constructor vacío para crear una instancia de {@code Direccion} sin inicializar los atributos.
     */
    public Direccion() {}

    /**
     * Obtiene el identificador único de la dirección.
     *
     * @return el identificador de la dirección
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único de la dirección.
     *
     * @param id el identificador de la dirección
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el país donde se encuentra la dirección.
     *
     * @return el país de la dirección
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país donde se encuentra la dirección.
     *
     * @param pais el país de la dirección
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Obtiene la ciudad donde se localiza la dirección.
     *
     * @return la ciudad de la dirección
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad donde se localiza la dirección.
     *
     * @param ciudad la ciudad de la dirección
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene la calle asociada a la dirección.
     *
     * @return la calle de la dirección
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Establece la calle asociada a la dirección.
     *
     * @param calle la calle de la dirección
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtiene el número de la dirección en la calle.
     *
     * @return el número de la dirección
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece el número de la dirección en la calle.
     *
     * @param numero el número de la dirección
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Devuelve una representación en forma de cadena de la dirección.
     *
     * @return una cadena que representa la dirección
     */
    @Override
    public String toString() {
        return pais + ", " + ciudad + ", " + calle + ", " + numero;
    }

    /**
     * Compara esta dirección con otro objeto para verificar si son iguales.
     * La comparación se realiza en función de los atributos país, ciudad, calle y número.
     *
     * @param o el objeto a comparar con esta dirección
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return numero == direccion.numero &&
                Objects.equals(pais, direccion.pais) &&
                Objects.equals(ciudad, direccion.ciudad) &&
                Objects.equals(calle, direccion.calle);
    }

    /**
     * Devuelve el código hash para esta dirección.
     *
     * @return el código hash calculado en función de los atributos país, ciudad, calle y número
     */
    @Override
    public int hashCode() {
        return Objects.hash(pais, ciudad, calle, numero);
    }
}
