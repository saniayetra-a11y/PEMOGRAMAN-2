package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.unpam.view.PesanDialog;

/**
 * Mahasiswa – Model Class
 * Pertemuan 14 – Aplikasi Web MVC
 */
public class Mahasiswa {

    private String nim, nama, kelas, password;
    private int semester;
    private String pesan;
    private Object[][] list;

    private final Koneksi     koneksi     = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public String getNim()             { return nim; }
    public void   setNim(String nim)   { this.nim = nim; }

    public String getNama()              { return nama; }
    public void   setNama(String nama)   { this.nama = nama; }

    public String getKelas()               { return kelas; }
    public void   setKelas(String kelas)   { this.kelas = kelas; }

    public int  getSemester()                { return semester; }
    public void setSemester(int semester)    { this.semester = semester; }

    public String getPassword()                { return password; }
    public void   setPassword(String password) { this.password = password; }

    public String     getPesan()                    { return pesan; }
    public Object[][] getList()                     { return list; }
    public void       setList(Object[][] list)       { this.list = list; }

    // ── Simpan ───────────────────────────────────────────────────────────────
    public boolean simpan() {

        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {

            int    jumlahSimpan = 0;
            String SQLStatemen  = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "INSERT INTO tbmahasiswa "
                            + "(nim, nama, semester, kelas, password) "
                            + "VALUES (?,?,?,?,?)";

                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, nama);
                preparedStatement.setInt   (3, semester);
                preparedStatement.setString(4, kelas);
                preparedStatement.setString(5, password);

                jumlahSimpan = preparedStatement.executeUpdate();

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data mahasiswa";
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmahasiswa\n" + ex + "\n" + SQLStatemen;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // ── Update ────────────────────────────────────────────────────────────────
    public boolean update() {

        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {

            int    jumlahUpdate = 0;
            String SQLStatemen  = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "UPDATE tbmahasiswa SET "
                            + "nama=?, semester=?, kelas=?, password=? "
                            + "WHERE nim=?";

                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, nama);
                preparedStatement.setInt   (2, semester);
                preparedStatement.setString(3, kelas);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, nim);

                jumlahUpdate = preparedStatement.executeUpdate();

                if (jumlahUpdate < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal memperbaharui data mahasiswa";
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmahasiswa\n" + ex;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // ── Hapus ────────────────────────────────────────────────────────────────
    public boolean hapus() {

        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {

            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen       = "DELETE FROM tbmahasiswa WHERE nim=?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, nim);
                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Gagal menghapus data mahasiswa\n" + ex;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // ── Cari semua ────────────────────────────────────────────────────────────
    public boolean cariSemua() {

        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {

            try {
                String    sql = "SELECT nim, nama, semester, kelas FROM tbmahasiswa ORDER BY nim";
                ResultSet rs  = connection.createStatement().executeQuery(sql);

                java.util.List<Object[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getInt   ("semester"),
                        rs.getString("kelas")
                    });
                }
                list = rows.toArray(new Object[0][]);
                rs.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat mengambil data mahasiswa\n" + ex;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // ── Login ────────────────────────────────────────────────────────────────
    public boolean login() {

        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {

            try {
                String            sql = "SELECT nim, nama FROM tbmahasiswa WHERE nim=? AND password=?";
                PreparedStatement ps  = connection.prepareStatement(sql);
                ps.setString(1, nim);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    nama = rs.getString("nama");
                } else {
                    adaKesalahan = true;
                    pesan = "NIM atau Password salah!";
                }

                rs.close(); ps.close(); connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Gagal login\n" + ex;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
