package penjualan;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormCustomer extends JFrame {

    private JTextField txtKode, txtNama, txtAlamat, txtTelp, txtEmail;
    private JButton btnSimpan, btnUpdate, btnHapus, btnBersih;
    private JTable tabel;
    private DefaultTableModel model;

    public FormCustomer() {
        setTitle("Data Customer");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        tampilData();
    }

    private void initComponents() {

        Font fLabel = new Font("SansSerif", Font.BOLD, 12);
        Font fField = new Font("SansSerif", Font.PLAIN, 12);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Input Data Customer"));
        pnlForm.setBackground(Color.WHITE);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 10, 5, 10);
        g.anchor = GridBagConstraints.WEST;

        String[] lbls = {"Kode Customer :", "Nama Customer :", "Alamat :", "Telepon :", "Email :"};
        txtKode   = new JTextField(20); txtKode.setFont(fField);
        txtNama   = new JTextField(20); txtNama.setFont(fField);
        txtAlamat = new JTextField(20); txtAlamat.setFont(fField);
        txtTelp   = new JTextField(20); txtTelp.setFont(fField);
        txtEmail  = new JTextField(20); txtEmail.setFont(fField);
        JTextField[] fields = {txtKode, txtNama, txtAlamat, txtTelp, txtEmail};

        for (int i = 0; i < lbls.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            JLabel l = new JLabel(lbls[i]); l.setFont(fLabel);
            pnlForm.add(l, g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            pnlForm.add(fields[i], g);
        }

        btnSimpan = new JButton("SIMPAN");
        btnUpdate = new JButton("UPDATE");
        btnHapus  = new JButton("HAPUS");
        btnBersih = new JButton("BERSIH");

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        pnlBtn.setBackground(Color.WHITE);
        for (JButton b : new JButton[]{btnSimpan, btnUpdate, btnHapus, btnBersih}) {
            b.setFont(new Font("SansSerif", Font.BOLD, 12));
            b.setPreferredSize(new Dimension(100, 30));
            pnlBtn.add(b);
        }

        model = new DefaultTableModel(
            new String[]{"Kode", "Nama", "Alamat", "Telepon", "Email"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabel = new JTable(model);
        tabel.setFont(fField);
        tabel.setRowHeight(22);
        tabel.getTableHeader().setFont(fLabel);
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { isiForm(); }
        });

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(BorderFactory.createTitledBorder("Daftar Customer"));

        JPanel pnlAtas = new JPanel(new BorderLayout());
        pnlAtas.setBackground(Color.WHITE);
        pnlAtas.add(pnlForm, BorderLayout.CENTER);
        pnlAtas.add(pnlBtn,  BorderLayout.SOUTH);

        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(Color.WHITE);
        add(pnlAtas, BorderLayout.NORTH);
        add(scroll,  BorderLayout.CENTER);

        btnSimpan.addActionListener(e -> simpan());
        btnUpdate.addActionListener(e -> update());
        btnHapus .addActionListener(e -> hapus());
        btnBersih.addActionListener(e -> bersih());
    }

    private void tampilData() {
        model.setRowCount(0);
        try (Connection c = Koneksi.getConnection();
             ResultSet rs = c.createStatement().executeQuery(
                "SELECT kode_customer, nama_customer, alamat, telepon, email FROM customer ORDER BY kode_customer")) {
            while (rs.next())
                model.addRow(new Object[]{
                    rs.getString("kode_customer"), rs.getString("nama_customer"),
                    rs.getString("alamat"), rs.getString("telepon"), rs.getString("email")});
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpan() {
        String sql = "INSERT INTO customer (kode_customer,nama_customer,alamat,telepon,email) VALUES (?,?,?,?,?)";
        try (Connection c = Koneksi.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, txtKode.getText().trim());
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, txtAlamat.getText().trim());
            ps.setString(4, txtTelp.getText().trim());
            ps.setString(5, txtEmail.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            bersih(); tampilData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update() {
        String sql = "UPDATE customer SET nama_customer=?,alamat=?,telepon=?,email=? WHERE kode_customer=?";
        try (Connection c = Koneksi.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, txtNama.getText().trim());
            ps.setString(2, txtAlamat.getText().trim());
            ps.setString(3, txtTelp.getText().trim());
            ps.setString(4, txtEmail.getText().trim());
            ps.setString(5, txtKode.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            bersih(); tampilData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapus() {
        if (txtKode.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Pilih data dulu!"); return; }
        if (JOptionPane.showConfirmDialog(this, "Hapus customer ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM customer WHERE kode_customer=?")) {
            ps.setString(1, txtKode.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            bersih(); tampilData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void isiForm() {
        int row = tabel.getSelectedRow();
        if (row < 0) return;
        txtKode  .setText(model.getValueAt(row, 0).toString());
        txtNama  .setText(model.getValueAt(row, 1).toString());
        txtAlamat.setText(model.getValueAt(row, 2).toString());
        txtTelp  .setText(model.getValueAt(row, 3).toString());
        txtEmail .setText(model.getValueAt(row, 4).toString());
    }

    private void bersih() {
        txtKode.setText(""); txtNama.setText(""); txtAlamat.setText("");
        txtTelp.setText(""); txtEmail.setText(""); tabel.clearSelection();
    }
}
