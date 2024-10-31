package com.example.ejerciciom.model;

import java.util.Objects;

/**
 * La clase {@code AeropuertoPrivado} representa un aeropuerto privado,
 * que extiende la funcionalidad de {@link Aeropuerto} y añade un número de socios privados.
 */
public class AeropuertoPrivado extends Aeropuerto {

    /** El aeropuerto público asociado al aeropuerto privado. */
    private Aeropuerto aeropuerto;

    /** Número de socios privados del aeropuerto. */
    private int numero_socios;

    /**
     * Constructor completo para crear una instancia de {@code AeropuertoPrivado}.
     *
     * @param aeropuerto   el aeropuerto público asociado
     * @param numero_socios el número de socios privados del aeropuerto
     */
    public AeropuertoPrivado(Aeropuerto aeropuerto, int numero_socios) {
        this.aeropuerto = aeropuerto;
        this.numero_socios = numero_socios;
    }

    /**
     * Constructor vacío para crear una instancia de {@code AeropuertoPrivado} sin inicializar los atributos.
     */
    public AeropuertoPrivado() {}

    /**
     * Obtiene el aeropuerto público asociado.
     *
     * @return el aeropuerto público asociado a este aeropuerto privado
     */
    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    /**
     * Establece el aeropuerto público asociado.
     *
     * @param aeropuerto el aeropuerto público asociado
     */
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Obtiene el número de socios privados del aeropuerto.
     *
     * @return el número de socios privados del aeropuerto
     */
    public int getNumero_socios() {
        return numero_socios;
    }

    /**
     * Establece el número de socios privados del aeropuerto.
     *
     * @param numero_socios el número de socios privados del aeropuerto
     */
    public void setNumero_socios(int numero_socios) {
        this.numero_socios = numero_socios;
    }

    /**
     * Compara este aeropuerto privado con otro objeto para verificar si son iguales.
     * La comparación se realiza en función del aeropuerto asociado.
     *
     * @param o el objeto a comparar con este aeropuerto privado
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AeropuertoPrivado that = (AeropuertoPrivado) o;
        return Objects.equals(aeropuerto, that.aeropuerto);
    }

    /**
     * Devuelve el código hash para este aeropuerto privado.
     *
     * @return el código hash calculado en función del aeropuerto asociado
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(aeropuerto);
    }
}
