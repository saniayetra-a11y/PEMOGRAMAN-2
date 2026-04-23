package pertemuan6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Pertemuan6 extends JFrame {

    JTextField txtNim, txtNama, txtSemester, txtKelas, txtCari;
    JComboBox<String> cbCari;
    JButton btnSimpan, btnHapus, btnUpdate, btnBersih, btnCari;
    JTable table;
    DefaultTableModel model;

    public Pertemuan6() {
        setTitle("Data Mahasiswa");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // FORM
        add(new JLabel("NIM")).setBounds(20, 20, 100, 25);
        txtNim = new JTextField(); add(txtNim).setBounds(120, 20, 150, 25);

        add(new JLabel("Nama")).setBounds(20, 50, 100, 25);
        txtNama = new JTextField(); add(txtNama).setBounds(120, 50, 150, 25);

        add(new JLabel("Semester")).setBounds(20, 80, 100, 25);
        txtSemester = new JTextField(); add(txtSemester).setBounds(120, 80, 150, 25);

        add(new JLabel("Kelas")).setBounds(20, 110, 100, 25);
        txtKelas = new JTextField(); add(txtKelas).setBounds(120, 110, 150, 25);

        // SEARCH
        add(new JLabel("Cari")).setBounds(320, 20, 100, 25);
        txtCari = new JTextField(); add(txtCari).setBounds(420, 20, 150, 25);

        cbCari = new JComboBox<>(new String[]{"NIM","Nama","Semester","Kelas"});
        add(cbCari).setBounds(320, 50, 90, 25);

        btnCari = new JButton("Cari");
        add(btnCari).setBounds(420, 50, 150, 25);

        // BUTTON
        btnSimpan = new JButton("Simpan"); add(btnSimpan).setBounds(20, 150, 100, 25);
        btnHapus = new JButton("Hapus"); add(btnHapus).setBounds(130, 150, 100, 25);
        btnUpdate = new JButton("Update"); add(btnUpdate).setBounds(240, 150, 100, 25);
        btnBersih = new JButton("Bersih"); add(btnBersih).setBounds(350, 150, 100, 25);

        // TABLE
        model = new DefaultTableModel();
        model.addColumn("NIM");
        model.addColumn("Nama");
        model.addColumn("Semester");
        model.addColumn("Kelas");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        add(sp).setBounds(20, 200, 650, 200);

        tampilData();

        // EVENT
        btnSimpan.addActionListener(e -> simpanData());
        btnHapus.addActionListener(e -> hapusData());
        btnUpdate.addActionListener(e -> updateData());
        btnBersih.addActionListener(e -> bersih());
        btnCari.addActionListener(e -> cariData());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtNim.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtSemester.setText(model.getValueAt(row, 2).toString());
                txtKelas.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    // TAMPIL DATA
    void tampilData() {
        model.setRowCount(0);
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM datamhs");

            while (r.next()) {
                model.addRow(new Object[]{
                        r.getString("nim"),
                        r.getString("nama"),
                        r.getString("semester"),
                        r.getString("kelas")
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // SIMPAN
    void simpanData() {
        try {
            Connection c = koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement(
                    "INSERT INTO datamhs VALUES (?,?,?,?)");

            p.setString(1, txtNim.getText());
            p.setString(2, txtNama.getText());
            p.setString(3, txtSemester.getText());
            p.setString(4, txtKelas.getText());

            p.executeUpdate();
            tampilData();
            bersih();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // HAPUS
    void hapusData() {
        try {
            Connection c = koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement(
                    "DELETE FROM datamhs WHERE nim=?");

            p.setString(1, txtNim.getText());
            p.executeUpdate();
            tampilData();
            bersih();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // UPDATE
    void updateData() {
        try {
            Connection c = koneksi.getKoneksi();
            PreparedStatement p = c.prepareStatement(
                    "UPDATE datamhs SET nama=?, semester=?, kelas=? WHERE nim=?");

            p.setString(1, txtNama.getText());
            p.setString(2, txtSemester.getText());
            p.setString(3, txtKelas.getText());
            p.setString(4, txtNim.getText());

            p.executeUpdate();
            tampilData();
            bersih();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // 🔍 SEARCH PILIHAN
    void cariData() {
        if (txtCari.getText().equals("")) {
            tampilData();
            return;
        }

        model.setRowCount(0);

        try {
            Connection c = koneksi.getKoneksi();
            Statement st = c.createStatement();

            String pilih = cbCari.getSelectedItem().toString();
            String field = "";

            if (pilih.equals("NIM")) field = "nim";
            else if (pilih.equals("Nama")) field = "nama";
            else if (pilih.equals("Semester")) field = "semester";
            else if (pilih.equals("Kelas")) field = "kelas";

            ResultSet rs = st.executeQuery(
                "SELECT * FROM datamhs WHERE " + field +
                " LIKE '%" + txtCari.getText() + "%'"
            );

            boolean ada = false;

            while (rs.next()) {
                ada = true;
                model.addRow(new Object[]{
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getString("semester"),
                        rs.getString("kelas")
                });
            }

            if (!ada) {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // BERSIH
    void bersih() {
        txtNim.setText("");
        txtNama.setText("");
        txtSemester.setText("");
        txtKelas.setText("");
        txtCari.setText("");
    }

    public static void main(String[] args) {
        new Pertemuan6().setVisible(true);
    }
}