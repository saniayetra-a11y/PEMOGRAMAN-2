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
import javax.servlet.http.HttpSession;

/**
 * LoginController – Controller Servlet
 * Pertemuan 14 – Aplikasi Web MVC
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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

        // ── Proses Login ──────────────────────────────────────────────────────
        if (aksi.equals("login")) {

            Mahasiswa  mhs      = new Mahasiswa();
            Enkripsi   enkripsi = new Enkripsi();

            try {
                mhs.setNim(request.getParameter("nim"));
                mhs.setPassword(enkripsi.hashMD5(request.getParameter("password")));

                if (mhs.login()) {
                    // Login berhasil – simpan ke session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userName", mhs.getNama());
                    session.setAttribute("nimLogin",  mhs.getNim());

                    // Menu setelah login
                    String menu = "<br><b>Master Data</b><br>"
                            + "<a href='MahasiswaController'>Mahasiswa</a><br>"
                            + "<a href='MataKuliahController'>Mata Kuliah</a><br><br>"
                            + "<b>Transaksi</b><br>"
                            + "<a href='NilaiController'>Nilai</a><br><br>"
                            + "<b>Laporan</b><br>"
                            + "<a href='LaporanController'>Nilai</a><br><br>"
                            + "<a href='LogoutController'>Logout</a><br><br>";

                    String topMenu = "<nav><ul>"
                            + "<li><a href='MainForm'>Home</a></li>"
                            + "<li><a href='#'>Master Data</a><ul>"
                            + "<li><a href='MahasiswaController'>Mahasiswa</a></li>"
                            + "<li><a href='MataKuliahController'>Mata Kuliah</a></li>"
                            + "</ul></li>"
                            + "<li><a href='#'>Transaksi</a><ul>"
                            + "<li><a href='NilaiController'>Nilai</a></li>"
                            + "</ul></li>"
                            + "<li><a href='#'>Laporan</a><ul>"
                            + "<li><a href='LaporanController'>Nilai</a></li>"
                            + "</ul></li>"
                            + "<li><a href='LogoutController'>Logout</a></li>"
                            + "</ul></nav>";

                    session.setAttribute("menu",    menu);
                    session.setAttribute("topMenu", topMenu);

                    konten = "<br><h1>Selamat Datang</h1><h2>" + mhs.getNama() + "</h2>";

                } else {
                    konten = "<p style='color:red;'><b>" + mhs.getPesan() + "</b></p>"
                           + formLogin("");
                }

            } catch (Exception ex) {
                konten = "<p style='color:red;'>Error: " + ex.getMessage() + "</p>"
                       + formLogin("");
            }

        } else {
            // Tampilkan form login
            konten = formLogin("");
        }

        mainForm.tampilkan(request, response, konten);
    }

    private String formLogin(String pesan) {
        return pesan
             + "<h3>Login</h3>"
             + "<form method='post' action='LoginController'>"
             + "<input type='hidden' name='aksi' value='login'>"
             + "<table border='1' cellpadding='5' cellspacing='0'>"
             + "<tr><td>NIM</td><td><input type='text' name='nim' required /></td></tr>"
             + "<tr><td>Password</td><td><input type='password' name='password' required /></td></tr>"
             + "<tr><td colspan='2' align='center'><input type='submit' value='Login' /></td></tr>"
             + "</table></form>";
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
