package dao;

import model.Customer;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) untuk tabel customer
 */
public class CustomerDAO {

    public List<Customer> getAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY nama";
        try (Connection conn = DBConnection.getConnection();
             Statement st   = conn.createStatement();
             ResultSet rs   = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public Customer getById(int id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public boolean insert(Customer c) throws SQLException {
        String sql = "INSERT INTO customer (nik, nama, alamat, no_telp, email) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNik());
            ps.setString(2, c.getNama());
            ps.setString(3, c.getAlamat());
            ps.setString(4, c.getNoTelp());
            ps.setString(5, c.getEmail());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Customer c) throws SQLException {
        String sql = "UPDATE customer SET nik=?, nama=?, alamat=?, no_telp=?, email=? WHERE id_customer=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNik());
            ps.setString(2, c.getNama());
            ps.setString(3, c.getAlamat());
            ps.setString(4, c.getNoTelp());
            ps.setString(5, c.getEmail());
            ps.setInt   (6, c.getIdCustomer());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id_customer=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setIdCustomer(rs.getInt   ("id_customer"));
        c.setNik       (rs.getString("nik"));
        c.setNama      (rs.getString("nama"));
        c.setAlamat    (rs.getString("alamat"));
        c.setNoTelp    (rs.getString("no_telp"));
        c.setEmail     (rs.getString("email"));
        c.setTglDaftar (rs.getString("tgl_daftar"));
        return c;
    }
}
