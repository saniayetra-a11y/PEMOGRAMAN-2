package servlet;

import dao.TransaksiDAO;
import model.Transaksi;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/laporan")
public class LaporanServlet extends HttpServlet {

    private final TransaksiDAO transaksiDAO = new TransaksiDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // default: bulan berjalan
        String tglAwal  = req.getParameter("tglAwal");
        String tglAkhir = req.getParameter("tglAkhir");

        if (tglAwal == null || tglAwal.isEmpty()) {
            LocalDate now = LocalDate.now();
            tglAwal  = now.withDayOfMonth(1).toString();
            tglAkhir = now.withDayOfMonth(now.lengthOfMonth()).toString();
        }

        try {
            List<Transaksi> list = transaksiDAO.getLaporan(tglAwal, tglAkhir);

            double totalPendapatan = list.stream()
                .mapToDouble(t -> t.getTotalBiaya() + t.getDenda()).sum();
            long jumlahTransaksi = list.size();
            long jumlahSelesai   = list.stream()
                .filter(t -> "selesai".equals(t.getStatus())).count();

            req.setAttribute("listLaporan",      list);
            req.setAttribute("totalPendapatan",  totalPendapatan);
            req.setAttribute("jumlahTransaksi",  jumlahTransaksi);
            req.setAttribute("jumlahSelesai",    jumlahSelesai);
            req.setAttribute("tglAwal",          tglAwal);
            req.setAttribute("tglAkhir",         tglAkhir);

            req.getRequestDispatcher("/laporan/laporan.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
