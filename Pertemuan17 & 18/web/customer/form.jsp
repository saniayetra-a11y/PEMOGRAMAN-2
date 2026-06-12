<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Customer - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="card" style="max-width:700px; margin:0 auto;">
        <%
            Customer c = (Customer) request.getAttribute("customer");
            boolean isEdit = (c != null);
        %>
        <h1 class="page-title"><%= isEdit ? "✏️ Edit Customer" : "➕ Tambah Customer" %></h1>

        <form method="post" action="${pageContext.request.contextPath}/customer">
            <input type="hidden" name="action" value="<%= isEdit ? "update" : "insert" %>">
            <% if (isEdit) { %>
            <input type="hidden" name="idCustomer" value="<%= c.getIdCustomer() %>">
            <% } %>

            <div class="form-group">
                <label>NIK (16 digit) *</label>
                <input type="text" name="nik" placeholder="3201010101010001"
                       value="<%= isEdit ? c.getNik() : "" %>"
                       required maxlength="20" pattern="\d{16}">
            </div>

            <div class="form-group">
                <label>Nama Lengkap *</label>
                <input type="text" name="nama" placeholder="Budi Santoso"
                       value="<%= isEdit ? c.getNama() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Alamat *</label>
                <textarea name="alamat" rows="3" placeholder="Jl. Merdeka No.1, Jakarta" required><%= isEdit ? c.getAlamat() : "" %></textarea>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>No. Telepon *</label>
                    <input type="tel" name="noTelp" placeholder="08123456789"
                           value="<%= isEdit ? c.getNoTelp() : "" %>" required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" placeholder="email@contoh.com"
                           value="<%= isEdit && c.getEmail()!=null ? c.getEmail() : "" %>">
                </div>
            </div>

            <div style="display:flex; gap:10px;">
                <button type="submit" class="btn btn-hijau"><%= isEdit ? "💾 Simpan Perubahan" : "➕ Tambah Customer" %></button>
                <a href="${pageContext.request.contextPath}/customer?action=list" class="btn btn-abu">Batal</a>
            </div>
        </form>
    </div>
</div>

<footer>Program Studi Teknik Informatika, Universitas Pamulang</footer>
</body>
</html>
