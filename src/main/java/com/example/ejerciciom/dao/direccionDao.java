package com.example.ejerciciom.dao;

import com.example.ejerciciom.db.ConectorDB;
import com.example.ejerciciom.model.Direccion;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase {@code direccionDao} proporciona métodos para realizar operaciones
 * de acceso a datos relacionadas con la entidad {@link Direccion}.
 * Estas operaciones incluyen la obtención, modificación, inserción y eliminación
 * de direcciones en la base de datos.
 */
public class direccionDao {

    /**
     * Obtiene una dirección de la base de datos a partir de su ID.
     *
     * @param id el ID de la dirección a obtener
     * @return la dirección correspondiente al ID, o {@code null} si no se encuentra
     */
    public static Direccion getDireccion(int id) {
        ConectorDB connection;
        Direccion direccion = null;
        try {
            connection = new ConectorDB();
            String consulta = "SELECT id,pais,ciudad,calle,numero FROM direcciones WHERE id = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String pais = rs.getString("pais");
                String ciudad = rs.getString("ciudad");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");
                direccion = new Direccion(id, pais, ciudad, calle, numero);
            }
            rs.close();
            connection.closeConexion();
        } catch (SQLException | FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return direccion;
    }

    /**
     * Modifica una dirección existente en la base de datos.
     *
     * @param direccion      la dirección que se va a modificar
     * @param direccionNueva la nueva dirección que reemplazará a la existente
     * @return {@code true} si la modificación fue exitosa; {@code false} en caso contrario
     */
    public static boolean modificar(Direccion direccion, Direccion direccionNueva) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "UPDATE direcciones SET pais = ?, ciudad = ?, calle = ?, numero = ? WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, direccionNueva.getPais());
            pstmt.setString(2, direccionNueva.getCiudad());
            pstmt.setString(3, direccionNueva.getCalle());
            pstmt.setInt(4, direccionNueva.getNumero());
            pstmt.setInt(5, direccion.getId());
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
     * Inserta una nueva dirección en la base de datos.
     *
     * @param direccion la dirección a insertar
     * @return el ID de la nueva dirección si la inserción fue exitosa; {@code -1} en caso contrario
     */
    public static int insertar(Direccion direccion) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "INSERT INTO direcciones (pais, ciudad, calle, numero) VALUES (?, ?, ?, ?)";
            pstmt = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, direccion.getPais());
            pstmt.setString(2, direccion.getCiudad());
            pstmt.setString(3, direccion.getCalle());
            pstmt.setInt(4, direccion.getNumero());
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
     * Elimina una dirección de la base de datos.
     *
     * @param direccion la dirección a eliminar
     * @return {@code true} si la eliminación fue exitosa; {@code false} en caso contrario
     */
    public static boolean eliminar(Direccion direccion) {
        ConectorDB connection;
        PreparedStatement pstmt;
        try {
            connection = new ConectorDB();
            String consulta = "DELETE FROM direcciones WHERE id = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, direccion.getId());
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
