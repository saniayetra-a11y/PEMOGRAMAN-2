package penjualan;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormInventory extends JFrame {

    private JTable tabel;
    private DefaultTableModel model;
    private JLabel lblInfo;

    public FormInventory() {
        setTitle("Inventory Barang");
        setSize(750, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        tampilData();
    }

    private void initComponents() {

        Font fLabel = new Font("SansSerif", Font.BOLD, 12);
        Font fField = new Font("SansSerif", Font.PLAIN, 12);

        // ── Header ────────────────────────────────────────────────────────────
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(22, 160, 133));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblJudul = new JLabel("INVENTORY STOK BARANG");
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblJudul.setForeground(Color.WHITE);
        pnlHeader.add(lblJudul, BorderLayout.WEST);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("SansSerif", Font.BOLD, 12));
        pnlHeader.add(btnRefresh, BorderLayout.EAST);

        // ── Filter ────────────────────────────────────────────────────────────
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.add(new JLabel("Filter Stok:"));

        JComboBox<String> cbFilter = new JComboBox<>(new String[]{
            "Semua Barang", "Stok Habis (= 0)", "Stok Menipis (< 10)", "Stok Aman (>= 10)"
        });
        cbFilter.setFont(fField);
        pnlFilter.add(cbFilter);

        lblInfo = new JLabel("Total: 0 item");
        lblInfo.setFont(fLabel);
        pnlFilter.add(lblInfo);

        // ── Tabel ────────────────────────────────────────────────────────────
        model = new DefaultTableModel(
            new String[]{"Kode", "Nama Barang", "Satuan", "Stok", "Harga", "Total Nilai", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabel = new JTable(model) {
            public java.awt.Component prepareRenderer(
                    javax.swing.table.TableCellRenderer r, int row, int col) {
                java.awt.Component comp = super.prepareRenderer(r, row, col);
                String status = getValueAt(row, 6).toString();
                if ("HABIS".equals(status))   comp.setBackground(new Color(255, 200, 200));
                else if ("MENIPIS".equals(status)) comp.setBackground(new Color(255, 243, 200));
                else { comp.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 250, 255)); }
                return comp;
            }
        };
        tabel.setFont(fField);
        tabel.setRowHeight(22);
        tabel.getTableHeader().setFont(fLabel);
        tabel.setShowGrid(true);
        tabel.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tabel);

        // ── Layout ───────────────────────────────────────────────────────────
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(Color.WHITE);
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlFilter, BorderLayout.CENTER);
        add(scroll,    BorderLayout.SOUTH);

        // Atur ukuran tabel
        scroll.setPreferredSize(new Dimension(730, 330));

        btnRefresh.addActionListener(e -> tampilData());
        cbFilter.addActionListener(e -> filterData(cbFilter.getSelectedIndex()));
    }

    private void tampilData() {
        model.setRowCount(0);
        String sql = "SELECT kode_barang, nama_barang, satuan, stok, harga, "
                   + "(stok * harga) AS total_nilai FROM barang ORDER BY nama_barang";
        try (Connection c = Koneksi.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                int stok = rs.getInt("stok");
                String status = stok == 0 ? "HABIS" : stok < 10 ? "MENIPIS" : "AMAN";
                model.addRow(new Object[]{
                    rs.getString("kode_barang"), rs.getString("nama_barang"),
                    rs.getString("satuan"), stok,
                    String.format("Rp %.0f", rs.getDouble("harga")),
                    String.format("Rp %.0f", rs.getDouble("total_nilai")),
                    status
                });
            }
            lblInfo.setText("Total: " + model.getRowCount() + " item barang");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterData(int filter) {
        model.setRowCount(0);
        String kondisi = switch (filter) {
            case 1 -> "WHERE stok = 0";
            case 2 -> "WHERE stok > 0 AND stok < 10";
            case 3 -> "WHERE stok >= 10";
            default -> "";
        };
        String sql = "SELECT kode_barang, nama_barang, satuan, stok, harga, "
                   + "(stok * harga) AS total_nilai FROM barang " + kondisi + " ORDER BY nama_barang";
        try (Connection c = Koneksi.getConnection();
             ResultSet rs = c.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                int stok = rs.getInt("stok");
                String status = stok == 0 ? "HABIS" : stok < 10 ? "MENIPIS" : "AMAN";
                model.addRow(new Object[]{
                    rs.getString("kode_barang"), rs.getString("nama_barang"),
                    rs.getString("satuan"), stok,
                    String.format("Rp %.0f", rs.getDouble("harga")),
                    String.format("Rp %.0f", rs.getDouble("total_nilai")),
                    status
                });
            }
            lblInfo.setText("Total: " + model.getRowCount() + " item barang");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
