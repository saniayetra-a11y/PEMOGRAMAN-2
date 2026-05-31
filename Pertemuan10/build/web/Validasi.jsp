<%-- 
    Validasi.jsp
    Pertemuan 10 – Dasar Servlet dan JSP
    Memproses login, session, dan cookies
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Validasi - Pertemuan 10</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                background-color: white;
                padding: 30px 40px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.2);
                width: 380px;
                text-align: center;
            }
            h2 { color: #333; }
            .sukses { color: green; }
            .gagal  { color: red; }
            a {
                display: inline-block;
                margin-top: 15px;
                padding: 8px 20px;
                background-color: #336699;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                font-size: 13px;
            }
            a:hover { background-color: #225588; }
            table {
                width: 100%;
                margin-top: 15px;
                text-align: left;
            }
            td { padding: 5px 8px; font-size: 13px; }
            th {
                background-color: #336699;
                color: white;
                padding: 6px 8px;
                font-size: 13px;
            }
        </style>
    </head>
    <body>
        <%
            String aksi = request.getParameter("aksi");
            if (aksi == null) aksi = "";

            // ── LOGOUT ───────────────────────────────────────────────────────
            if (aksi.equals("logout")) {
                session.invalidate();
        %>
                <div class="container">
                    <h2>Logout</h2>
                    <p class="sukses">Anda berhasil logout!</p>
                    <a href="index.jsp">Kembali ke Login</a>
                </div>
        <%
            // ── LOGIN ─────────────────────────────────────────────────────────
            } else if (aksi.equals("login")) {

                String user     = request.getParameter("user");
                String password = request.getParameter("password");

                if (user == null)     user = "";
                if (password == null) password = "";

                // Validasi: user & password harus ADMIN
                if (user.equalsIgnoreCase("ADMIN")
                        && password.equalsIgnoreCase("ADMIN")) {

                    // Simpan ke session
                    session.setAttribute("userLogin", user.toUpperCase());
                    session.setMaxInactiveInterval(60 * 60); // 1 jam

                    // Simpan cookies
                    Cookie userCookie = new Cookie("user", user.toUpperCase());
                    userCookie.setMaxAge(60 * 60 * 24); // 1 hari
                    response.addCookie(userCookie);

                    // Simpan waktu login ke cookies
                    Cookie loginTimeCookie = new Cookie("loginTime",
                            new java.text.SimpleDateFormat("dd-MM-yyyy_HH:mm:ss")
                             .format(new Date()).replace(":", "-"));
                    loginTimeCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(loginTimeCookie);
        %>
                    <div class="container">
                        <h2>Login Berhasil ✓</h2>
                        <p class="sukses">
                            Selamat datang, <b><%= user.toUpperCase() %></b>!
                        </p>

                        <table border="1" cellpadding="5" cellspacing="0">
                            <tr>
                                <th colspan="2">Informasi Session</th>
                            </tr>
                            <tr>
                                <td><b>User Login</b></td>
                                <td><%= session.getAttribute("userLogin") %></td>
                            </tr>
                            <tr>
                                <td><b>Session ID</b></td>
                                <td><%= session.getId() %></td>
                            </tr>
                            <tr>
                                <td><b>Session Berlaku</b></td>
                                <td>1 jam</td>
                            </tr>
                            <tr>
                                <td><b>Waktu Login</b></td>
                                <td>
                                    <%= new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                                            .format(new Date()) %>
                                </td>
                            </tr>
                        </table>

                        <table border="1" cellpadding="5" cellspacing="0"
                               style="margin-top:10px;">
                            <tr>
                                <th colspan="2">Informasi Cookies</th>
                            </tr>
                            <%
                                Cookie[] allCookies = request.getCookies();
                                if (allCookies != null) {
                                    for (Cookie c : allCookies) {
                            %>
                            <tr>
                                <td><b><%= c.getName() %></b></td>
                                <td><%= c.getValue() %></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </table>

                        <a href="index.jsp">Kembali</a>
                        &nbsp;
                        <a href="Validasi.jsp?aksi=logout"
                           style="background-color:#cc3333;">Logout</a>
                    </div>
        <%
                } else {
                    // Login gagal
                    session.setAttribute("pesanError",
                            "User atau Password salah! Gunakan ADMIN / ADMIN");
        %>
                    <div class="container">
                        <h2>Login Gagal ✗</h2>
                        <p class="gagal">
                            User atau Password yang Anda masukkan salah!<br>
                            <small>User: <b><%= user %></b></small>
                        </p>
                        <a href="index.jsp">Coba Lagi</a>
                    </div>
        <%
                }

            // ── Default ───────────────────────────────────────────────────────
            } else {
        %>
                <div class="container">
                    <h2>Pertemuan 10</h2>
                    <p>Dasar Servlet dan JSP</p>
                    <a href="index.jsp">Ke Halaman Login</a>
                </div>
        <%
            }
        %>
    </body>
</html>
