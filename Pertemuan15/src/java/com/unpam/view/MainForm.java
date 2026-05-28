package com.unpam.view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * MainForm – Servlet Utama (View Layer)
 * Pertemuan 14 – Aplikasi Web MVC
 *
 * URL: http://localhost:8080/AplikasiWeb/MainForm
 */
@WebServlet(name = "MainForm", urlPatterns = {"/MainForm"})
public class MainForm extends HttpServlet {

  
    public void tampilkan(
            HttpServletRequest  request,
            HttpServletResponse response,
            String              konten)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);

        // ── Menu sidebar (default – sebelum login) ───────────────────────────
        String menu = "<br><b>Master Data</b><br>"
                + "<a href='MahasiswaController'>Mahasiswa</a><br>"
                + "<a href='MataKuliahController'>Mata Kuliah</a><br><br>"
                + "<b>Transaksi</b><br>"
                + "<a href='NilaiController'>Nilai</a><br><br>"
                + "<b>Laporan</b><br>"
                + "<a href='LaporanController'>Nilai</a><br><br>"
                + "<a href='LoginController'>Login</a><br><br>";

        // ── Top navigation menu (default) ─────────────────────────────────────
        String topMenu = "<nav><ul>"
                + "<li><a href='MainForm'>Home</a></li>"
                + "<li><a href='#'>Master Data</a>"
                + "<ul>"
                + "<li><a href='MahasiswaController'>Mahasiswa</a></li>"
                + "<li><a href='MataKuliahController'>Mata Kuliah</a></li>"
                + "</ul>"
                + "</li>"
                + "<li><a href='#'>Transaksi</a>"
                + "<ul>"
                + "<li><a href='NilaiController'>Nilai</a></li>"
                + "</ul>"
                + "</li>"
                + "<li><a href='#'>Laporan</a>"
                + "<ul>"
                + "<li><a href='LaporanController'>Nilai</a></li>"
                + "</ul>"
                + "</li>"
                + "<li><a href='LoginController'>Login</a></li>"
                + "</ul></nav>";

        // ── Cek session login ─────────────────────────────────────────────────
        String userName = "";
        if (!session.isNew()) {
            try { userName = session.getAttribute("userName").toString(); } catch (Exception ex) {}

            if (!((userName == null) || userName.equals(""))) {
                if (konten.equals("")) {
                    konten = "<br><h1>Selamat Datang</h1><h2>" + userName + "</h2>";
                }
                try { menu    = session.getAttribute("menu").toString();    } catch (Exception ex) {}
                try { topMenu = session.getAttribute("topMenu").toString(); } catch (Exception ex) {}
            }
        }

        // ── Render HTML ───────────────────────────────────────────────────────
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href='style.css' rel='stylesheet' type='text/css' />");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Informasi Nilai Mahasiswa</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"#808080\">");
            out.println("<center>");
            out.println("<table width=\"80%\" bgcolor=\"#eeeeee\">");

            // ── Header ─────────────────────────────────────────────────────
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\">");
            out.println("<br>");
            out.println("<h2 style=\"margin-bottom:0px; margin-top:0px;\">Informasi Nilai Mahasiswa</h2>");
            out.println("<h1 style=\"margin-bottom:0px; margin-top:0px;\">UNIVERSITAS PAMULANG</h1>");
            out.println("<h4 style=\"margin-bottom:0px; margin-top:0px;\">Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</h4>");
            out.println("<br>");
            out.println("</td>");
            out.println("</tr>");

            // ── Body: Sidebar + Konten ──────────────────────────────────────
            out.println("<tr height=\"400\">");
            out.println("<td width=\"200\" align=\"center\" valign=\"top\" bgcolor=\"#eeffee\">");
            out.println("<div id='menu'>");
            out.println(menu);
            out.println("</div>");
            out.println("</td>");
            out.println("<td align=\"center\" valign=\"top\" bgcolor=\"#ffffff\">");
            out.println(topMenu);
            out.println("<br>");
            out.println(konten);
            out.println("</td>");
            out.println("</tr>");

            // ── Footer ──────────────────────────────────────────────────────
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"center\" bgcolor=\"#eeeeff\">");
            out.println("<small>");
            out.println("Copyright &copy; 2026 Universitas Pamulang<br>");
            out.println("Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten<br>");
            out.println("</small>");
            out.println("</td>");
            out.println("</tr>");

            out.println("</table>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tampilkan(request, response, "");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tampilkan(request, response, "");
    }
}
