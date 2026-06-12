<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Transaksi" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Pengembalian - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="card" style="max-width:700px; margin:0 auto;">
        <%
            Transaksi t = (Transaksi) request.getAttribute("transaksi");
        %>
        <h1 class="page-title">🔄 Proses Pengembalian Mobil</h1>

        <%-- Info transaksi --%>
        <div style="background:#f1f8f1; border-radius:6px; padding:16px; margin-bottom:20px;">
            <table style="font-size:14px; box-shadow:none;">
                <tr><td style="width:160px; font-weight:bold; border:none; padding:4px 8px;">Kode Transaksi</td>
                    <td style="border:none; padding:4px 8px;">: <%= t.getKodeTransaksi() %></td></tr>
                <tr><td style="font-weight:bold; border:none; padding:4px 8px;">Customer</td>
                    <td style="border:none; padding:4px 8px;">: <%= t.getNamaCustomer() %></td></tr>
                <tr><td style="font-weight:bold; border:none; padding:4px 8px;">Mobil</td>
                    <td style="border:none; padding:4px 8px;">: <%= t.getMerkMobil() %> <%= t.getTipeMobil() %> (<%= t.getNopolMobil() %>)</td></tr>
                <tr><td style="font-weight:bold; border:none; padding:4px 8px;">Tanggal Sewa</td>
                    <td style="border:none; padding:4px 8px;">: <%= t.getTglSewa() %></td></tr>
                <tr><td style="font-weight:bold; border:none; padding:4px 8px;">Rencana Kembali</td>
                    <td style="border:none; padding:4px 8px; color:#c62828; font-weight:bold;">: <%= t.getTglKembaliRencana() %></td></tr>
                <tr><td style="font-weight:bold; border:none; padding:4px 8px;">Total Sewa</td>
                    <td style="border:none; padding:4px 8px;">: Rp <%= String.format("%,.0f", t.getTotalBiaya()) %></td></tr>
            </table>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/transaksi" id="formKembali">
            <input type="hidden" name="action"             value="kembali">
            <input type="hidden" name="idTransaksi"        value="<%= t.getIdTransaksi() %>">
            <input type="hidden" name="idMobil"            value="<%= t.getIdMobil() %>">
            <input type="hidden" name="tglKembaliRencana"  value="<%= t.getTglKembaliRencana() %>">
            <input type="hidden" name="hargaSewa"          value="<%= t.getHargaSewa() %>">

            <div class="form-group">
                <label>Tanggal Kembali Aktual *</label>
                <input type="date" name="tglKembaliAktual" id="tglAktual"
                       required onchange="hitungDenda()">
            </div>

            <div class="form-group">
                <label>Info Denda Keterlambatan</label>
                <input type="text" id="infoDenda" readonly value="Belum ada denda"
                       style="background:#fff3e0; font-weight:bold; color:#e65100; font-size:15px;">
            </div>

            <div style="background:#e3f2fd; border-radius:6px; padding:12px; margin-bottom:16px; font-size:13px; color:#1565c0;">
                ℹ️ Denda keterlambatan dihitung sebesar <strong>50% harga sewa per hari</strong> terlambat.
            </div>

            <div style="display:flex; gap:10px;">
                <button type="submit" class="btn btn-hijau" style="font-size:15px; padding:10px 24px;">
                    ✅ Konfirmasi Pengembalian
                </button>
                <a href="${pageContext.request.contextPath}/transaksi?action=list" class="btn btn-abu">Batal</a>
            </div>
        </form>
    </div>
</div>

<script>
    var today = new Date().toISOString().split('T')[0];
    document.getElementById('tglAktual').value = today;
    document.getElementById('tglAktual').min = '<%= t.getTglSewa() %>';
    hitungDenda();

    function hitungDenda() {
        var tglRencana = new Date('<%= t.getTglKembaliRencana() %>');
        var tglAktual  = new Date(document.getElementById('tglAktual').value);
        var hargaSewa  = <%= t.getHargaSewa() %>;

        var diffMs  = tglAktual - tglRencana;
        var diffHari = Math.floor(diffMs / (1000*60*60*24));

        if (diffHari > 0) {
            var denda = diffHari * hargaSewa * 0.5;
            document.getElementById('infoDenda').value =
                'Terlambat ' + diffHari + ' hari — Denda: Rp ' + denda.toLocaleString('id-ID');
            document.getElementById('infoDenda').style.color = '#c62828';
            document.getElementById('infoDenda').style.background = '#fce4ec';
        } else {
            document.getElementById('infoDenda').value = 'Tidak ada denda (tepat waktu)';
            document.getElementById('infoDenda').style.color = '#2e7d32';
            document.getElementById('infoDenda').style.background = '#e8f5e9';
        }
    }
</script>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
