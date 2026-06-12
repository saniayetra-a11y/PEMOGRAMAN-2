<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rent Car - Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="card">
        <h1 class="page-title">🚗 Dashboard Aplikasi Rent Car</h1>
        <p style="color:#555; margin-bottom:24px;">
            Selamat datang di Sistem Informasi Penyewaan Mobil.<br>
            Gunakan menu di atas untuk mengelola data.
        </p>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="angka">🚘</div>
                <div class="label" style="font-size:15px; font-weight:bold; color:#1a472a; margin-top:8px;">Data Mobil</div>
                <div class="label">Kelola armada kendaraan</div>
                <a href="mobil?action=list" class="btn btn-hijau" style="margin-top:12px;">Buka</a>
            </div>
            <div class="stat-card">
                <div class="angka">👤</div>
                <div class="label" style="font-size:15px; font-weight:bold; color:#1a472a; margin-top:8px;">Data Customer</div>
                <div class="label">Kelola data penyewa</div>
                <a href="customer?action=list" class="btn btn-hijau" style="margin-top:12px;">Buka</a>
            </div>
            <div class="stat-card">
                <div class="angka">📋</div>
                <div class="label" style="font-size:15px; font-weight:bold; color:#1a472a; margin-top:8px;">Transaksi Sewa</div>
                <div class="label">Sewa &amp; pengembalian</div>
                <a href="transaksi?action=list" class="btn btn-hijau" style="margin-top:12px;">Buka</a>
            </div>
        </div>

        <div style="text-align:center; margin-top:10px;">
            <a href="transaksi?action=formSewa" class="btn btn-biru" style="font-size:15px; padding:10px 28px;">
                ➕ Buat Transaksi Sewa Baru
            </a>
            &nbsp;
            <a href="laporan" class="btn btn-abu" style="font-size:15px; padding:10px 28px;">
                📊 Lihat Laporan
            </a>
        </div>
    </div>
</div>

<footer>Program Studi Teknik Informatika, Universitas Pamulang &mdash; Pemrograman 2</footer>
</body>
</html>
