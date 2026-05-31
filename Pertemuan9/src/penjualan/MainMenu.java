package penjualan;

import javax.swing.*;
import java.awt.*;

/**
 * MainMenu – Menu Utama Aplikasi Penjualan
 * Pertemuan 9 – Proyek Aplikasi Enterprise
 */
public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("Aplikasi Penjualan - Pertemuan 9");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();

    }

    private void initComponents() {

        // ── Warna & Font ──────────────────────────────────────────────────────
        Color biru   = new Color(33, 97, 140);
        Color putih  = Color.WHITE;
        Font  fJudul = new Font("SansSerif", Font.BOLD, 20);
        Font  fBtn   = new Font("SansSerif", Font.BOLD, 13);

        // ── Header ────────────────────────────────────────────────────────────
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(biru);
        pnlHeader.setPreferredSize(new Dimension(600, 70));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblJudul = new JLabel("APLIKASI PENJUALAN BARANG");
        lblJudul.setFont(fJudul);
        lblJudul.setForeground(putih);
        lblJudul.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSub = new JLabel("Pemrograman 2 – Pertemuan 9");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(new Color(200, 220, 240));
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel pnlJudul = new JPanel(new GridLayout(2, 1));
        pnlJudul.setBackground(biru);
        pnlJudul.add(lblJudul);
        pnlJudul.add(lblSub);
        pnlHeader.add(pnlJudul, BorderLayout.CENTER);

        // ── Panel Menu ────────────────────────────────────────────────────────
        JPanel pnlMenu = new JPanel(new GridLayout(3, 2, 15, 15));
        pnlMenu.setBackground(new Color(245, 246, 250));
        pnlMenu.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Data master
        JButton btnBarang    = buatBtn("📦  Data Barang",    new Color(41, 128, 185),  putih, fBtn);
        JButton btnCustomer  = buatBtn("👥  Data Customer",  new Color(39, 174, 96),   putih, fBtn);
        JButton btnSupplier  = buatBtn("🏭  Data Supplier",  new Color(142, 68, 173),  putih, fBtn);

        // Transaksi & Laporan
        JButton btnTransaksi = buatBtn("🛒  Transaksi",      new Color(230, 126, 34),  putih, fBtn);
        JButton btnInventory = buatBtn("📊  Inventory",      new Color(22, 160, 133),  putih, fBtn);
        JButton btnLaporan   = buatBtn("📄  Laporan",        new Color(192, 57, 43),   putih, fBtn);

        pnlMenu.add(btnBarang);
        pnlMenu.add(btnCustomer);
        pnlMenu.add(btnSupplier);
        pnlMenu.add(btnTransaksi);
        pnlMenu.add(btnInventory);
        pnlMenu.add(btnLaporan);

        // ── Footer ────────────────────────────────────────────────────────────
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlFooter.setBackground(new Color(236, 240, 241));
        JButton btnKeluar = new JButton("Keluar");
        btnKeluar.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pnlFooter.add(btnKeluar);

        // ── Layout ───────────────────────────────────────────────────────────
        setLayout(new BorderLayout());
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlMenu,   BorderLayout.CENTER);
        add(pnlFooter, BorderLayout.SOUTH);

        // ── Events ───────────────────────────────────────────────────────────
        btnBarang   .addActionListener(e -> new FormBarang().setVisible(true));
        btnCustomer .addActionListener(e -> new FormCustomer().setVisible(true));
        btnSupplier .addActionListener(e -> new FormSupplier().setVisible(true));
        btnTransaksi.addActionListener(e -> new FormTransaksi().setVisible(true));
        btnInventory.addActionListener(e -> new FormInventory().setVisible(true));
        btnLaporan  .addActionListener(e -> new FormLaporan().setVisible(true));
        btnKeluar   .addActionListener(e -> System.exit(0));

    }

    private JButton buatBtn(String teks, Color bg, Color fg, Font f) {
        JButton b = new JButton(teks);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(f);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
