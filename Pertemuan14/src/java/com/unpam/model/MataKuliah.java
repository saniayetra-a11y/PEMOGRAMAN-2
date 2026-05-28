package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.unpam.view.PesanDialog;

/**
 * MataKuliah – Model Class
 * Pertemuan 14 – Aplikasi Web MVC
 */
public class MataKuliah {

    private String kodeMataKuliah, namaMataKuliah;
    private int    jumlahSks;
    private String pesan;
    private Object[][] list;

    private final Koneksi     koneksi     = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public String getKodeMataKuliah()                        { return kodeMataKuliah; }
    public void   setKodeMataKuliah(String kodeMataKuliah)   { this.kodeMataKuliah = kodeMataKuliah; }

    public String getNamaMataKuliah()                        { return namaMataKuliah; }
    public void   setNamaMataKuliah(String namaMataKuliah)   { this.namaMataKuliah = namaMataKuliah; }

    public int  getJumlahSks()                 { return jumlahSks; }
    public void setJumlahSks(int jumlahSks)    { this.jumlahSks = jumlahSks; }

    public String     getPesan()               { return pesan; }
    public Object[][] getList()                { return list; }
    public void       setList(Object[][] list) { this.list = list; }

    // ── Simpan ───────────────────────────────────────────────────────────────
    public boolean simpan() {

        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {

            String SQLStatemen = "";

            try {
                SQLStatemen = "INSERT INTO tbmatakuliah "
                            + "(kode_matakuliah, nama_matakuliah, jumlah_sks) "
                            + "VALUES (?,?,?)";

                PreparedStatement ps = connection.prepareStatement(SQLStatemen);
                ps.setString(1, kodeMataKuliah);
                ps.setString(2, namaMataKuliah);
                ps.setInt   (3, jumlahSks);

                int jumlahSimpan = ps.executeUpdate();

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data mata kuliah";
                }

                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmatakuliah\n" + ex + "\n" + SQLStatemen;
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

            try {
                String sql = "UPDATE tbmatakuliah SET "
                           + "nama_matakuliah=?, jumlah_sks=? "
                           + "WHERE kode_matakuliah=?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, namaMataKuliah);
                ps.setInt   (2, jumlahSks);
                ps.setString(3, kodeMataKuliah);

                int rows = ps.executeUpdate();

                if (rows < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal memperbaharui data mata kuliah";
                }

                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Gagal update mata kuliah\n" + ex;
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

            try {
                PreparedStatement ps = connection.prepareStatement(
                        "DELETE FROM tbmatakuliah WHERE kode_matakuliah=?");
                ps.setString(1, kodeMataKuliah);
                ps.executeUpdate();
                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Gagal menghapus mata kuliah\n" + ex;
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
                ResultSet rs = connection.createStatement().executeQuery(
                        "SELECT kode_matakuliah, nama_matakuliah, jumlah_sks FROM tbmatakuliah ORDER BY kode_matakuliah");

                java.util.List<Object[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("kode_matakuliah"),
                        rs.getString("nama_matakuliah"),
                        rs.getInt   ("jumlah_sks")
                    });
                }
                list = rows.toArray(new Object[0][]);
                rs.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat mengambil data mata kuliah\n" + ex;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
