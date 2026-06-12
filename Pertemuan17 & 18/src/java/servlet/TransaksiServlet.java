package servlet;

import dao.CustomerDAO;
import dao.MobilDAO;
import dao.TransaksiDAO;
import model.Customer;
import model.Mobil;
import model.Transaksi;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/transaksi")
public class TransaksiServlet extends HttpServlet {

    private final TransaksiDAO transaksiDAO = new TransaksiDAO();
    private final MobilDAO     mobilDAO     = new MobilDAO();
    private final CustomerDAO  customerDAO  = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {

                case "list":
                    req.setAttribute("listTransaksi", transaksiDAO.getAll());
                    req.getRequestDispatcher("/transaksi/list.jsp").forward(req, resp);
                    break;

                case "formSewa":
                    req.setAttribute("listCustomer", customerDAO.getAll());
                    req.setAttribute("listMobil",    mobilDAO.getTersedia());
                    req.getRequestDispatcher("/transaksi/formSewa.jsp").forward(req, resp);
                    break;

                case "formKembali":
                    String id = req.getParameter("id");
                    Transaksi t = transaksiDAO.getById(Integer.parseInt(id));
                    req.setAttribute("transaksi", t);
                    req.getRequestDispatcher("/transaksi/formKembali.jsp").forward(req, resp);
                    break;

                case "detail":
                    Transaksi detail = transaksiDAO.getById(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("transaksi", detail);
                    req.getRequestDispatcher("/transaksi/detail.jsp").forward(req, resp);
                    break;

                default:
                    resp.sendRedirect(req.getContextPath() + "/transaksi?action=list");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        try {
            if ("sewa".equals(action)) {
                // ── Proses Penyewaan ────────────────────────────────────
                Transaksi t   = new Transaksi();
                int idMobil   = Integer.parseInt(req.getParameter("idMobil"));
                int lamaSewa  = Integer.parseInt(req.getParameter("lamaSewa"));

                Mobil mobil   = mobilDAO.getById(idMobil);
                double total  = mobil.getHargaSewa() * lamaSewa;

                t.setKodeTransaksi    (transaksiDAO.generateKode());
                t.setIdCustomer       (Integer.parseInt(req.getParameter("idCustomer")));
                t.setIdMobil          (idMobil);
                t.setTglSewa          (req.getParameter("tglSewa"));
                t.setTglKembaliRencana(req.getParameter("tglKembaliRencana"));
                t.setLamaSewa         (lamaSewa);
                t.setTotalBiaya       (total);
                t.setKeterangan       (req.getParameter("keterangan"));

                transaksiDAO.insert(t);
                // ubah status mobil menjadi disewa
                mobilDAO.updateStatus(idMobil, "disewa");

                req.getSession().setAttribute("pesan",
                    "Transaksi sewa berhasil! Kode: " + t.getKodeTransaksi());
                resp.sendRedirect(req.getContextPath() + "/transaksi?action=list");

            } else if ("kembali".equals(action)) {
                // ── Proses Pengembalian ─────────────────────────────────
                int idTransaksi     = Integer.parseInt(req.getParameter("idTransaksi"));
                int idMobil         = Integer.parseInt(req.getParameter("idMobil"));
                String tglAktual    = req.getParameter("tglKembaliAktual");
                String tglRencana   = req.getParameter("tglKembaliRencana");
                double hargaSewa    = Double.parseDouble(req.getParameter("hargaSewa"));

                double denda = transaksiDAO.hitungDenda(tglRencana, tglAktual, hargaSewa);

                transaksiDAO.prosesPengembalian(idTransaksi, tglAktual, denda);
                // kembalikan status mobil
                mobilDAO.updateStatus(idMobil, "tersedia");

                String pesanDenda = denda > 0
                    ? " Denda keterlambatan: Rp " + String.format("%,.0f", denda)
                    : "";
                req.getSession().setAttribute("pesan",
                    "Pengembalian mobil berhasil diproses." + pesanDenda);
                resp.sendRedirect(req.getContextPath() + "/transaksi?action=list");
            }

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
