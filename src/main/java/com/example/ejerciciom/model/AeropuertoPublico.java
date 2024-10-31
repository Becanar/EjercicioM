package com.example.ejerciciom.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * La clase {@code AeropuertoPublico} representa un aeropuerto público,
 * que extiende la funcionalidad de {@link Aeropuerto} y añade información sobre su financiación
 * y número de trabajadores.
 */
public class AeropuertoPublico extends Aeropuerto {

    /** El aeropuerto asociado al aeropuerto público. */
    private Aeropuerto aeropuerto;

    /** Financiación pública del aeropuerto. */
    private BigDecimal financiacion;

    /** Número de trabajadores en el aeropuerto. */
    private int num_trabajadores;

    /**
     * Constructor completo para crear una instancia de {@code AeropuertoPublico}.
     *
     * @param aeropuerto      el aeropuerto asociado
     * @param financiacion    la financiación pública del aeropuerto
     * @param num_trabajadores el número de trabajadores en el aeropuerto
     */
    public AeropuertoPublico(Aeropuerto aeropuerto, BigDecimal financiacion, int num_trabajadores) {
        this.aeropuerto = aeropuerto;
        this.financiacion = financiacion;
        this.num_trabajadores = num_trabajadores;
    }

    /**
     * Constructor vacío para crear una instancia de {@code AeropuertoPublico} sin inicializar los atributos.
     */
    public AeropuertoPublico() {}

    /**
     * Obtiene el aeropuerto asociado a este aeropuerto público.
     *
     * @return el aeropuerto asociado
     */
    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    /**
     * Establece el aeropuerto asociado a este aeropuerto público.
     *
     * @param aeropuerto el aeropuerto asociado
     */
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    /**
     * Obtiene la financiación pública del aeropuerto.
     *
     * @return la financiación pública del aeropuerto
     */
    public BigDecimal getFinanciacion() {
        return financiacion;
    }

    /**
     * Establece la financiación pública del aeropuerto.
     *
     * @param financiacion la financiación pública del aeropuerto
     */
    public void setFinanciacion(BigDecimal financiacion) {
        this.financiacion = financiacion;
    }

    /**
     * Obtiene el número de trabajadores del aeropuerto.
     *
     * @return el número de trabajadores del aeropuerto
     */
    public int getNum_trabajadores() {
        return num_trabajadores;
    }

    /**
     * Establece el número de trabajadores del aeropuerto.
     *
     * @param num_trabajadores el número de trabajadores del aeropuerto
     */
    public void setNum_trabajadores(int num_trabajadores) {
        this.num_trabajadores = num_trabajadores;
    }

    /**
     * Compara este aeropuerto público con otro objeto para verificar si son iguales.
     * La comparación se realiza en función del aeropuerto asociado.
     *
     * @param o el objeto a comparar con este aeropuerto público
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AeropuertoPublico that = (AeropuertoPublico) o;
        return Objects.equals(aeropuerto, that.aeropuerto);
    }

    /**
     * Devuelve el código hash para este aeropuerto público.
     *
     * @return el código hash calculado en función del aeropuerto asociado
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(aeropuerto);
    }
}
