<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Mobil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Mobil - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="card" style="max-width:700px; margin:0 auto;">
        <%
            Mobil m = (Mobil) request.getAttribute("mobil");
            boolean isEdit = (m != null);
        %>
        <h1 class="page-title"><%= isEdit ? "✏️ Edit Mobil" : "➕ Tambah Mobil" %></h1>

        <form method="post" action="${pageContext.request.contextPath}/mobil">
            <input type="hidden" name="action" value="<%= isEdit ? "update" : "insert" %>">
            <% if (isEdit) { %>
            <input type="hidden" name="idMobil" value="<%= m.getIdMobil() %>">
            <% } %>

            <div class="form-row">
                <div class="form-group">
                    <label>No Polisi *</label>
                    <input type="text" name="nopol" placeholder="Contoh: B 1234 ABC"
                           value="<%= isEdit ? m.getNopol() : "" %>" required maxlength="15">
                </div>
                <div class="form-group">
                    <label>Merk *</label>
                    <input type="text" name="merk" placeholder="Contoh: Toyota"
                           value="<%= isEdit ? m.getMerk() : "" %>" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Tipe *</label>
                    <input type="text" name="tipe" placeholder="Contoh: Avanza"
                           value="<%= isEdit ? m.getTipe() : "" %>" required>
                </div>
                <div class="form-group">
                    <label>Warna *</label>
                    <input type="text" name="warna" placeholder="Contoh: Hitam"
                           value="<%= isEdit ? m.getWarna() : "" %>" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Tahun *</label>
                    <input type="number" name="tahun" min="2000" max="2030" placeholder="2023"
                           value="<%= isEdit ? m.getTahun() : "" %>" required>
                </div>
                <div class="form-group">
                    <label>Harga Sewa per Hari (Rp) *</label>
                    <input type="number" name="hargaSewa" min="0" placeholder="300000"
                           value="<%= isEdit ? (int)m.getHargaSewa() : "" %>" required>
                </div>
            </div>

            <div class="form-group">
                <label>Status *</label>
                <select name="status">
                    <option value="tersedia" <%= isEdit && "tersedia".equals(m.getStatus()) ? "selected" : "" %>>Tersedia</option>
                    <option value="disewa"   <%= isEdit && "disewa".equals(m.getStatus())   ? "selected" : "" %>>Disewa</option>
                    <option value="perawatan"<%= isEdit && "perawatan".equals(m.getStatus())? "selected" : "" %>>Perawatan</option>
                </select>
            </div>

            <div class="form-group">
                <label>Keterangan</label>
                <textarea name="keterangan" rows="3" placeholder="Catatan tambahan (opsional)"><%= isEdit && m.getKeterangan()!=null ? m.getKeterangan() : "" %></textarea>
            </div>

            <div style="display:flex; gap:10px;">
                <button type="submit" class="btn btn-hijau"><%= isEdit ? "💾 Simpan Perubahan" : "➕ Tambah Mobil" %></button>
                <a href="${pageContext.request.contextPath}/mobil?action=list" class="btn btn-abu">Batal</a>
            </div>
        </form>
    </div>
</div>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
