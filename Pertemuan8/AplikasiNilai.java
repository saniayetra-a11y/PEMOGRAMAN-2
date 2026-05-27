package pert8;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class AplikasiNilai extends JFrame {

    private JTextField      txtNIM, txtNama, txtMatkul, txtNilai1, txtNilai2, txtRata;
    private JButton         btnSimpan, btnUpdate, btnHapus, btnBersih;
    private JTable          tabel;
    private DefaultTableModel model;
    private JLabel          lblTotal;

    public AplikasiNilai() {
        setTitle("Aplikasi Nilai Mahasiswa - Pertemuan 8");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);
        initComponents();
        tampilData();
    }

    private void initComponents() {

        // ── Panel Form ───────────────────────────────────────────────────────
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Input Data Nilai Mahasiswa"));
        pnlForm.setBackground(Color.WHITE);

        GridBagConstraints g = new GridBagConstraints();
        g.insets  = new Insets(5, 10, 5, 10);
        g.anchor  = GridBagConstraints.WEST;

        Font fLabel = new Font("SansSerif", Font.BOLD,  12);
        Font fField = new Font("SansSerif", Font.PLAIN, 12);

        String[] lblTxt = {"NIM :", "Nama :", "Mata Kuliah :", "Nilai 1 :", "Nilai 2 :", "Rata-rata :"};
        txtNIM    = new JTextField(20); txtNIM.setFont(fField);
        txtNama   = new JTextField(20); txtNama.setFont(fField);
        txtMatkul = new JTextField(20); txtMatkul.setFont(fField);
        txtNilai1 = new JTextField(20); txtNilai1.setFont(fField);
        txtNilai2 = new JTextField(20); txtNilai2.setFont(fField);
        txtRata   = new JTextField(20); txtRata.setFont(fField);
        txtRata.setEditable(false);
        txtRata.setBackground(new Color(230, 230, 230));

        JTextField[] fields = {txtNIM, txtNama, txtMatkul, txtNilai1, txtNilai2, txtRata};

        for (int i = 0; i < lblTxt.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0; g.fill = GridBagConstraints.NONE;
            JLabel l = new JLabel(lblTxt[i]); l.setFont(fLabel);
            pnlForm.add(l, g);
            g.gridx = 1; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            pnlForm.add(fields[i], g);
        }

        // Auto hitung rata-rata
        txtNilai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { hitungRata(); }
        });
        txtNilai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { hitungRata(); }
        });

        // ── Tombol – pakai UIManager default biar pasti muncul ───────────────
        btnSimpan = new JButton("SIMPAN");
        btnUpdate = new JButton("UPDATE");
        btnHapus  = new JButton("HAPUS");
        btnBersih = new JButton("BERSIH");

        // Set ukuran seragam
        Dimension dimBtn = new Dimension(100, 30);
        for (JButton b : new JButton[]{btnSimpan, btnUpdate, btnHapus, btnBersih}) {
            b.setFont(new Font("SansSerif", Font.BOLD, 12));
            b.setPreferredSize(dimBtn);
        }

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.add(btnSimpan);
        pnlBtn.add(btnUpdate);
        pnlBtn.add(btnHapus);
        pnlBtn.add(btnBersih);

        // ── Tabel ────────────────────────────────────────────────────────────
        model = new DefaultTableModel(
                new String[]{"NIM", "Nama", "Mata Kuliah", "Nilai 1", "Nilai 2", "Rata-rata"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabel = new JTable(model);
        tabel.setFont(fField);
        tabel.setRowHeight(22);
        tabel.getTableHeader().setFont(fLabel);
        tabel.setGridColor(Color.LIGHT_GRAY);
        tabel.setShowGrid(true);

        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { isiDariTabel(); }
        });

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(BorderFactory.createTitledBorder("Data Nilai Mahasiswa"));

        lblTotal = new JLabel("Total: 0 mahasiswa");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblTotal.setBorder(new EmptyBorder(3, 8, 3, 8));

        // ── Layout utama ──────────────────────────────────────────────────────
        JPanel pnlAtas = new JPanel(new BorderLayout());
        pnlAtas.setBackground(Color.WHITE);
        pnlAtas.add(pnlForm, BorderLayout.CENTER);
        pnlAtas.add(pnlBtn,  BorderLayout.SOUTH);

        JPanel pnlBawah = new JPanel(new BorderLayout(0, 2));
        pnlBawah.setBackground(Color.WHITE);
        pnlBawah.add(lblTotal, BorderLayout.NORTH);
        pnlBawah.add(scroll,   BorderLayout.CENTER);

        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(Color.WHITE);
        add(pnlAtas,  BorderLayout.NORTH);
        add(pnlBawah, BorderLayout.CENTER);

        // ── Event ─────────────────────────────────────────────────────────────
        btnSimpan.addActionListener(e -> simpanData());
        btnUpdate.addActionListener(e -> updateData());
        btnHapus .addActionListener(e -> hapusData());
        btnBersih.addActionListener(e -> bersihForm());
    }

    private void hitungRata() {
        try {
            double n1 = Double.parseDouble(txtNilai1.getText().trim());
            double n2 = Double.parseDouble(txtNilai2.getText().trim());
            txtRata.setText(String.format("%.1f", (n1 + n2) / 2));
        } catch (NumberFormatException ex) {
            txtRata.setText("");
        }
    }

    private void tampilData() {
        model.setRowCount(0);
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT nim, nama, mata_kuliah, nilai1, nilai2, rata_rata FROM nilai_mahasiswa ORDER BY nim")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("mata_kuliah"),
                    rs.getDouble("nilai1"),
                    rs.getDouble("nilai2"),
                    rs.getDouble("rata_rata")
                });
            }
            lblTotal.setText("Total: " + model.getRowCount() + " mahasiswa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpanData() {
        if (!validasi()) return;
        double n1 = Double.parseDouble(txtNilai1.getText().trim());
        double n2 = Double.parseDouble(txtNilai2.getText().trim());
        double rata = (n1 + n2) / 2;
        String sql = "INSERT INTO nilai_mahasiswa (nim,nama,mata_kuliah,nilai1,nilai2,rata_rata) VALUES (?,?,?,?,?,?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNIM.getText().trim());
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, txtMatkul.getText().trim());
            ps.setDouble(4, n1); ps.setDouble(5, n2); ps.setDouble(6, rata);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            bersihForm(); tampilData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal simpan:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateData() {
        if (!validasi()) return;
        double n1 = Double.parseDouble(txtNilai1.getText().trim());
        double n2 = Double.parseDouble(txtNilai2.getText().trim());
        double rata = (n1 + n2) / 2;
        String sql = "UPDATE nilai_mahasiswa SET nama=?,mata_kuliah=?,nilai1=?,nilai2=?,rata_rata=? WHERE nim=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, txtNama.getText().trim());
            ps.setString(2, txtMatkul.getText().trim());
            ps.setDouble(3, n1); ps.setDouble(4, n2); ps.setDouble(5, rata);
            ps.setString(6, txtNIM.getText().trim());
            int rows = ps.executeUpdate();
            JOptionPane.showMessageDialog(this, rows > 0 ? "Data berhasil diupdate!" : "NIM tidak ditemukan.");
            bersihForm(); tampilData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal update:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusData() {
        if (txtNIM.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Klik baris tabel dulu!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
                "Hapus data NIM: " + txtNIM.getText() + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM nilai_mahasiswa WHERE nim=?")) {
            ps.setString(1, txtNIM.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            bersihForm(); tampilData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal hapus:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void isiDariTabel() {
        int row = tabel.getSelectedRow();
        if (row < 0) return;
        txtNIM.setText(model.getValueAt(row, 0).toString());
        txtNama.setText(model.getValueAt(row, 1).toString());
        txtMatkul.setText(model.getValueAt(row, 2).toString());
        txtNilai1.setText(model.getValueAt(row, 3).toString());
        txtNilai2.setText(model.getValueAt(row, 4).toString());
        txtRata.setText(model.getValueAt(row, 5).toString());
    }

    private void bersihForm() {
        txtNIM.setText(""); txtNama.setText(""); txtMatkul.setText("");
        txtNilai1.setText(""); txtNilai2.setText(""); txtRata.setText("");
        tabel.clearSelection();
    }

    private boolean validasi() {
        if (txtNIM.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty()
         || txtMatkul.getText().trim().isEmpty() || txtNilai1.getText().trim().isEmpty()
         || txtNilai2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtNilai1.getText().trim());
            Double.parseDouble(txtNilai2.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai 1 dan Nilai 2 harus angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // Pakai Nimbus Look and Feel - tombol pasti kelihatan jelas
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new AplikasiNilai().setVisible(true));
    }
}
