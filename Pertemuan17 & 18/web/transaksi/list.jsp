<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Transaksi" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Daftar Transaksi - Rent Car</title>
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
            <h1 class="page-title">📋 Daftar Transaksi Sewa</h1>
            <a href="?action=formSewa" class="btn btn-hijau">+ Sewa Mobil Baru</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>Kode</th>
                    <th>Customer</th>
                    <th>Mobil</th>
                    <th>Tgl Sewa</th>
                    <th>Tgl Kembali</th>
                    <th>Total Biaya</th>
                    <th>Status</th>
                    <th>Aksi</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Transaksi> list = (List<Transaksi>) request.getAttribute("listTransaksi");
                int no = 1;
                if (list != null && !list.isEmpty()) {
                    for (Transaksi t : list) {
            %>
                <tr>
                    <td><%= no++ %></td>
                    <td><strong><%= t.getKodeTransaksi() %></strong></td>
                    <td><%= t.getNamaCustomer() %></td>
                    <td><%= t.getMerkMobil() %> <%= t.getTipeMobil() %><br>
                        <small style="color:#888;"><%= t.getNopolMobil() %></small></td>
                    <td><%= t.getTglSewa() %></td>
                    <td>
                        <%= t.getTglKembaliRencana() %>
                        <% if (t.getTglKembaliAktual() != null) { %>
                        <br><small style="color:#2e7d32;">Aktual: <%= t.getTglKembaliAktual() %></small>
                        <% } %>
                    </td>
                    <td>
                        Rp <%= String.format("%,.0f", t.getTotalBiaya()) %>
                        <% if (t.getDenda() > 0) { %>
                        <br><small style="color:#c62828;">+Denda: Rp <%= String.format("%,.0f", t.getDenda()) %></small>
                        <% } %>
                    </td>
                    <td>
                        <span class="badge badge-<%= t.getStatus() %>"><%= t.getStatus() %></span>
                    </td>
                    <td>
                        <a href="?action=detail&id=<%= t.getIdTransaksi() %>" class="btn btn-biru btn-sm">Detail</a>
                        <% if ("aktif".equals(t.getStatus())) { %>
                        <a href="?action=formKembali&id=<%= t.getIdTransaksi() %>" class="btn btn-kuning btn-sm">Kembalikan</a>
                        <% } %>
                    </td>
                </tr>
            <%  }
                } else { %>
                <tr><td colspan="9" style="text-align:center; color:#999;">Belum ada transaksi.</td></tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
