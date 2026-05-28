package com.unpam.controller;

import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogoutController – Controller Servlet
 * Pertemuan 14 – Aplikasi Web MVC
 */
@WebServlet(name = "LogoutController", urlPatterns = {"/LogoutController"})
public class LogoutController extends HttpServlet {

    protected void processRequest(
            HttpServletRequest  request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Hapus semua session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        MainForm mainForm = new MainForm();
        String   konten   = "<br><h2>Anda telah berhasil logout.</h2>"
                          + "<p><a href='LoginController'>Login kembali</a></p>";

        mainForm.tampilkan(request, response, konten);
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { processRequest(req, res); }
}
