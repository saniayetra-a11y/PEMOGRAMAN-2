package view;

import koneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// ── Import JasperReports ──────────────────────────────────
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FormNilai extends javax.swing.JFrame {

    Connection conn;
    DefaultTableModel model;

    public FormNilai() {

        initComponents();

        conn = Koneksi.getKoneksi();

        tampilData();

        bersih();

        setLocationRelativeTo(null);

    }

    // ─────────────────────────────────────────────────────────────────────────
    // TAMPIL DATA
    // ─────────────────────────────────────────────────────────────────────────
    private void tampilData() {

        model = new DefaultTableModel();

        model.addColumn("NIM");
        model.addColumn("Nama");
        model.addColumn("Mata Kuliah");
        model.addColumn("Nilai 1");
        model.addColumn("Nilai 2");
        model.addColumn("Rata-rata");

        tblNilai.setModel(model);

        try {

            String sql = "SELECT * FROM nilai_mahasiswa";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

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

            tblNilai.setModel(model);

            // tampilkan jumlah data di label
            lblTotal.setText("Total data: "
                    + model.getRowCount()
                    + " mahasiswa");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    e.getMessage());

        }

    }

    // ─────────────────────────────────────────────────────────────────────────
    // BERSIH FORM
    // ─────────────────────────────────────────────────────────────────────────
    private void bersih() {

        txtNim.setText("");
        txtNama.setText("");
        txtMatkul.setText("");
        txtNilai1.setText("");
        txtNilai2.setText("");
        txtRata.setText("");

    }

    // ─────────────────────────────────────────────────────────────────────────
    // CETAK LAPORAN – menggunakan JasperReports
    // ─────────────────────────────────────────────────────────────────────────
    private void cetakLaporan() {

        try {

            // 1. Cari file LaporanNilai.jrxml secara otomatis
            //    (nama folder bisa "report" atau "reports", NetBeans mana saja)
            File jrxmlFile = cariFileJrxml("LaporanNilai.jrxml");

            if (jrxmlFile == null) {

                JOptionPane.showMessageDialog(this,
                        "File LaporanNilai.jrxml tidak ditemukan!\n\n"
                        + "Pastikan file .jrxml sudah ada di dalam\n"
                        + "folder  src/report/  atau  src/reports/\n"
                        + "di dalam proyek NetBeans kamu.",
                        "File Tidak Ditemukan",
                        JOptionPane.ERROR_MESSAGE);
                return;

            }

            // 2. Tentukan path .jasper (sama folder dengan .jrxml)
            String jrxmlPath  = jrxmlFile.getAbsolutePath();
            String jasperPath = jrxmlPath.replace(".jrxml", ".jasper");
            File   jasperFile = new File(jasperPath);

            // 3. Compile .jrxml → .jasper jika belum ada / lebih lama
            if (!jasperFile.exists()
                    || jasperFile.lastModified()
                       < jrxmlFile.lastModified()) {

                JasperCompileManager
                        .compileReportToFile(jrxmlPath, jasperPath);

            }

            // 4. Parameter laporan (kosong – semua data)
            Map<String, Object> params = new HashMap<>();

            // 5. Isi data dari database
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperPath,
                            params,
                            Koneksi.getKoneksi());

            // 6. Tampilkan di JasperViewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    "Gagal cetak laporan:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

        }

    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPER – cari file .jrxml di berbagai kemungkinan folder
    // ─────────────────────────────────────────────────────────────────────────
    private File cariFileJrxml(String namaFile) {

        try {

            String base = new File(".").getCanonicalPath()
                    + File.separator + "src" + File.separator;

            // Coba semua kemungkinan nama folder
            String[] folderOptions = {
                "report",        // ← nama folder kamu di screenshot
                "reports",
                "laporan",
                "view",
                ""               // langsung di dalam src/
            };

            for (String folder : folderOptions) {

                String path = base
                        + (folder.isEmpty() ? "" : folder + File.separator)
                        + namaFile;

                File f = new File(path);

                if (f.exists()) {
                    System.out.println("File ditemukan: " + path);
                    return f;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // tidak ditemukan

    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1   = new javax.swing.JLabel();
        jLabel2   = new javax.swing.JLabel();
        jLabel3   = new javax.swing.JLabel();
        jLabel4   = new javax.swing.JLabel();
        jLabel5   = new javax.swing.JLabel();
        jLabel6   = new javax.swing.JLabel();
        lblTotal  = new javax.swing.JLabel();   // label total data

        txtNim    = new javax.swing.JTextField();
        txtNama   = new javax.swing.JTextField();
        txtMatkul = new javax.swing.JTextField();
        txtNilai1 = new javax.swing.JTextField();
        txtNilai2 = new javax.swing.JTextField();
        txtRata   = new javax.swing.JTextField();

        btnSimpan = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnHapus  = new javax.swing.JButton();
        btnBersih = new javax.swing.JButton();
        btnCetak  = new javax.swing.JButton();

        jScrollPane1 = new javax.swing.JScrollPane();
        tblNilai     = new javax.swing.JTable();

        setDefaultCloseOperation(
                javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Nilai Mahasiswa");

        jLabel1.setText("NIM");
        jLabel2.setText("Nama");
        jLabel3.setText("Mata Kuliah");
        jLabel4.setText("Nilai 1");
        jLabel5.setText("Nilai 2");
        jLabel6.setText("Rata-rata");

        lblTotal.setText("Total data: 0 mahasiswa");
        lblTotal.setFont(new java.awt.Font(
                "Segoe UI", java.awt.Font.BOLD, 12));

        txtRata.setEditable(false);

        // ── SIMPAN ───────────────────────────────────────────────────────────
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(evt -> {
            try {
                double n1   = Double.parseDouble(
                        txtNilai1.getText());
                double n2   = Double.parseDouble(
                        txtNilai2.getText());
                double rata = (n1 + n2) / 2;

                txtRata.setText(String.valueOf(rata));

                String sql =
                        "INSERT INTO nilai_mahasiswa "
                        + "VALUES (?,?,?,?,?,?)";

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ps.setString(1, txtNim.getText());
                ps.setString(2, txtNama.getText());
                ps.setString(3, txtMatkul.getText());
                ps.setDouble(4, n1);
                ps.setDouble(5, n2);
                ps.setDouble(6, rata);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null,
                        "Data Berhasil Disimpan");

                tampilData();
                bersih();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage());
            }
        });

        // ── UPDATE ───────────────────────────────────────────────────────────
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(evt -> {
            try {
                double n1   = Double.parseDouble(
                        txtNilai1.getText());
                double n2   = Double.parseDouble(
                        txtNilai2.getText());
                double rata = (n1 + n2) / 2;

                String sql =
                        "UPDATE nilai_mahasiswa SET "
                        + "nama=?, mata_kuliah=?, "
                        + "nilai1=?, nilai2=?, "
                        + "rata_rata=? WHERE nim=?";

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ps.setString(1, txtNama.getText());
                ps.setString(2, txtMatkul.getText());
                ps.setDouble(3, n1);
                ps.setDouble(4, n2);
                ps.setDouble(5, rata);
                ps.setString(6, txtNim.getText());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null,
                        "Data Berhasil Diupdate");

                tampilData();
                bersih();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage());
            }
        });

        // ── HAPUS ────────────────────────────────────────────────────────────
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(evt -> {
            try {
                String sql =
                        "DELETE FROM nilai_mahasiswa "
                        + "WHERE nim=?";

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ps.setString(1, txtNim.getText());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null,
                        "Data Berhasil Dihapus");

                tampilData();
                bersih();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage());
            }
        });

        // ── BERSIH ───────────────────────────────────────────────────────────
        btnBersih.setText("Bersih");
        btnBersih.addActionListener(evt -> bersih());

        // ── CETAK → JasperReports ────────────────────────────────────────────
        btnCetak.setText("Cetak");
        btnCetak.addActionListener(evt -> cetakLaporan());

        // ── TABEL ────────────────────────────────────────────────────────────
        tblNilai.setModel(
                new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{}
                )
        );

        tblNilai.addMouseListener(
                new java.awt.event.MouseAdapter() {
            public void mouseClicked(
                    java.awt.event.MouseEvent evt) {

                int row = tblNilai.getSelectedRow();

                txtNim.setText(
                        model.getValueAt(row, 0).toString());
                txtNama.setText(
                        model.getValueAt(row, 1).toString());
                txtMatkul.setText(
                        model.getValueAt(row, 2).toString());
                txtNilai1.setText(
                        model.getValueAt(row, 3).toString());
                txtNilai2.setText(
                        model.getValueAt(row, 4).toString());
                txtRata.setText(
                        model.getValueAt(row, 5).toString());
            }
        });

        jScrollPane1.setViewportView(tblNilai);

        // ── LAYOUT ───────────────────────────────────────────────────────────
        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(lblTotal)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNim)
                            .addComponent(txtNama)
                            .addComponent(txtMatkul)
                            .addComponent(txtNilai1)
                            .addComponent(txtNilai2)
                            .addComponent(txtRata)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addGap(10)
                        .addComponent(btnUpdate)
                        .addGap(10)
                        .addComponent(btnHapus)
                        .addGap(10)
                        .addComponent(btnBersih)
                        .addGap(10)
                        .addComponent(btnCetak)))
                .addGap(30))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNim,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMatkul,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNilai1,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNilai2,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRata,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20)
                .addGroup(layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUpdate)
                    .addComponent(btnHapus)
                    .addComponent(btnBersih)
                    .addComponent(btnCetak))
                .addGap(10)
                .addComponent(lblTotal)
                .addGap(10)
                .addComponent(jScrollPane1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        200,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20))
        );

        pack();
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new FormNilai().setVisible(true);
        });

    }

    private javax.swing.JButton btnBersih;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;

    private javax.swing.JLabel  jLabel1;
    private javax.swing.JLabel  jLabel2;
    private javax.swing.JLabel  jLabel3;
    private javax.swing.JLabel  jLabel4;
    private javax.swing.JLabel  jLabel5;
    private javax.swing.JLabel  jLabel6;
    private javax.swing.JLabel  lblTotal;       

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable      tblNilai;

    private javax.swing.JTextField txtMatkul;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNilai1;
    private javax.swing.JTextField txtNilai2;
    private javax.swing.JTextField txtNim;
    private javax.swing.JTextField txtRata;
}