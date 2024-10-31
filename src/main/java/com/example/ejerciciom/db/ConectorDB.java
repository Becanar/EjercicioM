package com.example.ejerciciom.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que establece la conexión a la base de datos MySQL.
 * Proporciona métodos para obtener la conexión y cerrarla.
 */
public class ConectorDB {
    private final Connection connection; ///< Conexión a la base de datos.

    /**
     * Constructor de la clase ConectorDB.
     * Establece la conexión a la base de datos con las credenciales especificadas.
     *
     * @throws SQLException Si hay un error al establecer la conexión a la base de datos.
     */
    public ConectorDB() throws SQLException, FileNotFoundException {
        Properties props = loadProperties();
        String url = props.getProperty("dburl");
        connection = DriverManager.getConnection(url, props);

        DatabaseMetaData databaseMetaData = connection.getMetaData();
        /*
        System.out.println();
        System.out.println("--- Datos de conexión ------------------------------------------");
        System.out.printf("Base de datos: %s%n", databaseMetaData.getDatabaseProductName());
        System.out.printf("  Versión: %s%n", databaseMetaData.getDatabaseProductVersion());
        System.out.printf("Driver: %s%n", databaseMetaData.getDriverName());
        System.out.printf("  Versión: %s%n", databaseMetaData.getDriverVersion());
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        connection.setAutoCommit(true);
        */
    }

    /**
     * Obtiene la conexión a la base de datos.
     *
     * @return La conexión a la base de datos.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos.
     *
     * @throws SQLException Si hay un error al cerrar la conexión.
     */
    public Connection closeConexion() throws SQLException {
        connection.close(); // Cierra la conexión.
        return connection; // Retorna la conexión cerrada (aunque ya no será útil).
    }

    /*public static void main(String[] args) throws SQLException, FileNotFoundException {
        ConectorDB c = new ConectorDB();
        c.getConnection(); // Obtiene la conexión a la base de datos.
    }*/
    /**
     * Carga las propiedades desde el archivo "db.properties".
     *
     * @return un objeto {@link Properties} con las propiedades del archivo.
     * @throws FileNotFoundException si el archivo "db.properties" no se encuentra.
     * @throws RuntimeException si ocurre un error al cargar las propiedades.
     */
    public static Properties loadProperties() throws FileNotFoundException {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}