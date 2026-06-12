<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Transaksi" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detail Transaksi - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <%
        Transaksi t = (Transaksi) request.getAttribute("transaksi");
    %>

    <div class="card" style="max-width:700px; margin:0 auto;">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px;">
            <h1 class="page-title" style="margin-bottom:0;">📄 Detail Transaksi</h1>
            <button onclick="window.print()" class="btn btn-abu no-print">🖨 Cetak</button>
        </div>

        <%-- Header nota --%>
        <div style="text-align:center; margin-bottom:20px; border-bottom:2px solid #1a472a; padding-bottom:12px;">
            <h2 style="color:#1a472a;">NOTA PENYEWAAN MOBIL</h2>
            <p style="color:#555;">Program Studi Teknik Informatika, Universitas Pamulang</p>
            <p><strong>Kode: <%= t.getKodeTransaksi() %></strong></p>
        </div>

        <table style="font-size:14px;">
            <tr style="background:#f5f5f5;">
                <td style="font-weight:bold; width:40%; padding:10px;">Customer</td>
                <td style="padding:10px;"><%= t.getNamaCustomer() %></td>
            </tr>
            <tr>
                <td style="font-weight:bold; padding:10px;">Mobil</td>
                <td style="padding:10px;"><%= t.getMerkMobil() %> <%= t.getTipeMobil() %> (<%= t.getNopolMobil() %>)</td>
            </tr>
            <tr style="background:#f5f5f5;">
                <td style="font-weight:bold; padding:10px;">Harga Sewa/Hari</td>
                <td style="padding:10px;">Rp <%= String.format("%,.0f", t.getHargaSewa()) %></td>
            </tr>
            <tr>
                <td style="font-weight:bold; padding:10px;">Tanggal Sewa</td>
                <td style="padding:10px;"><%= t.getTglSewa() %></td>
            </tr>
            <tr style="background:#f5f5f5;">
                <td style="font-weight:bold; padding:10px;">Lama Sewa</td>
                <td style="padding:10px;"><%= t.getLamaSewa() %> hari</td>
            </tr>
            <tr>
                <td style="font-weight:bold; padding:10px;">Rencana Kembali</td>
                <td style="padding:10px;"><%= t.getTglKembaliRencana() %></td>
            </tr>
            <% if (t.getTglKembaliAktual() != null) { %>
            <tr style="background:#f5f5f5;">
                <td style="font-weight:bold; padding:10px;">Kembali Aktual</td>
                <td style="padding:10px;"><%= t.getTglKembaliAktual() %></td>
            </tr>
            <% } %>
            <tr>
                <td style="font-weight:bold; padding:10px;">Total Biaya Sewa</td>
                <td style="padding:10px;">Rp <%= String.format("%,.0f", t.getTotalBiaya()) %></td>
            </tr>
            <% if (t.getDenda() > 0) { %>
            <tr style="background:#fce4ec;">
                <td style="font-weight:bold; padding:10px; color:#c62828;">Denda Keterlambatan</td>
                <td style="padding:10px; color:#c62828;">Rp <%= String.format("%,.0f", t.getDenda()) %></td>
            </tr>
            <% } %>
            <tr style="background:#e8f5e9;">
                <td style="font-weight:bold; padding:10px; color:#1a472a; font-size:16px;">TOTAL PEMBAYARAN</td>
                <td style="padding:10px; font-weight:bold; color:#1a472a; font-size:16px;">
                    Rp <%= String.format("%,.0f", t.getGrandTotal()) %>
                </td>
            </tr>
            <tr>
                <td style="font-weight:bold; padding:10px;">Status</td>
                <td style="padding:10px;">
                    <span class="badge badge-<%= t.getStatus() %>"><%= t.getStatus().toUpperCase() %></span>
                </td>
            </tr>
        </table>

        <div style="margin-top:20px; display:flex; gap:10px;" class="no-print">
            <% if ("aktif".equals(t.getStatus())) { %>
            <a href="${pageContext.request.contextPath}/transaksi?action=formKembali&id=<%= t.getIdTransaksi() %>"
               class="btn btn-kuning">🔄 Proses Pengembalian</a>
            <% } %>
            <a href="${pageContext.request.contextPath}/transaksi?action=list" class="btn btn-abu">← Kembali</a>
        </div>
    </div>
</div>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
