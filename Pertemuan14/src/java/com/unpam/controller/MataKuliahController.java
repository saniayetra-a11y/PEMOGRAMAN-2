package com.unpam.controller;

import com.unpam.model.MataKuliah;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MataKuliahController – Controller Servlet
 * Pertemuan 14 – Aplikasi Web MVC
 */
@WebServlet(name = "MataKuliahController", urlPatterns = {"/MataKuliahController"})
public class MataKuliahController extends HttpServlet {

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

        if (aksi.equals("simpan")) {

            MataKuliah mk = new MataKuliah();
            mk.setKodeMataKuliah(request.getParameter("kode_matakuliah"));
            mk.setNamaMataKuliah(request.getParameter("nama_matakuliah"));
            mk.setJumlahSks(Integer.parseInt(request.getParameter("jumlah_sks")));

            if (mk.simpan()) {
                konten = "<p style='color:green;'><b>Mata kuliah berhasil disimpan!</b></p>";
            } else {
                konten = "<p style='color:red;'><b>Gagal: " + mk.getPesan() + "</b></p>";
            }

        } else if (aksi.equals("hapus")) {

            MataKuliah mk = new MataKuliah();
            mk.setKodeMataKuliah(request.getParameter("kode_matakuliah"));

            if (mk.hapus()) {
                konten = "<p style='color:green;'><b>Mata kuliah berhasil dihapus!</b></p>";
            } else {
                konten = "<p style='color:red;'><b>Gagal: " + mk.getPesan() + "</b></p>";
            }
        }

        // ── Form + Tabel ───────────────────────────────────────────────────────
        MataKuliah mkList = new MataKuliah();
        mkList.cariSemua();
        Object[][] list = mkList.getList();

        StringBuilder tabel = new StringBuilder();
        tabel.append(konten);
        tabel.append("<h3>Form Input Mata Kuliah</h3>");
        tabel.append("<form method='post' action='MataKuliahController'>");
        tabel.append("<input type='hidden' name='aksi' value='simpan'>");
        tabel.append("<table border='1' cellpadding='5' cellspacing='0'>");
        tabel.append("<tr><td>Kode MK</td><td><input type='text' name='kode_matakuliah' required /></td></tr>");
        tabel.append("<tr><td>Nama MK</td><td><input type='text' name='nama_matakuliah' required /></td></tr>");
        tabel.append("<tr><td>Jumlah SKS</td><td><input type='number' name='jumlah_sks' min='1' max='6' required /></td></tr>");
        tabel.append("<tr><td colspan='2' align='center'><input type='submit' value='Simpan' /></td></tr>");
        tabel.append("</table></form>");

        tabel.append("<br><h3>Daftar Mata Kuliah</h3>");
        tabel.append("<table border='1' cellpadding='5' cellspacing='0' width='90%'>");
        tabel.append("<tr bgcolor='#cccccc'><th>Kode</th><th>Nama Mata Kuliah</th><th>SKS</th><th>Aksi</th></tr>");

        if (list != null) {
            for (Object[] row : list) {
                tabel.append("<tr>");
                tabel.append("<td>").append(row[0]).append("</td>");
                tabel.append("<td>").append(row[1]).append("</td>");
                tabel.append("<td align='center'>").append(row[2]).append("</td>");
                tabel.append("<td align='center'>");
                tabel.append("<a href='MataKuliahController?aksi=hapus&kode_matakuliah=").append(row[0])
                     .append("' onclick=\"return confirm('Hapus data ini?');\">Hapus</a>");
                tabel.append("</td></tr>");
            }
        }
        tabel.append("</table>");

        mainForm.tampilkan(request, response, tabel.toString());
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
