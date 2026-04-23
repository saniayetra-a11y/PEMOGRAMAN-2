package pertemuan6;

import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {
    
    public static Connection getKoneksi() {
        Connection koneksi = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            koneksi = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mahasiswa?useSSL=false&serverTimezone=UTC",
                "root",
                ""
            );
            
            System.out.println("Koneksi Berhasil");
            
        } catch (Exception e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
        }
        
        return koneksi;
    }
}