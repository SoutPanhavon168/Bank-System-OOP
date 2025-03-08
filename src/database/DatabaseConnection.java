package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://banksystem-prumsereyreaksa-c339.h.aivencloud.com:23842/banksystem?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_eEoTvjWciUxHeIQBJHR";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the MySQL driver is in your classpath
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Failed to connect to database", e);
        }
    }
}
