<%-- 
    TestJSP.jsp
    Pertemuan 12 – file JSP untuk uji URL pattern
    Sesuai slide 10: tambahkan file JSP dengan nama TestJSP
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Test JSP</title>
    </head>
    <body>
        <h3>Test JSP - Pertemuan 12</h3>
        <p>File ini digunakan untuk menguji URL Pattern servlet.</p>
        <p>Coba ubah url-pattern di web.xml menjadi <b>/*</b> lalu akses halaman ini.</p>
        <p>Maka yang terbuka adalah servlet HitungNilai, bukan halaman ini.</p>
        <br>
        <a href="HitungNilai">Ke HitungNilai</a>
    </body>
</html>
