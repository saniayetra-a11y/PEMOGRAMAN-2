package dao;

import model.Transaksi;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) untuk tabel transaksi
 */
public class TransaksiDAO {

    // ── Ambil semua transaksi (join customer & mobil) ──────────────────────
    public List<Transaksi> getAll() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, c.nama AS nama_customer, "
                   + "m.nopol, m.merk, m.tipe, m.harga_sewa "
                   + "FROM transaksi t "
                   + "JOIN customer c ON t.id_customer = c.id_customer "
                   + "JOIN mobil m    ON t.id_mobil    = m.id_mobil "
                   + "ORDER BY t.tgl_sewa DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement st   = conn.createStatement();
             ResultSet rs   = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // ── Ambil transaksi aktif saja ─────────────────────────────────────────
    public List<Transaksi> getAktif() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, c.nama AS nama_customer, "
                   + "m.nopol, m.merk, m.tipe, m.harga_sewa "
                   + "FROM transaksi t "
                   + "JOIN customer c ON t.id_customer = c.id_customer "
                   + "JOIN mobil m    ON t.id_mobil    = m.id_mobil "
                   + "WHERE t.status = 'aktif' ORDER BY t.tgl_kembali_rencana";
        try (Connection conn = DBConnection.getConnection();
             Statement st   = conn.createStatement();
             ResultSet rs   = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // ── Ambil satu transaksi berdasarkan ID ────────────────────────────────
    public Transaksi getById(int id) throws SQLException {
        String sql = "SELECT t.*, c.nama AS nama_customer, "
                   + "m.nopol, m.merk, m.tipe, m.harga_sewa "
                   + "FROM transaksi t "
                   + "JOIN customer c ON t.id_customer = c.id_customer "
                   + "JOIN mobil m    ON t.id_mobil    = m.id_mobil "
                   + "WHERE t.id_transaksi = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    // ── Laporan berdasarkan rentang tanggal ───────────────────────────────
    public List<Transaksi> getLaporan(String tglAwal, String tglAkhir) throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.*, c.nama AS nama_customer, "
                   + "m.nopol, m.merk, m.tipe, m.harga_sewa "
                   + "FROM transaksi t "
                   + "JOIN customer c ON t.id_customer = c.id_customer "
                   + "JOIN mobil m    ON t.id_mobil    = m.id_mobil "
                   + "WHERE t.tgl_sewa BETWEEN ? AND ? "
                   + "ORDER BY t.tgl_sewa";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tglAwal);
            ps.setString(2, tglAkhir);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ── Buat transaksi baru (sewa) ─────────────────────────────────────────
    public boolean insert(Transaksi t) throws SQLException {
        String sql = "INSERT INTO transaksi "
                   + "(kode_transaksi, id_customer, id_mobil, tgl_sewa, "
                   + "tgl_kembali_rencana, lama_sewa, total_biaya, status, keterangan) "
                   + "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getKodeTransaksi());
            ps.setInt   (2, t.getIdCustomer());
            ps.setInt   (3, t.getIdMobil());
            ps.setString(4, t.getTglSewa());
            ps.setString(5, t.getTglKembaliRencana());
            ps.setInt   (6, t.getLamaSewa());
            ps.setDouble(7, t.getTotalBiaya());
            ps.setString(8, "aktif");
            ps.setString(9, t.getKeterangan());
            return ps.executeUpdate() > 0;
        }
    }

    // ── Proses pengembalian mobil ──────────────────────────────────────────
    public boolean prosesPengembalian(int idTransaksi, String tglAktual, double denda) throws SQLException {
        String sql = "UPDATE transaksi SET tgl_kembali_aktual=?, denda=?, status='selesai' "
                   + "WHERE id_transaksi=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tglAktual);
            ps.setDouble(2, denda);
            ps.setInt   (3, idTransaksi);
            return ps.executeUpdate() > 0;
        }
    }

    // ── Generate kode transaksi otomatis ──────────────────────────────────
    public String generateKode() throws SQLException {
        String tgl  = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sql  = "SELECT COUNT(*) FROM transaksi WHERE kode_transaksi LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "TRX" + tgl + "%");
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                int urut = rs.getInt(1) + 1;
                return String.format("TRX%s%03d", tgl, urut);
            }
        }
    }

    // ── Hitung denda keterlambatan ─────────────────────────────────────────
    public double hitungDenda(String tglRencana, String tglAktual, double hargaSewa) {
        try {
            LocalDate rencana = LocalDate.parse(tglRencana);
            LocalDate aktual  = LocalDate.parse(tglAktual);
            long telat = aktual.toEpochDay() - rencana.toEpochDay();
            if (telat > 0) return telat * hargaSewa * 0.5; // denda 50% per hari
        } catch (Exception e) { /* abaikan */ }
        return 0;
    }

    // ── Mapping ResultSet ke objek Transaksi ──────────────────────────────
    private Transaksi mapRow(ResultSet rs) throws SQLException {
        Transaksi t = new Transaksi();
        t.setIdTransaksi      (rs.getInt   ("id_transaksi"));
        t.setKodeTransaksi    (rs.getString("kode_transaksi"));
        t.setIdCustomer       (rs.getInt   ("id_customer"));
        t.setIdMobil          (rs.getInt   ("id_mobil"));
        t.setTglSewa          (rs.getString("tgl_sewa"));
        t.setTglKembaliRencana(rs.getString("tgl_kembali_rencana"));
        t.setTglKembaliAktual (rs.getString("tgl_kembali_aktual"));
        t.setLamaSewa         (rs.getInt   ("lama_sewa"));
        t.setTotalBiaya       (rs.getDouble("total_biaya"));
        t.setDenda            (rs.getDouble("denda"));
        t.setStatus           (rs.getString("status"));
        t.setKeterangan       (rs.getString("keterangan"));
        // join fields
        t.setNamaCustomer     (rs.getString("nama_customer"));
        t.setNopolMobil       (rs.getString("nopol"));
        t.setMerkMobil        (rs.getString("merk"));
        t.setTipeMobil        (rs.getString("tipe"));
        t.setHargaSewa        (rs.getDouble("harga_sewa"));
        return t;
    }
}
