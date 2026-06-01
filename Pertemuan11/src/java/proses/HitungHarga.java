package proses;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HitungHarga", urlPatterns = {"/HitungHarga"})
public class HitungHarga extends HttpServlet {

    protected void processRequest(
            HttpServletRequest  request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String namaBarang  = request.getParameter("namaBarang");
        double hargaSatuan = Double.parseDouble(request.getParameter("hargaSatuan"));
        int    jumlah      = Integer.parseInt(request.getParameter("jumlah"));

        double total  = hargaSatuan * jumlah;
        double diskon = 0;

        if (jumlah >= 100 && total >= 1000000) {
            diskon = total * 0.05;
        }

        total = total - diskon;

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><meta charset='UTF-8'><title>Hasil Penghitungan Harga</title></head>");
            out.println("<body>");
            out.println("<h3>Hasil Penghitungan Harga</h3>");
            out.println("<table>");
            out.println("<tr><td>Nama Barang</td><td>: " + namaBarang    + "</td></tr>");
            out.println("<tr><td>Harga Satuan</td><td>: " + (int)hargaSatuan + "</td></tr>");
            out.println("<tr><td>Jumlah</td><td>: "       + jumlah        + "</td></tr>");
            out.println("<tr><td>Diskon</td><td>: "        + (int)diskon  + "</td></tr>");
            out.println("<tr><td>Total</td><td>: "         + (int)total   + "</td></tr>");
            out.println("</table>");
            out.println("<br><input type='button' value='Kembali' onclick='history.back()' />");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { processRequest(req, res); }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { processRequest(req, res); }
}