package servlet;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "list":
                    List<Customer> list = customerDAO.getAll();
                    req.setAttribute("listCustomer", list);
                    req.getRequestDispatcher("/customer/list.jsp").forward(req, resp);
                    break;

                case "form":
                    String id = req.getParameter("id");
                    if (id != null && !id.isEmpty()) {
                        Customer c = customerDAO.getById(Integer.parseInt(id));
                        req.setAttribute("customer", c);
                    }
                    req.getRequestDispatcher("/customer/form.jsp").forward(req, resp);
                    break;

                case "delete":
                    customerDAO.delete(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("pesan", "Data customer berhasil dihapus.");
                    resp.sendRedirect(req.getContextPath() + "/customer?action=list");
                    break;

                default:
                    resp.sendRedirect(req.getContextPath() + "/customer?action=list");
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
            Customer c = new Customer();
            String idStr = req.getParameter("idCustomer");
            if (idStr != null && !idStr.isEmpty())
                c.setIdCustomer(Integer.parseInt(idStr));

            c.setNik   (req.getParameter("nik").trim());
            c.setNama  (req.getParameter("nama"));
            c.setAlamat(req.getParameter("alamat"));
            c.setNoTelp(req.getParameter("noTelp"));
            c.setEmail (req.getParameter("email"));

            if ("insert".equals(action)) {
                customerDAO.insert(c);
                req.getSession().setAttribute("pesan", "Data customer berhasil ditambahkan.");
            } else {
                customerDAO.update(c);
                req.getSession().setAttribute("pesan", "Data customer berhasil diperbarui.");
            }
            resp.sendRedirect(req.getContextPath() + "/customer?action=list");

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
