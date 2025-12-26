package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/naura_farma?serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            // ✅ WAJIB: register driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL tidak ditemukan!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal!");
            e.printStackTrace();
        }
        return null;
    }
}
