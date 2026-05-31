package penjualan;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormTransaksi extends JFrame {

    private JTextField  txtNoNota, txtTanggal, txtTotalBayar, txtBayar, txtKembalian;
    private JComboBox<String> cbCustomer, cbBarang;
    private JTextField  txtQty, txtHargaSatuan, txtSubtotal;
    private JButton     btnTambahItem, btnHapusItem, btnSimpanTransaksi, btnBersih;
    private JTable      tabelItem;
    private DefaultTableModel modelItem;
    private JLabel      lblTotal;

    public FormTransaksi() {
        setTitle("Transaksi Penjualan");
        setSize(850, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadCustomer();
        loadBarang();
        generateNoNota();
    }

    private void initComponents() {

        Font fLabel = new Font("SansSerif", Font.BOLD, 12);
        Font fField = new Font("SansSerif", Font.PLAIN, 12);
        Color bg = Color.WHITE;

        // ── Panel Header Transaksi ────────────────────────────────────────────
        JPanel pnlHeader = new JPanel(new GridBagLayout());
        pnlHeader.setBorder(BorderFactory.createTitledBorder("Header Transaksi"));
        pnlHeader.setBackground(bg);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 10, 4, 10);
        g.anchor = GridBagConstraints.WEST;

        txtNoNota   = new JTextField(15); txtNoNota.setFont(fField); txtNoNota.setEditable(false);
        txtNoNota.setBackground(new Color(230,230,230));
        txtTanggal  = new JTextField(15); txtTanggal.setFont(fField); txtTanggal.setEditable(false);
        txtTanggal.setBackground(new Color(230,230,230));
        txtTanggal.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        cbCustomer  = new JComboBox<>(); cbCustomer.setFont(fField);

        String[][] rows = {{"No Nota :", "0"}, {"Tanggal :", "1"}, {"Customer :", "2"}};
        JComponent[] comps = {txtNoNota, txtTanggal, cbCustomer};
        for (int i = 0; i < 3; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0;
            JLabel l = new JLabel(rows[i][0]); l.setFont(fLabel); pnlHeader.add(l, g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            pnlHeader.add(comps[i], g);
        }

        // ── Panel Item ────────────────────────────────────────────────────────
        JPanel pnlItem = new JPanel(new GridBagLayout());
        pnlItem.setBorder(BorderFactory.createTitledBorder("Tambah Item"));
        pnlItem.setBackground(bg);
        GridBagConstraints gi = new GridBagConstraints();
        gi.insets = new Insets(4, 10, 4, 10);
        gi.anchor = GridBagConstraints.WEST;

        cbBarang      = new JComboBox<>(); cbBarang.setFont(fField);
        txtQty        = new JTextField(8); txtQty.setFont(fField);
        txtHargaSatuan= new JTextField(10); txtHargaSatuan.setFont(fField); txtHargaSatuan.setEditable(false);
        txtHargaSatuan.setBackground(new Color(230,230,230));
        txtSubtotal   = new JTextField(10); txtSubtotal.setFont(fField); txtSubtotal.setEditable(false);
        txtSubtotal.setBackground(new Color(230,230,230));

        String[] itemLbls = {"Barang :", "Qty :", "Harga Satuan :", "Subtotal :"};
        JComponent[] itemComps = {cbBarang, txtQty, txtHargaSatuan, txtSubtotal};
        for (int i = 0; i < 4; i++) {
            gi.gridx = 0; gi.gridy = i; gi.weightx = 0; gi.fill = GridBagConstraints.NONE;
            JLabel l = new JLabel(itemLbls[i]); l.setFont(fLabel); pnlItem.add(l, gi);
            gi.gridx = 1; gi.weightx = 1; gi.fill = GridBagConstraints.HORIZONTAL;
            pnlItem.add(itemComps[i], gi);
        }

        btnTambahItem = new JButton("+ Tambah Item");
        btnHapusItem  = new JButton("- Hapus Item");
        btnTambahItem.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnHapusItem .setFont(new Font("SansSerif", Font.BOLD, 12));

        gi.gridx = 0; gi.gridy = 4; gi.gridwidth = 2; gi.fill = GridBagConstraints.NONE;
        JPanel pnlItemBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlItemBtn.setBackground(bg);
        pnlItemBtn.add(btnTambahItem); pnlItemBtn.add(btnHapusItem);
        pnlItem.add(pnlItemBtn, gi);

        // ── Tabel Item ────────────────────────────────────────────────────────
        modelItem = new DefaultTableModel(
            new String[]{"Kode Barang", "Nama Barang", "Qty", "Harga Satuan", "Subtotal"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelItem = new JTable(modelItem);
        tabelItem.setFont(fField); tabelItem.setRowHeight(22);
        tabelItem.getTableHeader().setFont(fLabel);
        JScrollPane scrollItem = new JScrollPane(tabelItem);
        scrollItem.setPreferredSize(new Dimension(800, 150));
        scrollItem.setBorder(BorderFactory.createTitledBorder("Daftar Item"));

        // ── Panel Pembayaran ──────────────────────────────────────────────────
        JPanel pnlBayar = new JPanel(new GridBagLayout());
        pnlBayar.setBorder(BorderFactory.createTitledBorder("Pembayaran"));
        pnlBayar.setBackground(bg);
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(4, 10, 4, 10);
        gb.anchor = GridBagConstraints.WEST;

        txtTotalBayar = new JTextField(12); txtTotalBayar.setFont(fField); txtTotalBayar.setEditable(false);
        txtTotalBayar.setBackground(new Color(230,230,230));
        txtBayar      = new JTextField(12); txtBayar.setFont(fField);
        txtKembalian  = new JTextField(12); txtKembalian.setFont(fField); txtKembalian.setEditable(false);
        txtKembalian.setBackground(new Color(230,230,230));
        lblTotal = new JLabel("Total: Rp 0");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTotal.setForeground(new Color(192, 57, 43));

        String[] bayarLbls = {"Total Belanja :", "Uang Bayar :", "Kembalian :"};
        JTextField[] bayarFields = {txtTotalBayar, txtBayar, txtKembalian};
        for (int i = 0; i < 3; i++) {
            gb.gridx = 0; gb.gridy = i; gb.weightx = 0;
            JLabel l = new JLabel(bayarLbls[i]); l.setFont(fLabel); pnlBayar.add(l, gb);
            gb.gridx = 1; gb.weightx = 1; gb.fill = GridBagConstraints.HORIZONTAL;
            pnlBayar.add(bayarFields[i], gb);
        }

        btnSimpanTransaksi = new JButton("SIMPAN TRANSAKSI");
        btnBersih          = new JButton("BERSIH");
        btnSimpanTransaksi.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnBersih.setFont(new Font("SansSerif", Font.BOLD, 12));

        JPanel pnlSimpan = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        pnlSimpan.setBackground(bg);
        pnlSimpan.add(btnSimpanTransaksi); pnlSimpan.add(btnBersih);

        // ── Kiri: header + item panel | Kanan: tabel + bayar ─────────────────
        JPanel pnlKiri = new JPanel(new BorderLayout(0, 5));
        pnlKiri.setBackground(bg);
        pnlKiri.add(pnlHeader, BorderLayout.NORTH);
        pnlKiri.add(pnlItem,   BorderLayout.CENTER);

        JPanel pnlKanan = new JPanel(new BorderLayout(0, 5));
        pnlKanan.setBackground(bg);
        pnlKanan.add(pnlBayar,  BorderLayout.CENTER);
        pnlKanan.add(pnlSimpan, BorderLayout.SOUTH);

        JPanel pnlTengah = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlTengah.setBackground(bg);
        pnlTengah.add(pnlKiri);
        pnlTengah.add(pnlKanan);

        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(bg);
        add(pnlTengah,  BorderLayout.NORTH);
        add(scrollItem, BorderLayout.CENTER);

        // ── Events ────────────────────────────────────────────────────────────
        cbBarang.addActionListener(e -> loadHargaBarang());
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { hitungSubtotal(); }
        });
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { hitungKembalian(); }
        });
        btnTambahItem.addActionListener(e -> tambahItem());
        btnHapusItem .addActionListener(e -> hapusItem());
        btnSimpanTransaksi.addActionListener(e -> simpanTransaksi());
        btnBersih.addActionListener(e -> bersih());
    }

    private void generateNoNota() {
        try (Connection c = Koneksi.getConnection();
             ResultSet rs = c.createStatement().executeQuery(
                "SELECT COUNT(*)+1 AS no FROM transaksi")) {
            if (rs.next())
                txtNoNota.setText(String.format("TRX%05d", rs.getInt("no")));
        } catch (Exception ex) { txtNoNota.setText("TRX00001"); }
    }

    private void loadCustomer() {
        cbCustomer.removeAllItems();
        try (Connection c = Koneksi.getConnection();
             ResultSet rs = c.createStatement().executeQuery(
                "SELECT kode_customer, nama_customer FROM customer ORDER BY nama_customer")) {
            while (rs.next())
                cbCustomer.addItem(rs.getString("kode_customer") + " - " + rs.getString("nama_customer"));
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }

    private void loadBarang() {
        cbBarang.removeAllItems();
        try (Connection c = Koneksi.getConnection();
             ResultSet rs = c.createStatement().executeQuery(
                "SELECT kode_barang, nama_barang FROM barang WHERE stok > 0 ORDER BY nama_barang")) {
            while (rs.next())
                cbBarang.addItem(rs.getString("kode_barang") + " - " + rs.getString("nama_barang"));
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }

    private void loadHargaBarang() {
        if (cbBarang.getSelectedItem() == null) return;
        String kode = cbBarang.getSelectedItem().toString().split(" - ")[0];
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT harga FROM barang WHERE kode_barang=?")) {
            ps.setString(1, kode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) txtHargaSatuan.setText(String.valueOf(rs.getDouble("harga")));
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        hitungSubtotal();
    }

    private void hitungSubtotal() {
        try {
            double harga = Double.parseDouble(txtHargaSatuan.getText().trim());
            int    qty   = Integer.parseInt(txtQty.getText().trim());
            txtSubtotal.setText(String.valueOf(harga * qty));
        } catch (NumberFormatException ignored) { txtSubtotal.setText(""); }
    }

    private void hitungKembalian() {
        try {
            String strTotal = txtTotalBayar.getText().trim().replace(",", ".");
            String strBayar = txtBayar.getText().trim().replace(",", ".");
            double total = Double.parseDouble(strTotal);
            double bayar = Double.parseDouble(strBayar);
            txtKembalian.setText(String.format("%.0f", bayar - total));
        } catch (NumberFormatException ignored) { txtKembalian.setText(""); }
    }

    private void tambahItem() {
        if (cbBarang.getSelectedItem() == null || txtQty.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih barang dan isi qty!"); return;
        }
        String kode = cbBarang.getSelectedItem().toString().split(" - ")[0];
        String nama = cbBarang.getSelectedItem().toString().split(" - ")[1];
        try {
            double harga    = Double.parseDouble(txtHargaSatuan.getText().trim());
            int    qty      = Integer.parseInt(txtQty.getText().trim());
            double subtotal = harga * qty;
            modelItem.addRow(new Object[]{kode, nama, qty, harga, subtotal});
            hitungTotal();
            txtQty.setText(""); txtHargaSatuan.setText(""); txtSubtotal.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty harus angka!");
        }
    }

    private void hapusItem() {
        int row = tabelItem.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Pilih item dulu!"); return; }
        modelItem.removeRow(row);
        hitungTotal();
    }

    private void hitungTotal() {
        double total = 0;
        for (int i = 0; i < modelItem.getRowCount(); i++)
            total += Double.parseDouble(modelItem.getValueAt(i, 4).toString());
        txtTotalBayar.setText(String.valueOf((long)total));
        lblTotal.setText("Total: Rp " + String.format("%.2f", total));
        hitungKembalian();
    }

    private void simpanTransaksi() {
        if (modelItem.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tambahkan item dulu!"); return;
        }

        // Validasi uang bayar
        if (txtBayar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi Uang Bayar dulu!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            txtBayar.requestFocus();
            return;
        }

        double total = 0;
        double bayar = 0;
        try {
            // Ganti koma dengan titik agar kompatibel semua locale
            String strTotal = txtTotalBayar.getText().trim().replace(",", ".");
            String strBayar = txtBayar.getText().trim().replace(",", ".");
            total = Double.parseDouble(strTotal);
            bayar = Double.parseDouble(strBayar);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Uang Bayar harus berupa angka!\nContoh: 100000",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (bayar < total) {
            JOptionPane.showMessageDialog(this,
                    "Uang bayar kurang!\nTotal: " + total + "\nBayar: " + bayar,
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hitung & tampilkan kembalian
        txtKembalian.setText(String.format("%.0f", bayar - total));

        String kodeCustomer = cbCustomer.getSelectedItem().toString().split(" - ")[0];
        String noNota       = txtNoNota.getText().trim();
        String tanggal      = txtTanggal.getText().trim();

        try (Connection c = Koneksi.getConnection()) {
            c.setAutoCommit(false);
            // Simpan header transaksi
            PreparedStatement ps1 = c.prepareStatement(
                "INSERT INTO transaksi (no_nota, tanggal, kode_customer, total_bayar) VALUES (?,?,?,?)");
            ps1.setString(1, noNota); ps1.setString(2, tanggal);
            ps1.setString(3, kodeCustomer); ps1.setDouble(4, total);
            ps1.executeUpdate();

            // Simpan detail & update stok
            for (int i = 0; i < modelItem.getRowCount(); i++) {
                String kodeBarang = modelItem.getValueAt(i, 0).toString();
                int    qty        = Integer.parseInt(modelItem.getValueAt(i, 2).toString());
                double harga      = Double.parseDouble(modelItem.getValueAt(i, 3).toString());
                double subtotal   = Double.parseDouble(modelItem.getValueAt(i, 4).toString());

                PreparedStatement ps2 = c.prepareStatement(
                    "INSERT INTO detail_transaksi (no_nota, kode_barang, qty, harga_satuan, subtotal) VALUES (?,?,?,?,?)");
                ps2.setString(1, noNota); ps2.setString(2, kodeBarang);
                ps2.setInt(3, qty); ps2.setDouble(4, harga); ps2.setDouble(5, subtotal);
                ps2.executeUpdate();

                PreparedStatement ps3 = c.prepareStatement(
                    "UPDATE barang SET stok = stok - ? WHERE kode_barang = ?");
                ps3.setInt(1, qty); ps3.setString(2, kodeBarang);
                ps3.executeUpdate();
            }
            c.commit();
            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!\nNo Nota: " + noNota);
            bersih();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bersih() {
        modelItem.setRowCount(0);
        txtQty.setText(""); txtHargaSatuan.setText(""); txtSubtotal.setText("");
        txtTotalBayar.setText(""); txtBayar.setText(""); txtKembalian.setText("");
        generateNoNota(); loadBarang();
    }
}
