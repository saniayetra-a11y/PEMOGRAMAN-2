<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Data Customer - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <%
        String pesan = (String) session.getAttribute("pesan");
        if (pesan != null) { session.removeAttribute("pesan");
    %>
    <div class="alert alert-sukses">✅ <%= pesan %></div>
    <% } %>

    <div class="card">
        <div style="display:flex; justify-content:space-between; align-items:center;">
            <h1 class="page-title">👤 Data Customer</h1>
            <a href="?action=form" class="btn btn-hijau">+ Tambah Customer</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>NIK</th>
                    <th>Nama</th>
                    <th>No. Telepon</th>
                    <th>Email</th>
                    <th>Tgl Daftar</th>
                    <th>Aksi</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Customer> list = (List<Customer>) request.getAttribute("listCustomer");
                int no = 1;
                if (list != null && !list.isEmpty()) {
                    for (Customer c : list) {
            %>
                <tr>
                    <td><%= no++ %></td>
                    <td><%= c.getNik() %></td>
                    <td><strong><%= c.getNama() %></strong></td>
                    <td><%= c.getNoTelp() %></td>
                    <td><%= c.getEmail() != null ? c.getEmail() : "-" %></td>
                    <td><%= c.getTglDaftar() %></td>
                    <td>
                        <a href="?action=form&id=<%= c.getIdCustomer() %>" class="btn btn-kuning btn-sm">Edit</a>
                        <a href="?action=delete&id=<%= c.getIdCustomer() %>"
                           class="btn btn-merah btn-sm"
                           onclick="return confirm('Hapus customer <%= c.getNama() %>?')">Hapus</a>
                    </td>
                </tr>
            <%  }
                } else { %>
                <tr><td colspan="7" style="text-align:center; color:#999;">Belum ada data customer.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
