package penjualan;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static final String HOST = "localhost";
    private static final String DB   = "db_penjualan";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://" + HOST + "/" + DB
                + "?useSSL=false&serverTimezone=UTC", USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Gagal koneksi: " + e.getMessage(), e);
        }
    }
}
