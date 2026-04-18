package pertemuan5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Pertemuan5 extends JFrame {
    
    JTextField txtNim, txtNama, txtSemester, txtKelas;
    JButton btnSimpan, btnHapus, btnUpdate, btnBersih;
    JTable table;
    DefaultTableModel model;

    public Pertemuan5() {
        setTitle("Data Mahasiswa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Label & TextField
        add(new JLabel("NIM")).setBounds(20, 20, 100, 25);
        txtNim = new JTextField(); add(txtNim).setBounds(120, 20, 150, 25);

        add(new JLabel("Nama")).setBounds(20, 50, 100, 25);
        txtNama = new JTextField(); add(txtNama).setBounds(120, 50, 150, 25);

        add(new JLabel("Semester")).setBounds(20, 80, 100, 25);
        txtSemester = new JTextField(); add(txtSemester).setBounds(120, 80, 150, 25);

        add(new JLabel("Kelas")).setBounds(20, 110, 100, 25);
        txtKelas = new JTextField(); add(txtKelas).setBounds(120, 110, 150, 25);

        // Tombol
        btnSimpan = new JButton("Simpan"); add(btnSimpan).setBounds(300, 20, 100, 25);
        btnHapus = new JButton("Hapus"); add(btnHapus).setBounds(300, 50, 100, 25);
        btnUpdate = new JButton("Update"); add(btnUpdate).setBounds(300, 80, 100, 25);
        btnBersih = new JButton("Bersih"); add(btnBersih).setBounds(300, 110, 100, 25);

        // JTable
        model = new DefaultTableModel();
        model.addColumn("NIM");
        model.addColumn("Nama");
        model.addColumn("Semester");
        model.addColumn("Kelas");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        add(sp).setBounds(20, 150, 550, 200);

        tampilData();

        // EVENT SIMPAN
        btnSimpan.addActionListener(e -> simpanData());

        // EVENT HAPUS
        btnHapus.addActionListener(e -> hapusData());

        // EVENT UPDATE
        btnUpdate.addActionListener(e -> updateData());

        // EVENT BERSIH
        btnBersih.addActionListener(e -> bersih());

        // Klik tabel
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
            System.out.println(e.getMessage());
        }
    }

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
            System.out.println(e.getMessage());
        }
    }

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
            System.out.println(e.getMessage());
        }
    }

    void bersih() {
        txtNim.setText("");
        txtNama.setText("");
        txtSemester.setText("");
        txtKelas.setText("");
    }

    public static void main(String[] args) {
        new Pertemuan5().setVisible(true);
    }
}