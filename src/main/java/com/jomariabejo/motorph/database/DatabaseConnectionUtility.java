package com.jomariabejo.motorph.database;


import com.jomariabejo.motorph.utility.AlertUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtility {
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll_sys";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Method to establish a database connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Open a connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // Handle exceptions
            AlertUtility.showErrorAlert(e.getMessage(), e.getSQLState(), String.valueOf(e.getCause()));

        }
        return connection;
    }

    // Method to close the database connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
    }
}
