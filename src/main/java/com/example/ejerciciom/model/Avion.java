package com.example.ejerciciom.model;

import java.util.Objects;

/**
 * La clase {@code Avion} representa un avión con detalles sobre su modelo, número de asientos, velocidad máxima,
 * estado de activación y el aeropuerto asociado.
 */
public class Avion {

    /** Identificador único del avión. */
    private int id;

    /** Modelo del avión. */
    private String modelo;

    /** Número de asientos en el avión. */
    private int numero_asientos;

    /** Velocidad máxima del avión en km/h. */
    private int velocidad_maxima;

    /** Estado de activación del avión: {@code true} si está activado, {@code false} si está desactivado. */
    private boolean activado;

    /** Aeropuerto al que pertenece el avión, representado por una instancia de {@link Aeropuerto}. */
    private Aeropuerto aeropuerto;

    /**
     * Constructor completo para crear una instancia de {@code Avion} con todos los atributos inicializados.
     *
     * @param id                el identificador único del avión
     * @param modelo            el modelo del avión
     * @param numero_asientos   el número de asientos en el avión
     * @param velocidad_maxima  la velocidad máxima del avión
     * @param activado          el estado de activación del avión
     * @param aeropuerto        el aeropuerto al que está asociado el avión
     */
    public Avion(int id, String modelo, int numero_asientos, int velocidad_maxima, boolean activado, Aeropuerto aeropuerto) {
        this.id = id;
        this.modelo = modelo;
        this.numero_asientos = numero_asientos;
        this.velocidad_maxima = velocidad_maxima;
        this.activado = activado;
        this.aeropuerto = aeropuerto;
    }

    /**
     * Constructor vacío para crear una instancia de {@code Avion} sin inicializar los atributos.
     */
    public Avion() {}

    /**
     * Obtiene el identificador único del avión.
     *
     * @return el identificador del avión
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del avión.
     *
     * @param id el identificador del avión
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el modelo del avión.
     *
     * @return el modelo del avión
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del avión.
     *
     * @param modelo el modelo del avión
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el número de asientos del avión.
     *
     * @return el número de asientos en el avión
     */
    public int getNumero_asientos() {
        return numero_asientos;
    }

    /**
     * Establece el número de asientos del avión.
     *
     * @param numero_asientos el número de asientos en el avión
     */
    public void setNumero_asientos(int numero_asientos) {
        this.numero_asientos = numero_asientos;
    }

    /**
     * Obtiene la velocidad máxima del avión.
     *
     * @return la velocidad máxima del avión en km/h
     */
    public int getVelocidad_maxima() {
        return velocidad_maxima;
    }

    /**
     * Establece la velocidad máxima del avión.
     *
     * @param velocidad_maxima la velocidad máxima del avión en km/h
     */
    public void setVelocidad_maxima(int velocidad_maxima) {
        this.velocidad_maxima = velocidad_maxima;
    }

    /**
     * Verifica si el avión está activado.
     *
     * @return {@code true} si el avión está activado, {@code false} en caso contrario
     */
    public boolean isActivado() {
        return activado;
    }

    /**
     * Establece el estado de activación del avión.
     *
     * @param activado el estado de activación del avión
     */
    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    /**
     * Obtiene el aeropuerto asociado al avión.
     *
     * @return el aeropuerto al que pertenece el avión
     */
    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    /**
     * Establece el aeropuerto al que pertenece el avión.
     *
     * @param aeropuerto el aeropuerto asociado al avión
     */
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Devuelve una representación en cadena del avión, incluyendo su modelo, número de asientos, velocidad máxima y estado de activación.
     *
     * @return una cadena que representa el avión
     */
    @Override
    public String toString() {
        return modelo  + numero_asientos + ", asientos," +
                ", velocidad máxima de " + velocidad_maxima +
                ", activado=" + activado;
    }

    /**
     * Compara este avión con otro objeto para verificar si son iguales.
     * La comparación se realiza en función del modelo y el aeropuerto al que están asociados.
     *
     * @param o el objeto a comparar con este avión
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return Objects.equals(modelo, avion.modelo) && Objects.equals(aeropuerto, avion.aeropuerto);
    }

    /**
     * Devuelve el código hash para este avión.
     *
     * @return el código hash calculado en función del modelo y aeropuerto
     */
    @Override
    public int hashCode() {
        return Objects.hash(modelo, aeropuerto);
    }

}
