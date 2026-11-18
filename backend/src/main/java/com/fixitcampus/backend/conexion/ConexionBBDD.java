package com.fixitcampus.backend.conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBBDD {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties props = new Properties();

        // 1. Intentar cargar desde el classpath (src/main/resources) o desde la raíz del proyecto
        try (InputStream input = ConexionBBDD.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                try (FileInputStream fileInput = new FileInputStream("application.properties")) {
                    props.load(fileInput);
                }
            }
        } catch (IOException e) {
            System.err.println("Aviso: No se encontró application.properties, se usarán variables de entorno.");
        }

        // 2. Priorizar variables de entorno; si no existen, usar el valor del fichero .properties
        URL = obtenerConfig("DB_URL", props.getProperty("db.url", "jdbc:mysql://localhost:3306/fixit_campus?useSSL=false&serverTimezone=UTC"));
        USER = obtenerConfig("DB_USER", props.getProperty("db.user", "root"));
        PASSWORD = obtenerConfig("DB_PASSWORD", props.getProperty("db.password"));

    }

    private static String obtenerConfig(String envVar, String valorPorDefecto) {
        String env = System.getenv(envVar);
        return (env != null && !env.isBlank()) ? env : valorPorDefecto;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}