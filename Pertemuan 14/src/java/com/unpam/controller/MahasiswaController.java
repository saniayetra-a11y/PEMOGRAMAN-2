package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Mahasiswa;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MahasiswaController – Controller Servlet
 * Pertemuan 14 – Aplikasi Web MVC
 *
 * URL: http://localhost:8080/AplikasiWeb/MahasiswaController
 */
@WebServlet(name = "MahasiswaController", urlPatterns = {"/MahasiswaController"})
public class MahasiswaController extends HttpServlet {

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

            Mahasiswa  mhs      = new Mahasiswa();
            Enkripsi   enkripsi = new Enkripsi();

            try {
                mhs.setNim     (request.getParameter("nim"));
                mhs.setNama    (request.getParameter("nama"));
                mhs.setSemester(Integer.parseInt(request.getParameter("semester")));
                mhs.setKelas   (request.getParameter("kelas"));
                mhs.setPassword(enkripsi.hashMD5(request.getParameter("password")));

                if (mhs.simpan()) {
                    konten = "<p style='color:green;'><b>Data mahasiswa berhasil disimpan!</b></p>";
                } else {
                    konten = "<p style='color:red;'><b>Gagal: " + mhs.getPesan() + "</b></p>";
                }
            } catch (Exception ex) {
                konten = "<p style='color:red;'>Error: " + ex.getMessage() + "</p>";
            }

        // ── UPDATE ────────────────────────────────────────────────────────────
        } else if (aksi.equals("update")) {

            Mahasiswa  mhs      = new Mahasiswa();
            Enkripsi   enkripsi = new Enkripsi();

            try {
                mhs.setNim     (request.getParameter("nim"));
                mhs.setNama    (request.getParameter("nama"));
                mhs.setSemester(Integer.parseInt(request.getParameter("semester")));
                mhs.setKelas   (request.getParameter("kelas"));

                String pw = request.getParameter("password");
                if (pw != null && !pw.trim().isEmpty()) {
                    mhs.setPassword(enkripsi.hashMD5(pw));
                } else {
                    mhs.setPassword(request.getParameter("password_lama"));
                }

                if (mhs.update()) {
                    konten = "<p style='color:green;'><b>Data mahasiswa berhasil diupdate!</b></p>";
                } else {
                    konten = "<p style='color:red;'><b>Gagal: " + mhs.getPesan() + "</b></p>";
                }
            } catch (Exception ex) {
                konten = "<p style='color:red;'>Error: " + ex.getMessage() + "</p>";
            }

        // ── HAPUS ─────────────────────────────────────────────────────────────
        } else if (aksi.equals("hapus")) {

            Mahasiswa mhs = new Mahasiswa();
            mhs.setNim(request.getParameter("nim"));

            if (mhs.hapus()) {
                konten = "<p style='color:green;'><b>Data mahasiswa berhasil dihapus!</b></p>";
            } else {
                konten = "<p style='color:red;'><b>Gagal: " + mhs.getPesan() + "</b></p>";
            }
        }

        // ── Tampilkan form + tabel data ───────────────────────────────────────
        Mahasiswa mhsList = new Mahasiswa();
        mhsList.cariSemua();
        Object[][] list = mhsList.getList();

        StringBuilder tabel = new StringBuilder();
        tabel.append(konten);
        tabel.append("<h3>Form Input Mahasiswa</h3>");
        tabel.append("<form method='post' action='MahasiswaController'>");
        tabel.append("<input type='hidden' name='aksi' value='simpan'>");
        tabel.append("<table border='1' cellpadding='5' cellspacing='0'>");
        tabel.append("<tr><td>NIM</td><td><input type='text' name='nim' required /></td></tr>");
        tabel.append("<tr><td>Nama</td><td><input type='text' name='nama' required /></td></tr>");
        tabel.append("<tr><td>Semester</td><td><input type='number' name='semester' min='1' max='14' required /></td></tr>");
        tabel.append("<tr><td>Kelas</td><td><input type='text' name='kelas' required /></td></tr>");
        tabel.append("<tr><td>Password</td><td><input type='password' name='password' required /></td></tr>");
        tabel.append("<tr><td colspan='2' align='center'>");
        tabel.append("<input type='submit' value='Simpan' />");
        tabel.append("</td></tr>");
        tabel.append("</table></form>");

        tabel.append("<br><h3>Daftar Mahasiswa</h3>");
        tabel.append("<table border='1' cellpadding='5' cellspacing='0' width='90%'>");
        tabel.append("<tr bgcolor='#cccccc'>");
        tabel.append("<th>NIM</th><th>Nama</th><th>Semester</th><th>Kelas</th><th>Aksi</th>");
        tabel.append("</tr>");

        if (list != null) {
            for (Object[] row : list) {
                tabel.append("<tr>");
                tabel.append("<td>").append(row[0]).append("</td>");
                tabel.append("<td>").append(row[1]).append("</td>");
                tabel.append("<td align='center'>").append(row[2]).append("</td>");
                tabel.append("<td>").append(row[3]).append("</td>");
                tabel.append("<td align='center'>");
                tabel.append("<a href='MahasiswaController?aksi=hapus&nim=").append(row[0])
                     .append("' onclick=\"return confirm('Hapus data ini?');\">Hapus</a>");
                tabel.append("</td>");
                tabel.append("</tr>");
            }
        }
        tabel.append("</table>");

        mainForm.tampilkan(request, response, tabel.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
