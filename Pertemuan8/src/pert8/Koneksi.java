package pert8;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Koneksi Database MySQL
 * Pertemuan 8 – Deployment Installer Java
 */
public class Koneksi {

    private static final String HOST = "localhost";
    private static final String DB   = "dbnilai";
    private static final String USER = "root";
    private static final String PASS = "";   // isi jika MySQL pakai password

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + "/" + DB
                    + "?useSSL=false&serverTimezone=UTC",
                    USER, PASS);

        } catch (ClassNotFoundException e) {

            throw new RuntimeException(
                    "Driver MySQL tidak ditemukan!\n"
                    + "Tambahkan mysql-connector-j ke Libraries.", e);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Gagal koneksi ke database '" + DB + "':\n"
                    + e.getMessage(), e);

        }

    }

}
