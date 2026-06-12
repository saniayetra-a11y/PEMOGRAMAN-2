<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Customer, model.Mobil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Sewa Mobil - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="card" style="max-width:750px; margin:0 auto;">
        <h1 class="page-title">🚗 Form Penyewaan Mobil</h1>

        <form method="post" action="${pageContext.request.contextPath}/transaksi" id="formSewa">
            <input type="hidden" name="action" value="sewa">

            <%-- Customer --%>
            <div class="form-group">
                <label>Customer *</label>
                <select name="idCustomer" required>
                    <option value="">-- Pilih Customer --</option>
                    <%
                        List<Customer> listCustomer = (List<Customer>) request.getAttribute("listCustomer");
                        if (listCustomer != null) {
                            for (Customer c : listCustomer) {
                    %>
                    <option value="<%= c.getIdCustomer() %>">
                        <%= c.getNama() %> - <%= c.getNik() %>
                    </option>
                    <%      }
                        }
                    %>
                </select>
            </div>

            <%-- Mobil --%>
            <div class="form-group">
                <label>Mobil yang Disewa *</label>
                <select name="idMobil" id="pilihMobil" required onchange="updateHarga()">
                    <option value="">-- Pilih Mobil (Tersedia) --</option>
                    <%
                        List<Mobil> listMobil = (List<Mobil>) request.getAttribute("listMobil");
                        if (listMobil != null) {
                            for (Mobil m : listMobil) {
                    %>
                    <option value="<%= m.getIdMobil() %>" data-harga="<%= (int)m.getHargaSewa() %>">
                        <%= m.getMerk() %> <%= m.getTipe() %> | <%= m.getNopol() %>
                        | Rp <%= String.format("%,.0f", m.getHargaSewa()) %>/hari
                    </option>
                    <%      }
                        }
                    %>
                </select>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Tanggal Sewa *</label>
                    <input type="date" name="tglSewa" id="tglSewa"
                           required onchange="hitungTotal()">
                </div>
                <div class="form-group">
                    <label>Lama Sewa (hari) *</label>
                    <input type="number" name="lamaSewa" id="lamaSewa"
                           min="1" max="30" value="1" required onchange="hitungTotal()">
                </div>
            </div>

            <div class="form-group">
                <label>Tanggal Kembali Rencana</label>
                <input type="date" name="tglKembaliRencana" id="tglKembaliRencana" readonly
                       style="background:#f5f5f5;">
            </div>

            <div class="form-group">
                <label>Estimasi Total Biaya</label>
                <input type="text" id="totalEstimasi" readonly value="Rp 0"
                       style="background:#e8f5e9; font-weight:bold; color:#2e7d32; font-size:16px;">
            </div>

            <div class="form-group">
                <label>Keterangan</label>
                <textarea name="keterangan" rows="2" placeholder="Catatan tambahan (opsional)"></textarea>
            </div>

            <div style="display:flex; gap:10px;">
                <button type="submit" class="btn btn-hijau" style="font-size:15px; padding:10px 24px;">
                    ✅ Proses Penyewaan
                </button>
                <a href="${pageContext.request.contextPath}/transaksi?action=list" class="btn btn-abu">Batal</a>
            </div>
        </form>
    </div>
</div>

<script>
    // Set tanggal sewa default = hari ini
    var today = new Date().toISOString().split('T')[0];
    document.getElementById('tglSewa').value = today;
    hitungTotal();

    function updateHarga() { hitungTotal(); }

    function hitungTotal() {
        var sel = document.getElementById('pilihMobil');
        var harga = 0;
        if (sel.selectedIndex > 0) {
            harga = parseInt(sel.options[sel.selectedIndex].getAttribute('data-harga')) || 0;
        }
        var lama = parseInt(document.getElementById('lamaSewa').value) || 1;
        var tglSewa = document.getElementById('tglSewa').value;

        // Hitung tanggal kembali
        if (tglSewa) {
            var d = new Date(tglSewa);
            d.setDate(d.getDate() + lama);
            document.getElementById('tglKembaliRencana').value = d.toISOString().split('T')[0];
        }

        // Tampilkan total
        var total = harga * lama;
        document.getElementById('totalEstimasi').value =
            'Rp ' + total.toLocaleString('id-ID');
    }
</script>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
