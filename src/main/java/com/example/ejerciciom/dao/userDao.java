package com.example.ejerciciom.dao;

import com.example.ejerciciom.db.ConectorDB;
import com.example.ejerciciom.model.User;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase {@code userDao} proporciona métodos para realizar operaciones
 * de acceso a datos relacionadas con la entidad {@link User}.
 * En particular, permite recuperar información sobre los usuarios almacenados
 * en la base de datos.
 */
public class userDao {

    /**
     * Obtiene un usuario de la base de datos a partir de su nombre de usuario.
     *
     * @param usuario el nombre de usuario del usuario a recuperar
     * @return un objeto {@link User} que contiene la información del usuario,
     *         o {@code null} si no se encuentra ningún usuario con el nombre proporcionado
     */
    public static User getUsuario(String usuario) {
        ConectorDB connection;
        User user = null;
        try {
            connection = new ConectorDB();
            String consulta = "SELECT usuario, password FROM usuarios WHERE usuario = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nom_usuario = rs.getString("usuario");
                String password = rs.getString("password");
                user = new User(nom_usuario, password);
            }
            rs.close();
            connection.closeConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
