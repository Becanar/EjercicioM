package com.example.ejerciciom.dao;

import com.example.ejerciciom.db.ConectorDB;
import com.example.ejerciciom.model.Aeropuerto;
import com.example.ejerciciom.model.AeropuertoPrivado;
import com.example.ejerciciom.model.Direccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase {@code aeropuertoPrivadoDao} proporciona métodos para realizar operaciones
 * de acceso a datos relacionadas con la entidad {@link AeropuertoPrivado}.
 * Esto incluye la recuperación, modificación, inserción y eliminación
 * de información sobre aeropuertos privados en la base de datos.
 */
public class aeropuertoPrivadoDao {

    /**
     * Obtiene un aeropuerto privado de la base de datos a partir de su ID.
     *
     * @param id el ID del aeropuerto a recuperar
     * @return un objeto {@link AeropuertoPrivado} que contiene la información del aeropuerto,
     *         o {@code null} si no se encuentra ningún aeropuerto con el ID proporcionado
     */
    public static AeropuertoPrivado getAeropuerto(int id) {
        ConectorDB connection;
        AeropuertoPrivado aeropuerto = null;
        try {
            connection = new ConectorDB();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen,numero_socios FROM aeropuertos,aeropuertos_privados WHERE id=id_aeropuerto AND id = ?";
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
                Aeropuerto airport = new Aeropuerto(id,nombre,anio_inauguracion,capacidad,direccion,imagen);
                int numero_socios = rs.getInt("numero_socios");
                aeropuerto = new AeropuertoPrivado(airport,numero_socios);
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
     * Carga una lista de todos los aeropuertos privados en la base de datos.
     *
     * @return una lista observable de objetos {@link AeropuertoPrivado}
     */
    public static ObservableList<AeropuertoPrivado> cargarListado() {
        ConectorDB connection;
        ObservableList<AeropuertoPrivado> airportList = FXCollections.observableArrayList();
        try{
            connection = new ConectorDB();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen,numero_socios FROM aeropuertos,aeropuertos_privados WHERE id=id_aeropuerto";
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
                Aeropuerto aeropuerto = new Aeropuerto(id,nombre,anio_inauguracion,capacidad,direccion,imagen);
                int numero_socios = rs.getInt("numero_socios");
                AeropuertoPrivado airport = new AeropuertoPrivado(aeropuerto,numero_socios);
                airportList.add(airport);
            }
            rs.close();
            connection.closeConexion();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return airportList;
    }

    /**
     * Modifica la información de un aeropuerto privado en la base de datos.
     *
     * @param aeropuerto el aeropuerto privado existente que se desea modificar
     * @param aeropuertoNuevo el nuevo objeto {@link AeropuertoPrivado} que contiene la información actualizada
     * @return {@code true} si la modificación fue exitosa; {@code false} en caso contrario
     */
    public static boolean modificar(AeropuertoPrivado aeropuerto, AeropuertoPrivado aeropuertoNuevo) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "UPDATE aeropuertos_privados SET numero_socios = ? WHERE id_aeropuerto = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);

            pstmt.setInt(1, aeropuertoNuevo.getNumero_socios());
            pstmt.setInt(2, aeropuerto.getAeropuerto().getId());

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
     * Inserta un nuevo aeropuerto privado en la base de datos.
     *
     * @param aeropuerto el aeropuerto privado a insertar
     * @return {@code true} si la inserción fue exitosa; {@code false} en caso contrario
     */
    public static boolean insertar(AeropuertoPrivado aeropuerto) {
        ConectorDB connection;
        PreparedStatement pstmt;

        try {
            connection = new ConectorDB();

            String consulta = "INSERT INTO aeropuertos_privados (id_aeropuerto,numero_socios) VALUES (?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta);

            pstmt.setInt(1, aeropuerto.getAeropuerto().getId());
            pstmt.setInt(2, aeropuerto.getNumero_socios());

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
     * Elimina un aeropuerto privado de la base de datos.
     *
     * @param aeropuerto el aeropuerto privado que se desea eliminar
     * @return {@code true} si la eliminación fue exitosa; {@code false} en caso contrario
     */
    public static boolean eliminar(AeropuertoPrivado aeropuerto) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "DELETE FROM aeropuertos_privados WHERE (id_aeropuerto = ?)";
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
