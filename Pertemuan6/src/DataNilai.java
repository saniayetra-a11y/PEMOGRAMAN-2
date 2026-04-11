import java.sql.*;

public class DataNilai {

    // 🔍 CARI DATA
    public void cariData(String nama) {
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT * FROM datanil WHERE nama LIKE '%" + nama + "%'"
            );

            if (rs.next()) {
                System.out.println("NIM: " + rs.getString("nim"));
                System.out.println("Nama: " + rs.getString("nama"));
                System.out.println("Nilai 1: " + rs.getString("nil1"));
                System.out.println("Nilai 2: " + rs.getString("nil2"));
            } else {
                System.out.println("Data tidak ditemukan");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ✏️ UPDATE DATA
    public void updateData(String nim, int nil1, int nil2) {
        try {
            Connection conn = Koneksi.getKoneksi();

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE datanil SET nil1=?, nil2=? WHERE nim=?"
            );

            ps.setInt(1, nil1);
            ps.setInt(2, nil2);
            ps.setString(3, nim);

            ps.executeUpdate();
            System.out.println("Data berhasil diupdate");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 🗑️ DELETE DATA
    public void hapusData(String nim) {
        try {
            Connection conn = Koneksi.getKoneksi();

            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM datanil WHERE nim=?"
            );

            ps.setString(1, nim);
            ps.executeUpdate();

            System.out.println("Data berhasil dihapus");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
