package servlet;

import dao.MobilDAO;
import model.Mobil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/mobil")
public class MobilServlet extends HttpServlet {

    private final MobilDAO mobilDAO = new MobilDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "list":
                    List<Mobil> list = mobilDAO.getAll();
                    req.setAttribute("listMobil", list);
                    req.getRequestDispatcher("/mobil/list.jsp").forward(req, resp);
                    break;

                case "form":
                    String id = req.getParameter("id");
                    if (id != null && !id.isEmpty()) {
                        Mobil m = mobilDAO.getById(Integer.parseInt(id));
                        req.setAttribute("mobil", m);
                    }
                    req.getRequestDispatcher("/mobil/form.jsp").forward(req, resp);
                    break;

                case "delete":
                    mobilDAO.delete(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("pesan", "Data mobil berhasil dihapus.");
                    resp.sendRedirect(req.getContextPath() + "/mobil?action=list");
                    break;

                default:
                    resp.sendRedirect(req.getContextPath() + "/mobil?action=list");
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
            Mobil m = new Mobil();
            String idStr = req.getParameter("idMobil");
            if (idStr != null && !idStr.isEmpty())
                m.setIdMobil(Integer.parseInt(idStr));

            m.setNopol     (req.getParameter("nopol").trim().toUpperCase());
            m.setMerk      (req.getParameter("merk"));
            m.setTipe      (req.getParameter("tipe"));
            m.setWarna     (req.getParameter("warna"));
            m.setTahun     (Integer.parseInt(req.getParameter("tahun")));
            m.setHargaSewa (Double.parseDouble(req.getParameter("hargaSewa")));
            m.setStatus    (req.getParameter("status"));
            m.setKeterangan(req.getParameter("keterangan"));

            if ("insert".equals(action)) {
                mobilDAO.insert(m);
                req.getSession().setAttribute("pesan", "Data mobil berhasil ditambahkan.");
            } else {
                mobilDAO.update(m);
                req.getSession().setAttribute("pesan", "Data mobil berhasil diperbarui.");
            }
            resp.sendRedirect(req.getContextPath() + "/mobil?action=list");

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
