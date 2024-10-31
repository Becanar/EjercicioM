package com.example.ejerciciom.dao;

import com.example.ejerciciom.db.ConectorDB;
import com.example.ejerciciom.model.Aeropuerto;
import com.example.ejerciciom.model.AeropuertoPublico;
import com.example.ejerciciom.model.Direccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase {@code aeropuertoPublicoDao} proporciona métodos para realizar operaciones
 * de acceso a datos relacionadas con la entidad {@link AeropuertoPublico}.
 * Esto incluye la recuperación, modificación, inserción y eliminación
 * de información sobre aeropuertos públicos en la base de datos.
 */
public class aeropuertoPublicoDao {

    /**
     * Obtiene un aeropuerto público de la base de datos a partir de su ID.
     *
     * @param id el ID del aeropuerto a recuperar
     * @return un objeto {@link AeropuertoPublico} que contiene la información del aeropuerto,
     *         o {@code null} si no se encuentra ningún aeropuerto con el ID proporcionado
     */
    public static AeropuertoPublico getAeropuerto(int id) {
        ConectorDB connection;
        AeropuertoPublico aeropuerto = null;
        try {
            connection = new ConectorDB();
            String consulta = "SELECT id, nombre, anio_inauguracion, capacidad, id_direccion, imagen, financiacion, num_trabajadores "
                    + "FROM aeropuertos, aeropuertos_publicos "
                    + "WHERE id = id_aeropuerto AND id = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_aeropuerto = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int anio_inauguracion = rs.getInt("anio_inauguracion");
                int capacidad = rs.getInt("capacidad");
                int id_direccion = rs.getInt("id_direccion");
                Direccion direccion = direccionDao.getDireccion(id_direccion);
                Blob imagen = rs.getBlob("imagen");
                Aeropuerto airport = new Aeropuerto(id_aeropuerto, nombre, anio_inauguracion, capacidad, direccion, imagen);
                BigDecimal financiacion = rs.getBigDecimal("financiacion");
                int num_trabajadores = rs.getInt("num_trabajadores");
                aeropuerto = new AeropuertoPublico(airport, financiacion, num_trabajadores);
            }
            rs.close();
            connection.closeConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return aeropuerto;
    }

    /**
     * Carga una lista de todos los aeropuertos públicos en la base de datos.
     *
     * @return una lista observable de objetos {@link AeropuertoPublico}
     */
    public static ObservableList<AeropuertoPublico> cargarListado() {
        ConectorDB connection;
        ObservableList<AeropuertoPublico> airportList = FXCollections.observableArrayList();
        try {
            connection = new ConectorDB();
            String consulta = "SELECT id, nombre, anio_inauguracion, capacidad, id_direccion, imagen, financiacion, num_trabajadores "
                    + "FROM aeropuertos, aeropuertos_publicos "
                    + "WHERE id = id_aeropuerto";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int anio_inauguracion = rs.getInt("anio_inauguracion");
                int capacidad = rs.getInt("capacidad");
                int id_direccion = rs.getInt("id_direccion");
                Direccion direccion = direccionDao.getDireccion(id_direccion);
                Blob imagen = rs.getBlob("imagen");
                Aeropuerto aeropuerto = new Aeropuerto(id, nombre, anio_inauguracion, capacidad, direccion, imagen);
                BigDecimal financiacion = rs.getBigDecimal("financiacion");
                int num_trabajadores = rs.getInt("num_trabajadores");
                AeropuertoPublico airport = new AeropuertoPublico(aeropuerto, financiacion, num_trabajadores);
                airportList.add(airport);
            }
            rs.close();
            connection.closeConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return airportList;
    }

    /**
     * Modifica la información de un aeropuerto público en la base de datos.
     *
     * @param aeropuerto el aeropuerto público existente que se desea modificar
     * @param aeropuertoNuevo el nuevo objeto {@link AeropuertoPublico} que contiene la información actualizada
     * @return {@code true} si la modificación fue exitosa; {@code false} en caso contrario
     */
    public static boolean modificar(AeropuertoPublico aeropuerto, AeropuertoPublico aeropuertoNuevo) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "UPDATE aeropuertos_publicos SET financiacion = ?, num_trabajadores = ? WHERE id_aeropuerto = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setBigDecimal(1, aeropuertoNuevo.getFinanciacion());
            pstmt.setInt(2, aeropuertoNuevo.getNum_trabajadores());
            pstmt.setInt(3, aeropuerto.getAeropuerto().getId());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserta un nuevo aeropuerto público en la base de datos.
     *
     * @param aeropuerto el aeropuerto público a insertar
     * @return {@code true} si la inserción fue exitosa; {@code false} en caso contrario
     */
    public static boolean insertar(AeropuertoPublico aeropuerto) {
        ConectorDB connection;
        PreparedStatement pstmt;

        try {
            connection = new ConectorDB();
            String consulta = "INSERT INTO aeropuertos_publicos (id_aeropuerto, financiacion, num_trabajadores) VALUES (?, ?, ?)";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, aeropuerto.getAeropuerto().getId());
            pstmt.setBigDecimal(2, aeropuerto.getFinanciacion());
            pstmt.setInt(3, aeropuerto.getNum_trabajadores());

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConexion();
            return (filasAfectadas > 0);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un aeropuerto público de la base de datos.
     *
     * @param aeropuerto el aeropuerto público que se desea eliminar
     * @return {@code true} si la eliminación fue exitosa; {@code false} en caso contrario
     */
    public static boolean eliminar(AeropuertoPublico aeropuerto) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "DELETE FROM aeropuertos_publicos WHERE id_aeropuerto = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, aeropuerto.getAeropuerto().getId());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            connection.closeConexion();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
