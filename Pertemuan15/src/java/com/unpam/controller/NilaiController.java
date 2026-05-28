package com.unpam.controller;

import com.unpam.model.MataKuliah;
import com.unpam.model.Nilai;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import com.unpam.model.Koneksi;

/**
 * NilaiController – Controller Servlet Penilaian
 * Pertemuan 15 – Form Transaksi Aplikasi Web
 *
 * URL: http://localhost:8080/Pertemuan14/NilaiController
 */
@WebServlet(name = "NilaiController", urlPatterns = {"/NilaiController"})
public class NilaiController extends HttpServlet {

    protected void processRequest(
            HttpServletRequest  request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        MainForm mainForm = new MainForm();
        String   konten   = "";
        String   aksi     = "";

        try { aksi = request.getParameter("aksi"); } catch (Exception ex) {}
        if (aksi == null) aksi = "";

        // ── SIMPAN ────────────────────────────────────────────────────────────
        if (aksi.equals("simpan")) {

            try {
                Nilai nilai = new Nilai();
                nilai.setNim              (request.getParameter("nim"));
                nilai.setKodeMataKuliah   (request.getParameter("kode_matakuliah"));
                nilai.setNilaiUTS  (Double.parseDouble(request.getParameter("nilai_uts")));
                nilai.setNilaiUAS  (Double.parseDouble(request.getParameter("nilai_uas")));
                nilai.setNilaiTugas(Double.parseDouble(request.getParameter("nilai_tugas")));

                if (nilai.simpan()) {
                    konten = "<p style='color:green;'><b>Nilai berhasil disimpan! "
                           + "Nilai Akhir: " + String.format("%.2f", nilai.getNilaiAkhir())
                           + " Grade: " + nilai.getGrade() + "</b></p>";
                } else {
                    konten = "<p style='color:red;'><b>Gagal: " + nilai.getPesan() + "</b></p>";
                }
            } catch (Exception ex) {
                konten = "<p style='color:red;'>Error: " + ex.getMessage() + "</p>";
            }

        // ── HAPUS ─────────────────────────────────────────────────────────────
        } else if (aksi.equals("hapus")) {

            try {
                Nilai nilai = new Nilai();
                nilai.setNim           (request.getParameter("nim"));
                nilai.setKodeMataKuliah(request.getParameter("kode_matakuliah"));

                if (nilai.hapus()) {
                    konten = "<p style='color:green;'><b>Data nilai berhasil dihapus!</b></p>";
                } else {
                    konten = "<p style='color:red;'><b>Gagal: " + nilai.getPesan() + "</b></p>";
                }
            } catch (Exception ex) {
                konten = "<p style='color:red;'>Error: " + ex.getMessage() + "</p>";
            }

        // ── CETAK PDF (JasperReports) ─────────────────────────────────────────
        } else if (aksi.equals("cetak")) {

            try {
                String jrxmlPath = getServletContext().getRealPath("/reports/LaporanNilai.jrxml");
                String jasperPath = jrxmlPath.replace(".jrxml", ".jasper");

                // Compile jika belum ada
                java.io.File jasperFile = new java.io.File(jasperPath);
                if (!jasperFile.exists()) {
                    JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
                }

                // Koneksi database
                Koneksi koneksi = new Koneksi();
                Connection conn = koneksi.getConnection();

                Map<String, Object> params = new HashMap<>();

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperPath, params, conn);

                // Export ke PDF
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=LaporanNilai.pdf");

                OutputStream out = response.getOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                exporter.exportReport();
                out.flush();
                conn.close();
                return;

            } catch (Exception ex) {
                konten = "<p style='color:red;'>Gagal cetak laporan: " + ex.getMessage() + "</p>";
            }
        }

        // ── Tampilkan form + tabel ─────────────────────────────────────────────
        // Ambil daftar mata kuliah untuk dropdown
        MataKuliah mkList = new MataKuliah();
        mkList.cariSemua();
        Object[][] listMK = mkList.getList();

        // Ambil daftar nilai
        Nilai nilaiList = new Nilai();
        nilaiList.cariSemua();
        Object[][] listNilai = nilaiList.getList();

