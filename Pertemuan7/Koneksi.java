package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection conn = null;

    public static Connection getKoneksi() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/dbnilai"
                    + "?useSSL=false&serverTimezone=UTC",
                    "root",
                    "");   // ganti jika MySQL kamu pakai password

        } catch (Exception e) {

            System.out.println("Koneksi gagal: "
                    + e.getMessage());

        }

        return conn;

    }

}
