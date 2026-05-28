package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Koneksi – Database Connection
 * Pertemuan 14 – Aplikasi Web MVC
 */
public class Koneksi {

    private static final String driver   = "com.mysql.cj.jdbc.Driver";
    private static final String database = "jdbc:mysql://localhost:3306/dbaplikasipenilaianmahasiswa";
    private static final String user     = "root";
    private static final String password = "";   // ganti jika MySQL pakai password

    private Connection connection;
    private String pesanKesalahan;

    public String getPesanKesalahan() {
        return pesanKesalahan;
    }

    public Connection getConnection() {

        connection      = null;
        pesanKesalahan  = "";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "JDBC Driver tidak ditemukan\n" + ex;
        }

        if (pesanKesalahan.equals("")) {
            try {
                connection = DriverManager.getConnection(
                        database + "?useSSL=false&serverTimezone=UTC",
                        user, password);
            } catch (SQLException ex) {
                pesanKesalahan = "Koneksi ke " + database + " gagal\n" + ex;
            }
        }

        return connection;
    }
}
