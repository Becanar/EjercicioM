package com.example.ejerciciom.dao;

import com.example.ejerciciom.db.ConectorDB;
import com.example.ejerciciom.model.Direccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.ejerciciom.model.Aeropuerto;

import java.io.*;
import java.sql.*;

/**
 * La clase {@code aeropuertoDao} proporciona métodos para realizar operaciones
 * de acceso a datos relacionadas con la entidad {@link Aeropuerto}.
 * Esto incluye la recuperación, modificación, inserción y eliminación
 * de información sobre aeropuertos en la base de datos.
 */
public class aeropuertoDao {

    /**
     * Obtiene un aeropuerto de la base de datos a partir de su ID.
     *
     * @param id el ID del aeropuerto a recuperar
     * @return un objeto {@link Aeropuerto} que contiene la información del aeropuerto,
     *         o {@code null} si no se encuentra ningún aeropuerto con el ID proporcionado
     */
    public static Aeropuerto getAeropuerto(int id) {
        ConectorDB connection;
        Aeropuerto aeropuerto = null;
        try {
            connection = new ConectorDB();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen FROM aeropuertos WHERE id = ?";
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
                aeropuerto = new Aeropuerto(id_aeropuerto,nombre,anio_inauguracion,capacidad,direccion,imagen);
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
     * Carga una lista de todos los aeropuertos en la base de datos.
     *
     * @return una lista observable de objetos {@link Aeropuerto}
     */
    public static ObservableList<Aeropuerto> cargarListado() {
        ConectorDB connection;
        ObservableList<Aeropuerto> airportList = FXCollections.observableArrayList();
        try{
            connection = new ConectorDB();
            String consulta = "SELECT id,nombre,anio_inauguracion,capacidad,id_direccion,imagen FROM aeropuertos";
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
                Aeropuerto airport = new Aeropuerto(id,nombre,anio_inauguracion,capacidad,direccion,imagen);
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
     * Modifica la información de un aeropuerto en la base de datos.
     *
     * @param aeropuerto el aeropuerto existente que se desea modificar
     * @param aeropuertoNuevo el nuevo objeto {@link Aeropuerto} que contiene la información actualizada
     * @return {@code true} si la modificación fue exitosa; {@code false} en caso contrario
     */
    public static boolean modificar(Aeropuerto aeropuerto, Aeropuerto aeropuertoNuevo) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "UPDATE aeropuertos SET nombre = ?,anio_inauguracion = ?,capacidad = ?,id_direccion = ?,imagen = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, aeropuertoNuevo.getNombre());
            pstmt.setInt(2, aeropuertoNuevo.getAnio_inauguracion());
            pstmt.setInt(3, aeropuertoNuevo.getCapacidad());
            pstmt.setInt(4, aeropuertoNuevo.getDireccion().getId());
            pstmt.setBlob(5, aeropuertoNuevo.getImagen());
            pstmt.setInt(6, aeropuerto.getId());
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
     * Inserta un nuevo aeropuerto en la base de datos.
     *
     * @param aeropuerto el aeropuerto a insertar
     * @return el ID del nuevo aeropuerto si la inserción fue exitosa; -1 en caso contrario
     */
    public  static int insertar(Aeropuerto aeropuerto) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "INSERT INTO aeropuertos (nombre,anio_inauguracion,capacidad,id_direccion,imagen) VALUES (?,?,?,?,?) ";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, aeropuerto.getNombre());
            pstmt.setInt(2, aeropuerto.getAnio_inauguracion());
            pstmt.setInt(3, aeropuerto.getCapacidad());
            pstmt.setInt(4, aeropuerto.getDireccion().getId());
            pstmt.setBlob(5, aeropuerto.getImagen());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    pstmt.close();
                    connection.closeConexion();
                    return id;
                }
            }
            pstmt.close();
            connection.closeConexion();
            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un aeropuerto de la base de datos.
     *
     * @param aeropuerto el aeropuerto que se desea eliminar
     * @return {@code true} si la eliminación fue exitosa; {@code false} en caso contrario
     */
    public  static boolean eliminar(Aeropuerto aeropuerto){
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "DELETE FROM aeropuertos WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, aeropuerto.getId());
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
     * Convierte un archivo en un objeto Blob para almacenarlo en una base de datos.
     *
     * <p>Este método lee los datos de un archivo y los convierte en un Blob mediante
     * un flujo de bytes. El Blob resultante puede utilizarse para almacenar datos binarios
     * en la base de datos. La conexión a la base de datos se gestiona a través de
     * la clase ConectorDB.</p>
     *
     * @param file el archivo que se desea convertir en un Blob.
     * @return un objeto Blob que contiene los datos binarios del archivo proporcionado.
     * @throws SQLException si ocurre un error al crear el Blob o con la conexión a la base de datos.
     * @throws FileNotFoundException si el archivo especificado no se encuentra o no se puede abrir.
     * @throws RuntimeException si ocurre un error de entrada/salida al leer el archivo.
     */
    public static Blob convertFileToBlob(File file) throws SQLException, FileNotFoundException {
        ConectorDB connection = new ConectorDB();
        try (Connection conn = connection.getConnection();
             FileInputStream inputStream = new FileInputStream(file)) {

            Blob blob = conn.createBlob();
            byte[] buffer = new byte[1024];
            int bytesRead;

            try (var outputStream = blob.setBinaryStream(1)) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return blob;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
