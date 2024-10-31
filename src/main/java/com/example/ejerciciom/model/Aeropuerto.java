package com.example.ejerciciom.model;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Objects;

/**
 * La clase {@code Aeropuerto} representa un aeropuerto con información detallada
 * sobre su identificación, nombre, año de inauguración, capacidad, dirección e imagen.
 */
public class Aeropuerto {

    /** Identificador único del aeropuerto. */
    private int id;

    /** Nombre del aeropuerto. */
    private String nombre;

    /** Año en que se inauguró el aeropuerto. */
    private int anio_inauguracion;

    /** Capacidad máxima del aeropuerto en términos de pasajeros o carga. */
    private int capacidad;

    /** Dirección del aeropuerto, representada mediante una instancia de la clase {@link Direccion}. */
    private Direccion direccion;

    /** Imagen del aeropuerto, almacenada como un {@link Blob}. */
    private Blob imagen;

    /**
     * Constructor completo para crear una instancia de {@code Aeropuerto} con todos sus atributos inicializados.
     *
     * @param id                el identificador único del aeropuerto
     * @param nombre            el nombre del aeropuerto
     * @param anio_inauguracion el año en que se inauguró el aeropuerto
     * @param capacidad         la capacidad máxima del aeropuerto
     * @param direccion         la dirección del aeropuerto
     * @param imagen            una imagen del aeropuerto en formato {@link Blob}
     */
    public Aeropuerto(int id, String nombre, int anio_inauguracion, int capacidad, Direccion direccion, Blob imagen) {
        this.id = id;
        this.nombre = nombre;
        this.anio_inauguracion = anio_inauguracion;
        this.capacidad = capacidad;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    /**
     * Constructor vacío para crear una instancia de {@code Aeropuerto} sin inicializar los atributos.
     */
    public Aeropuerto() {}

    /**
     * Obtiene el identificador del aeropuerto.
     *
     * @return el identificador del aeropuerto
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del aeropuerto.
     *
     * @param id el identificador del aeropuerto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del aeropuerto.
     *
     * @return el nombre del aeropuerto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del aeropuerto.
     *
     * @param nombre el nombre del aeropuerto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el año de inauguración del aeropuerto.
     *
     * @return el año de inauguración del aeropuerto
     */
    public int getAnio_inauguracion() {
        return anio_inauguracion;
    }

    /**
     * Establece el año de inauguración del aeropuerto.
     *
     * @param anio_inauguracion el año de inauguración del aeropuerto
     */
    public void setAnio_inauguracion(int anio_inauguracion) {
        this.anio_inauguracion = anio_inauguracion;
    }

    /**
     * Obtiene la capacidad máxima del aeropuerto.
     *
     * @return la capacidad máxima del aeropuerto
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece la capacidad máxima del aeropuerto.
     *
     * @param capacidad la capacidad máxima del aeropuerto
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Obtiene la dirección del aeropuerto.
     *
     * @return la dirección del aeropuerto
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del aeropuerto.
     *
     * @param direccion la dirección del aeropuerto
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la imagen del aeropuerto.
     *
     * @return la imagen del aeropuerto en formato {@link Blob}
     */
    public Blob getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen del aeropuerto.
     *
     * @param imagen la imagen del aeropuerto en formato {@link Blob}
     */
    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    /**
     * Devuelve una representación en formato de cadena de texto de la instancia de {@code Aeropuerto}.
     * Incluye el nombre, el año de inauguración, la capacidad y la dirección.
     *
     * @return una cadena que representa el aeropuerto
     */
    @Override
    public String toString() {
        return nombre +
                ", " + anio_inauguracion +
                ", Capacidad de: " + capacidad +
                ", " + direccion;
    }

    /**
     * Compara este aeropuerto con otro objeto para verificar si son iguales.
     * La comparación se realiza en función del nombre, año de inauguración, capacidad, dirección e imagen.
     *
     * @param o el objeto a comparar con este aeropuerto
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aeropuerto that = (Aeropuerto) o;
        return anio_inauguracion == that.anio_inauguracion && capacidad == that.capacidad &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(direccion, that.direccion) &&
                Objects.equals(imagen, that.imagen);
    }

    /**
     * Devuelve el código hash para este aeropuerto.
     *
     * @return el código hash calculado en función del nombre, año de inauguración, capacidad, dirección e imagen
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, anio_inauguracion, capacidad, direccion, imagen);
    }
}
