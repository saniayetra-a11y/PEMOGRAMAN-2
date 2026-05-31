<%-- 
    index.jsp
    Pertemuan 10 – Dasar Servlet dan JSP
    Form Login dengan Session dan Cookies
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login - Pertemuan 10</title>
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
                width: 320px;
            }
            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
            }
            td {
                padding: 6px 4px;
            }
            input[type=text], input[type=password] {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 13px;
            }
            input[type=submit] {
                width: 100%;
                padding: 10px;
                background-color: #336699;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                margin-top: 10px;
            }
            input[type=submit]:hover {
                background-color: #225588;
            }
            .info {
                font-size: 11px;
                color: #888;
                text-align: center;
                margin-top: 15px;
            }
            .pesan {
                color: red;
                font-size: 12px;
                text-align: center;
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <%
            // Cek apakah sudah login (ada session)
            String userLogin = "";
            try {
                userLogin = (String) session.getAttribute("userLogin");
                if (userLogin == null) userLogin = "";
            } catch (Exception e) {}

            // Cek cookies untuk last visit
            String lastVisit = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("lastVisit")) {
                        lastVisit = c.getValue();
                    }
                }
            }

            // Simpan waktu kunjungan sekarang ke cookies
            Cookie visitCookie = new Cookie("lastVisit",
                    new java.text.SimpleDateFormat("dd-MM-yyyy_HH:mm:ss")
    .format(new Date()).replace(":", "-"));
            visitCookie.setMaxAge(60 * 60 * 24); // 1 hari
            response.addCookie(visitCookie);
        %>

        <div class="container">
            <h2>Login</h2>

            <%-- Tampilkan pesan error jika ada --%>
            <%
                String pesanError = "";
                try {
                    pesanError = (String) session.getAttribute("pesanError");
                    if (pesanError == null) pesanError = "";
                    session.removeAttribute("pesanError");
                } catch (Exception e) {}

                if (!pesanError.isEmpty()) {
            %>
                <p class="pesan"><%= pesanError %></p>
            <% } %>

            <%-- Tampilkan info jika sudah login --%>
            <% if (!userLogin.isEmpty()) { %>
                <p style="color:green; text-align:center;">
                    Anda sudah login sebagai <b><%= userLogin %></b><br>
                    <a href="Validasi.jsp?aksi=logout">Logout</a>
                </p>
            <% } else { %>

            <form method="post" action="Validasi.jsp">
                <input type="hidden" name="aksi" value="login">
                <table>
                    <tr>
                        <td>User</td>
                        <td>
                            <input type="text" name="user"
                                   placeholder="Masukkan username" />
                        </td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td>
                            <input type="password" name="password"
                                   placeholder="Masukkan password" />
                        </td>
                    </tr>
                </table>
                <input type="submit" value="Login" />
            </form>

            <% } %>

            <div class="info">
                <% if (!lastVisit.isEmpty()) { %>
                    Kunjungan terakhir: <%= lastVisit %>
                <% } else { %>
                    Kunjungan pertama kali
                <% } %>
            </div>

            <div class="info">
                Hint: User = <b>ADMIN</b> | Password = <b>ADMIN</b>
            </div>
        </div>
    </body>
</html>
