package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/naura_farma?serverTimezone=Asia/Jakarta",
                    "root",
                    ""

                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
