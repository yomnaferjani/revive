package edu.urgence.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/revive";
    private static final String USER     = "root";
    private static final String PASSWORD = "";   // WAMP default: no password

    private static Myconnection instance;
    private Connection connection;

    private Myconnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connexion à la base de données réussie !");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion SQL : " + e.getMessage());
        }
    }

    public static Myconnection getInstance() {
        if (instance == null) {
            instance = new Myconnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            // Reconnect if connection is closed
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("❌ Reconnexion échouée : " + e.getMessage());
        }
        return connection;
    }
}