        StringBuilder sb = new StringBuilder();
        sb.append(konten);
        sb.append("<h3>Form Input Nilai Mahasiswa</h3>");
        sb.append("<form method='post' action='NilaiController'>");
        sb.append("<input type='hidden' name='aksi' value='simpan'>");
        sb.append("<table border='1' cellpadding='5' cellspacing='0'>");
        sb.append("<tr><td>NIM</td><td><input type='text' name='nim' required /></td></tr>");

        // Dropdown Mata Kuliah
        sb.append("<tr><td>Mata Kuliah</td><td><select name='kode_matakuliah' required>");
        sb.append("<option value=''>-- Pilih Mata Kuliah --</option>");
        if (listMK != null) {
            for (Object[] mk : listMK) {
                sb.append("<option value='").append(mk[0]).append("'>")
                  .append(mk[0]).append(" - ").append(mk[1])
                  .append("</option>");
            }
        }
        sb.append("</select></td></tr>");

        sb.append("<tr><td>Nilai UTS</td><td><input type='number' name='nilai_uts' min='0' max='100' step='0.1' required /></td></tr>");
        sb.append("<tr><td>Nilai UAS</td><td><input type='number' name='nilai_uas' min='0' max='100' step='0.1' required /></td></tr>");
        sb.append("<tr><td>Nilai Tugas</td><td><input type='number' name='nilai_tugas' min='0' max='100' step='0.1' required /></td></tr>");
        sb.append("<tr><td colspan='2' align='center'>");
        sb.append("<input type='submit' value='Simpan' />");
        sb.append("&nbsp;&nbsp;");
        sb.append("<a href='NilaiController?aksi=cetak'><button type='button'>Cetak PDF</button></a>");
        sb.append("</td></tr>");
        sb.append("</table></form>");

        // Keterangan bobot nilai
        sb.append("<p style='font-size:11px; color:#666;'>");
        sb.append("<b>Keterangan:</b> Nilai Akhir = (UTS x 30%) + (UAS x 40%) + (Tugas x 30%)</p>");

        // Tabel data nilai
        sb.append("<br><h3>Daftar Nilai Mahasiswa</h3>");
        sb.append("<table border='1' cellpadding='5' cellspacing='0' width='95%'>");
        sb.append("<tr bgcolor='#cccccc'>");
        sb.append("<th>NIM</th><th>Nama</th><th>Mata Kuliah</th>");
        sb.append("<th>UTS</th><th>UAS</th><th>Tugas</th><th>Nilai Akhir</th><th>Grade</th><th>Aksi</th>");
        sb.append("</tr>");

        if (listNilai != null && listNilai.length > 0) {
            for (Object[] row : listNilai) {
                String gradeColor = "black";
                String g = row[8].toString();
                if      (g.equals("A")) gradeColor = "green";
                else if (g.equals("B")) gradeColor = "blue";
                else if (g.equals("C")) gradeColor = "orange";
                else if (g.equals("D") || g.equals("E")) gradeColor = "red";

                sb.append("<tr>");
                sb.append("<td>").append(row[0]).append("</td>");
                sb.append("<td>").append(row[1]).append("</td>");
                sb.append("<td>").append(row[3]).append("</td>");
                sb.append("<td align='center'>").append(row[4]).append("</td>");
                sb.append("<td align='center'>").append(row[5]).append("</td>");
                sb.append("<td align='center'>").append(row[6]).append("</td>");
                sb.append("<td align='center'><b>").append(String.format("%.2f", (Double) row[7])).append("</b></td>");
                sb.append("<td align='center' style='color:").append(gradeColor).append(";'><b>").append(g).append("</b></td>");
                sb.append("<td align='center'>");
                sb.append("<a href='NilaiController?aksi=hapus&nim=").append(row[0])
                  .append("&kode_matakuliah=").append(row[2])
                  .append("' onclick=\"return confirm('Hapus nilai ini?');\">Hapus</a>");
                sb.append("</td>");
                sb.append("</tr>");
            }
        } else {
            sb.append("<tr><td colspan='9' align='center'>Belum ada data nilai</td></tr>");
        }
        sb.append("</table>");

        mainForm.tampilkan(request, response, sb.toString());
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
