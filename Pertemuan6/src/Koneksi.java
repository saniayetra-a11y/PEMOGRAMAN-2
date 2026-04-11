import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    public static Connection getKoneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // penting

            String url = "jdbc:mysql://localhost:3306/dbkampus";
            String user = "root";
            String pass = "";

            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi berhasil");
            return conn;

        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
