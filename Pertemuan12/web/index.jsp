<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Menghitung Harga</title>
    </head>
    <body>
        <h3>Form Memasukkan Nilai</h3>
        <form method="post" action="/Pertemuan11/HitungHarga">
            <table>
                <tr>
                    <td>Nama Barang</td>
                    <td><input type="text" name="namaBarang" /></td>
                </tr>
                <tr>
                    <td>Harga Satuan</td>
                    <td><input type="text" name="hargaSatuan" /></td>
                </tr>
                <tr>
                    <td>Jumlah</td>
                    <td><input type="text" name="jumlah" /></td>
                </tr>
            </table>
            <br>
            Diskon diberikan sebesar 5% jika jumlah >= 100<br>
            dan total harga sebelum diskon >= 1000000
            <br><br>
            <input type="submit" value="Hitung" />
        </form>
        <br><a href="/Pertemuan11/index.html">Kembali ke Menu</a>
    </body>
</html>
