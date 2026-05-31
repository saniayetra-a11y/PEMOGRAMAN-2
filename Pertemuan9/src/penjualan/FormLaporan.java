package penjualan;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.*;

public class FormLaporan extends JFrame {

    private JTable tabelTransaksi, tabelDetail;
    private DefaultTableModel modelTransaksi, modelDetail;
    private JTextField txtDari, txtSampai;
    private JLabel lblTotal;

    public FormLaporan() {
        setTitle("Laporan Transaksi Penjualan");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        tampilSemuaTransaksi();
    }

    private void initComponents() {

        Font fLabel = new Font("SansSerif", Font.BOLD, 12);
        Font fField = new Font("SansSerif", Font.PLAIN, 12);
        Color bg = Color.WHITE;

        // ── Header ────────────────────────────────────────────────────────────
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(192, 57, 43));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        JLabel lblJudul = new JLabel("LAPORAN TRANSAKSI PENJUALAN");
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblJudul.setForeground(Color.WHITE);
        pnlHeader.add(lblJudul, BorderLayout.WEST);

        // ── Filter Tanggal ────────────────────────────────────────────────────
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pnlFilter.setBackground(bg);
        pnlFilter.setBorder(BorderFactory.createTitledBorder("Filter Tanggal"));

        txtDari   = new JTextField(10); txtDari.setFont(fField);
        txtSampai = new JTextField(10); txtSampai.setFont(fField);

        JButton btnFilter = new JButton("Tampilkan");
        JButton btnSemua  = new JButton("Semua");
        JButton btnCetak  = new JButton("Cetak (Print)");
        btnFilter.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnSemua .setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCetak .setFont(new Font("SansSerif", Font.BOLD, 12));

        lblTotal = new JLabel("Total Transaksi: Rp 0");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTotal.setForeground(new Color(192, 57, 43));

        pnlFilter.add(new JLabel("Dari:")); pnlFilter.add(txtDari);
        pnlFilter.add(new JLabel("Sampai:")); pnlFilter.add(txtSampai);
        pnlFilter.add(btnFilter); pnlFilter.add(btnSemua);
        pnlFilter.add(new JSeparator(SwingConstants.VERTICAL));
        pnlFilter.add(btnCetak);
        pnlFilter.add(lblTotal);

        // ── Tabel Transaksi ───────────────────────────────────────────────────
        modelTransaksi = new DefaultTableModel(
            new String[]{"No Nota", "Tanggal", "Customer", "Total Bayar"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelTransaksi = new JTable(modelTransaksi);
        tabelTransaksi.setFont(fField); tabelTransaksi.setRowHeight(22);
        tabelTransaksi.getTableHeader().setFont(fLabel);
        tabelTransaksi.setShowGrid(true); tabelTransaksi.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollTrx = new JScrollPane(tabelTransaksi);
        scrollTrx.setBorder(BorderFactory.createTitledBorder("Daftar Transaksi"));

        // ── Tabel Detail ──────────────────────────────────────────────────────
        modelDetail = new DefaultTableModel(
            new String[]{"No Nota", "Kode Barang", "Nama Barang", "Qty", "Harga Satuan", "Subtotal"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelDetail = new JTable(modelDetail);
        tabelDetail.setFont(fField); tabelDetail.setRowHeight(22);
        tabelDetail.getTableHeader().setFont(fLabel);
        tabelDetail.setShowGrid(true); tabelDetail.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollDetail = new JScrollPane(tabelDetail);
        scrollDetail.setBorder(BorderFactory.createTitledBorder("Detail Transaksi (klik baris atas)"));

        // Klik baris transaksi → tampil detail
        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tabelTransaksi.getSelectedRow();
                if (row >= 0) tampilDetail(modelTransaksi.getValueAt(row, 0).toString());
            }
        });

        // ── Split pane ────────────────────────────────────────────────────────
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTrx, scrollDetail);
        split.setDividerLocation(230);
        split.setResizeWeight(0.5);

        JPanel pnlAtas = new JPanel(new BorderLayout());
        pnlAtas.setBackground(bg);
        pnlAtas.add(pnlHeader, BorderLayout.NORTH);
        pnlAtas.add(pnlFilter, BorderLayout.SOUTH);

        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(bg);
        add(pnlAtas, BorderLayout.NORTH);
        add(split,   BorderLayout.CENTER);

        // ── Events ───────────────────────────────────────────────────────────
        btnFilter.addActionListener(e -> filterTanggal());
        btnSemua .addActionListener(e -> tampilSemuaTransaksi());
        btnCetak .addActionListener(e -> cetakLaporan());
    }

    private void tampilSemuaTransaksi() {
        modelTransaksi.setRowCount(0);
        String sql = "SELECT t.no_nota, t.tanggal, c.nama_customer, t.total_bayar "
                   + "FROM transaksi t JOIN customer c ON t.kode_customer = c.kode_customer "
                   + "ORDER BY t.tanggal DESC";
        double grandTotal = 0;
        try (Connection c = Koneksi.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                double total = rs.getDouble("total_bayar");
                grandTotal += total;
                modelTransaksi.addRow(new Object[]{
                    rs.getString("no_nota"), rs.getString("tanggal"),
                    rs.getString("nama_customer"),
                    String.format("Rp %.2f", total)
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        lblTotal.setText("Total Transaksi: Rp " + String.format("%.2f", grandTotal));
    }

    private void filterTanggal() {
        if (txtDari.getText().trim().isEmpty() || txtSampai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi tanggal dari dan sampai! (format: yyyy-MM-dd)");
            return;
        }
        modelTransaksi.setRowCount(0);
        String sql = "SELECT t.no_nota, t.tanggal, c.nama_customer, t.total_bayar "
                   + "FROM transaksi t JOIN customer c ON t.kode_customer = c.kode_customer "
                   + "WHERE t.tanggal BETWEEN ? AND ? ORDER BY t.tanggal DESC";
        double grandTotal = 0;
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, txtDari.getText().trim());
            ps.setString(2, txtSampai.getText().trim());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double total = rs.getDouble("total_bayar");
                grandTotal += total;
                modelTransaksi.addRow(new Object[]{
                    rs.getString("no_nota"), rs.getString("tanggal"),
                    rs.getString("nama_customer"),
                    String.format("Rp %.2f", total)
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        lblTotal.setText("Total: Rp " + String.format("%.2f", grandTotal));
    }

    private void tampilDetail(String noNota) {
        modelDetail.setRowCount(0);
        String sql = "SELECT d.no_nota, d.kode_barang, b.nama_barang, d.qty, d.harga_satuan, d.subtotal "
                   + "FROM detail_transaksi d JOIN barang b ON d.kode_barang = b.kode_barang "
                   + "WHERE d.no_nota = ?";
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, noNota);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                modelDetail.addRow(new Object[]{
                    rs.getString("no_nota"), rs.getString("kode_barang"),
                    rs.getString("nama_barang"), rs.getInt("qty"),
                    String.format("Rp %.2f", rs.getDouble("harga_satuan")),
                    String.format("Rp %.2f", rs.getDouble("subtotal"))
                });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cetakLaporan() {
        try {
            tabelTransaksi.print(JTable.PrintMode.FIT_WIDTH,
                new java.text.MessageFormat("Laporan Transaksi Penjualan"),
                new java.text.MessageFormat("Halaman {0}"));
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Gagal cetak: " + ex.getMessage());
        }
    }
}
