<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Transaksi" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Laporan Transaksi - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">

    <%-- Form Filter --%>
    <div class="card no-print">
        <h1 class="page-title">📊 Laporan Transaksi</h1>
        <form method="get" action="${pageContext.request.contextPath}/laporan"
              style="display:flex; gap:12px; align-items:flex-end; flex-wrap:wrap;">
            <div class="form-group" style="margin:0;">
                <label>Tanggal Awal</label>
                <input type="date" name="tglAwal" value="${tglAwal}">
            </div>
            <div class="form-group" style="margin:0;">
                <label>Tanggal Akhir</label>
                <input type="date" name="tglAkhir" value="${tglAkhir}">
            </div>
            <button type="submit" class="btn btn-biru">🔍 Tampilkan</button>
            <button type="button" class="btn btn-abu" onclick="window.print()">🖨 Cetak</button>
        </form>
    </div>

    <%-- Judul Cetak (muncul saat print) --%>
    <div style="display:none;" class="print-only" id="printHeader">
        <h2 style="text-align:center; color:#1a472a;">LAPORAN TRANSAKSI PENYEWAAN MOBIL</h2>
        <p style="text-align:center;">Program Studi Teknik Informatika, Universitas Pamulang</p>
        <p style="text-align:center;">Periode: ${tglAwal} s/d ${tglAkhir}</p>
        <hr>
    </div>

    <%-- Statistik --%>
    <div class="stats-grid">
        <div class="stat-card">
            <div class="angka">${jumlahTransaksi}</div>
            <div class="label">Total Transaksi</div>
        </div>
        <div class="stat-card">
            <div class="angka">${jumlahSelesai}</div>
            <div class="label">Transaksi Selesai</div>
        </div>
        <div class="stat-card">
            <div class="angka" style="font-size:20px;">
                Rp <%= request.getAttribute("totalPendapatan") != null
                    ? String.format("%,.0f", (Double)request.getAttribute("totalPendapatan"))
                    : "0" %>
            </div>
            <div class="label">Total Pendapatan</div>
        </div>
    </div>

    <%-- Tabel Laporan --%>
    <div class="card">
        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>Kode</th>
                    <th>Customer</th>
                    <th>Mobil</th>
                    <th>Tgl Sewa</th>
                    <th>Lama</th>
                    <th>Total Sewa</th>
                    <th>Denda</th>
                    <th>Grand Total</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Transaksi> list = (List<Transaksi>) request.getAttribute("listLaporan");
                int no = 1;
                if (list != null && !list.isEmpty()) {
                    for (Transaksi t : list) {
            %>
                <tr>
                    <td><%= no++ %></td>
                    <td><%= t.getKodeTransaksi() %></td>
                    <td><%= t.getNamaCustomer() %></td>
                    <td><%= t.getMerkMobil() %> <%= t.getTipeMobil() %></td>
                    <td><%= t.getTglSewa() %></td>
                    <td><%= t.getLamaSewa() %> hari</td>
                    <td>Rp <%= String.format("%,.0f", t.getTotalBiaya()) %></td>
                    <td><%= t.getDenda() > 0 ? "Rp "+String.format("%,.0f",t.getDenda()) : "-" %></td>
                    <td><strong>Rp <%= String.format("%,.0f", t.getGrandTotal()) %></strong></td>
                    <td><span class="badge badge-<%= t.getStatus() %>"><%= t.getStatus() %></span></td>
                </tr>
            <%  }
                } else { %>
                <tr><td colspan="10" style="text-align:center; color:#999;">
                    Tidak ada data transaksi pada periode ini.</td></tr>
            <% } %>
            </tbody>
            <%-- Total row --%>
            <% if (list != null && !list.isEmpty()) { %>
            <tfoot>
                <tr style="background:#1a472a; color:white; font-weight:bold;">
                    <td colspan="8" style="padding:10px; text-align:right;">TOTAL PENDAPATAN:</td>
                    <td colspan="2" style="padding:10px;">
                        Rp <%= String.format("%,.0f", (Double)request.getAttribute("totalPendapatan")) %>
                    </td>
                </tr>
            </tfoot>
            <% } %>
        </table>
    </div>
</div>

<style>
    @media print {
        #printHeader { display:block !important; }
        .stats-grid { grid-template-columns: repeat(3,1fr); }
    }
</style>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
