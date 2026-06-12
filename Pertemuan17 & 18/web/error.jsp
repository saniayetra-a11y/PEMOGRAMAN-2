<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Rent Car</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <div class="card" style="max-width:600px; margin:0 auto; text-align:center;">
        <h1 style="color:#c62828; font-size:60px; margin-bottom:10px;">⚠️</h1>
        <h2 style="color:#c62828;">Terjadi Kesalahan</h2>
        <div class="alert alert-error" style="text-align:left; margin-top:16px;">
            <%= request.getAttribute("error") %>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn btn-hijau" style="margin-top:12px;">
            ← Kembali ke Dashboard
        </a>
    </div>
</div>
</body>
</html>
