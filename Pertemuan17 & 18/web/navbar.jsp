<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="brand">🚗 RentCar</a>
    <a href="${pageContext.request.contextPath}/mobil?action=list">Data Mobil</a>
    <a href="${pageContext.request.contextPath}/customer?action=list">Customer</a>
    <a href="${pageContext.request.contextPath}/transaksi?action=list">Transaksi</a>
    <a href="${pageContext.request.contextPath}/transaksi?action=formSewa">+ Sewa Mobil</a>
    <a href="${pageContext.request.contextPath}/laporan">Laporan</a>
</nav>
