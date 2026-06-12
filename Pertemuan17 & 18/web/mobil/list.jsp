<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Mobil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Data Mobil - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <%-- Pesan sukses --%>
    <%
        String pesan = (String) session.getAttribute("pesan");
        if (pesan != null) { session.removeAttribute("pesan");
    %>
    <div class="alert alert-sukses">✅ <%= pesan %></div>
    <% } %>

    <div class="card">
        <div style="display:flex; justify-content:space-between; align-items:center;">
            <h1 class="page-title">🚘 Data Mobil</h1>
            <a href="?action=form" class="btn btn-hijau">+ Tambah Mobil</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>No Polisi</th>
                    <th>Merk</th>
                    <th>Tipe</th>
                    <th>Warna</th>
                    <th>Tahun</th>
                    <th>Harga Sewa/Hari</th>
                    <th>Status</th>
                    <th>Aksi</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Mobil> list = (List<Mobil>) request.getAttribute("listMobil");
                int no = 1;
                if (list != null && !list.isEmpty()) {
                    for (Mobil m : list) {
            %>
                <tr>
                    <td><%= no++ %></td>
                    <td><strong><%= m.getNopol() %></strong></td>
                    <td><%= m.getMerk() %></td>
                    <td><%= m.getTipe() %></td>
                    <td><%= m.getWarna() %></td>
                    <td><%= m.getTahun() %></td>
                    <td>Rp <%= String.format("%,.0f", m.getHargaSewa()) %></td>
                    <td>
                        <span class="badge badge-<%= m.getStatus() %>">
                            <%= m.getStatus() %>
                        </span>
                    </td>
                    <td>
                        <a href="?action=form&id=<%= m.getIdMobil() %>" class="btn btn-kuning btn-sm">Edit</a>
                        <a href="?action=delete&id=<%= m.getIdMobil() %>"
                           class="btn btn-merah btn-sm"
                           onclick="return confirm('Hapus mobil <%= m.getNopol() %>?')">Hapus</a>
                    </td>
                </tr>
            <%  }
                } else { %>
                <tr><td colspan="9" style="text-align:center; color:#999;">Belum ada data mobil.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
