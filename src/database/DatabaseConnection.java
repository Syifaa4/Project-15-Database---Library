package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // URL database
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";

    // Username MySQL
    private static final String USER = "root";

    // Password MySQL (kosong jika default XAMPP)
    private static final String PASSWORD = "";

    // Method untuk mendapatkan koneksi
    public static Connection getConnection() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Berhasil terhubung ke database.");

        } catch (SQLException e) {

            System.out.println("Koneksi gagal!");
            System.out.println(e.getMessage());

        }

        return conn;
    }
}