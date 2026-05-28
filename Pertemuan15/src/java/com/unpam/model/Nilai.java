package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.unpam.view.PesanDialog;

/**
 * Nilai – Model Class Penilaian Mahasiswa
 * Pertemuan 15 – Form Transaksi Aplikasi Web
 */
public class Nilai {

    private String nim;
    private String kodeMataKuliah;
    private double nilaiUTS;
    private double nilaiUAS;
    private double nilaiTugas;
    private double nilaiAkhir;
    private String grade;
    private String pesan;
    private Object[][] list;

    private final Koneksi     koneksi     = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    // ── Getter & Setter ──────────────────────────────────────────────────────
    public String getNim()                 { return nim; }
    public void   setNim(String nim)       { this.nim = nim; }

    public String getKodeMataKuliah()                      { return kodeMataKuliah; }
    public void   setKodeMataKuliah(String k)              { this.kodeMataKuliah = k; }

    public double getNilaiUTS()                { return nilaiUTS; }
    public void   setNilaiUTS(double v)        { this.nilaiUTS = v; }

    public double getNilaiUAS()                { return nilaiUAS; }
    public void   setNilaiUAS(double v)        { this.nilaiUAS = v; }

    public double getNilaiTugas()              { return nilaiTugas; }
    public void   setNilaiTugas(double v)      { this.nilaiTugas = v; }

    public double     getNilaiAkhir()          { return nilaiAkhir; }
    public String     getGrade()               { return grade; }
    public String     getPesan()               { return pesan; }
    public Object[][] getList()                { return list; }
    public void       setList(Object[][] l)    { this.list = l; }

    // ── Hitung Nilai Akhir & Grade ────────────────────────────────────────────
    public void hitungNilaiAkhir() {
        nilaiAkhir = (nilaiUTS * 0.30) + (nilaiUAS * 0.40) + (nilaiTugas * 0.30);
        if      (nilaiAkhir >= 85) grade = "A";
        else if (nilaiAkhir >= 75) grade = "B";
        else if (nilaiAkhir >= 65) grade = "C";
        else if (nilaiAkhir >= 55) grade = "D";
        else                       grade = "E";
    }

    // ── Simpan ───────────────────────────────────────────────────────────────
    public boolean simpan() {
        boolean    adaKesalahan = false;
        Connection connection;
        hitungNilaiAkhir();

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "INSERT INTO tbnilai "
                       + "(nim, kode_matakuliah, nilai_uts, nilai_uas, nilai_tugas, nilai_akhir, grade) "
                       + "VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim); ps.setString(2, kodeMataKuliah);
                ps.setDouble(3, nilaiUTS); ps.setDouble(4, nilaiUAS);
                ps.setDouble(5, nilaiTugas); ps.setDouble(6, nilaiAkhir);
                ps.setString(7, grade);
                if (ps.executeUpdate() < 1) { adaKesalahan = true; pesan = "Gagal menyimpan data nilai"; }
                ps.close(); connection.close();
            } catch (SQLException ex) { adaKesalahan = true; pesan = "Error: " + ex.getMessage(); }
        } else { adaKesalahan = true; pesan = "Koneksi gagal\n" + koneksi.getPesanKesalahan(); }
        return !adaKesalahan;
    }

    // ── Update ────────────────────────────────────────────────────────────────
    public boolean update() {
        boolean    adaKesalahan = false;
        Connection connection;
        hitungNilaiAkhir();

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "UPDATE tbnilai SET nilai_uts=?, nilai_uas=?, nilai_tugas=?, nilai_akhir=?, grade=? "
                       + "WHERE nim=? AND kode_matakuliah=?";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setDouble(1, nilaiUTS); ps.setDouble(2, nilaiUAS);
                ps.setDouble(3, nilaiTugas); ps.setDouble(4, nilaiAkhir);
                ps.setString(5, grade); ps.setString(6, nim); ps.setString(7, kodeMataKuliah);
                if (ps.executeUpdate() < 1) { adaKesalahan = true; pesan = "Gagal update nilai"; }
                ps.close(); connection.close();
            } catch (SQLException ex) { adaKesalahan = true; pesan = "Error: " + ex.getMessage(); }
        } else { adaKesalahan = true; pesan = "Koneksi gagal\n" + koneksi.getPesanKesalahan(); }
        return !adaKesalahan;
    }

    // ── Hapus ────────────────────────────────────────────────────────────────
    public boolean hapus() {
        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM tbnilai WHERE nim=? AND kode_matakuliah=?");
                ps.setString(1, nim); ps.setString(2, kodeMataKuliah);
                ps.executeUpdate(); ps.close(); connection.close();
            } catch (SQLException ex) { adaKesalahan = true; pesan = "Gagal hapus: " + ex.getMessage(); }
        } else { adaKesalahan = true; pesan = "Koneksi gagal\n" + koneksi.getPesanKesalahan(); }
        return !adaKesalahan;
    }

    // ── Cari semua ────────────────────────────────────────────────────────────
    public boolean cariSemua() {
        boolean    adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            try {
                String sql = "SELECT n.nim, m.nama, n.kode_matakuliah, mk.nama_matakuliah, "
                           + "n.nilai_uts, n.nilai_uas, n.nilai_tugas, n.nilai_akhir, n.grade "
                           + "FROM tbnilai n "
                           + "JOIN tbmahasiswa m ON n.nim = m.nim "
                           + "JOIN tbmatakuliah mk ON n.kode_matakuliah = mk.kode_matakuliah "
                           + "ORDER BY n.nim";
                ResultSet rs = connection.createStatement().executeQuery(sql);
                java.util.List<Object[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("nim"), rs.getString("nama"),
                        rs.getString("kode_matakuliah"), rs.getString("nama_matakuliah"),
                        rs.getDouble("nilai_uts"), rs.getDouble("nilai_uas"),
                        rs.getDouble("nilai_tugas"), rs.getDouble("nilai_akhir"),
                        rs.getString("grade")
                    });
                }
                list = rows.toArray(new Object[0][]);
                rs.close(); connection.close();
            } catch (SQLException ex) { adaKesalahan = true; pesan = "Error: " + ex.getMessage(); }
        } else { adaKesalahan = true; pesan = "Koneksi gagal\n" + koneksi.getPesanKesalahan(); }
        return !adaKesalahan;
    }
}
