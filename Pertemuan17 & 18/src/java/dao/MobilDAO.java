package dao;

import model.Mobil;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) untuk tabel mobil
 */
public class MobilDAO {

    // ── Ambil semua mobil ──────────────────────────────────────────────────
    public List<Mobil> getAll() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil ORDER BY merk, tipe";
        try (Connection conn = DBConnection.getConnection();
             Statement st   = conn.createStatement();
             ResultSet rs   = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // ── Ambil mobil yang tersedia saja ─────────────────────────────────────
    public List<Mobil> getTersedia() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil WHERE status = 'tersedia' ORDER BY merk";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // ── Ambil satu mobil berdasarkan ID ────────────────────────────────────
    public Mobil getById(int id) throws SQLException {
        String sql = "SELECT * FROM mobil WHERE id_mobil = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    // ── Tambah mobil baru ──────────────────────────────────────────────────
    public boolean insert(Mobil m) throws SQLException {
        String sql = "INSERT INTO mobil (nopol, merk, tipe, warna, tahun, harga_sewa, status, keterangan) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNopol());
            ps.setString(2, m.getMerk());
            ps.setString(3, m.getTipe());
            ps.setString(4, m.getWarna());
            ps.setInt   (5, m.getTahun());
            ps.setDouble(6, m.getHargaSewa());
            ps.setString(7, m.getStatus());
            ps.setString(8, m.getKeterangan());
            return ps.executeUpdate() > 0;
        }
    }

    // ── Update data mobil ──────────────────────────────────────────────────
    public boolean update(Mobil m) throws SQLException {
        String sql = "UPDATE mobil SET nopol=?, merk=?, tipe=?, warna=?, tahun=?, "
                   + "harga_sewa=?, status=?, keterangan=? WHERE id_mobil=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNopol());
            ps.setString(2, m.getMerk());
            ps.setString(3, m.getTipe());
            ps.setString(4, m.getWarna());
            ps.setInt   (5, m.getTahun());
            ps.setDouble(6, m.getHargaSewa());
            ps.setString(7, m.getStatus());
            ps.setString(8, m.getKeterangan());
            ps.setInt   (9, m.getIdMobil());
            return ps.executeUpdate() > 0;
        }
    }

    // ── Update status mobil saja ───────────────────────────────────────────
    public boolean updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE mobil SET status=? WHERE id_mobil=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt   (2, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── Hapus mobil ────────────────────────────────────────────────────────
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM mobil WHERE id_mobil=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── Mapping ResultSet ke objek Mobil ───────────────────────────────────
    private Mobil mapRow(ResultSet rs) throws SQLException {
        Mobil m = new Mobil();
        m.setIdMobil   (rs.getInt   ("id_mobil"));
        m.setNopol     (rs.getString("nopol"));
        m.setMerk      (rs.getString("merk"));
        m.setTipe      (rs.getString("tipe"));
        m.setWarna     (rs.getString("warna"));
        m.setTahun     (rs.getInt   ("tahun"));
        m.setHargaSewa (rs.getDouble("harga_sewa"));
        m.setStatus    (rs.getString("status"));
        m.setKeterangan(rs.getString("keterangan"));
        return m;
    }
}
