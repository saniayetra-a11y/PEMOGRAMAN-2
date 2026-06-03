package proses;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HitungNilai", urlPatterns = {"/HitungNilai"})
public class HitungNilai extends HttpServlet {

    protected void processRequest(
            HttpServletRequest  request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String sHadir      = request.getParameter("jumlahHadir");
        String sPertemuan  = request.getParameter("jumlahPertemuan");
        String sTugas      = request.getParameter("nilaiTugas");
        String sUTS        = request.getParameter("nilaiUTS");
        String sUAS        = request.getParameter("nilaiUAS");

       
        if (sHadir == null || sHadir.trim().isEmpty()) {
            tampilForm(response);
            return;
        }

        double jumlahHadir     = Double.parseDouble(sHadir);
        double jumlahPertemuan = Double.parseDouble(sPertemuan);
        double nilaiTugas      = Double.parseDouble(sTugas);
        double nilaiUTS        = Double.parseDouble(sUTS);
        double nilaiUAS        = Double.parseDouble(sUAS);

        // Hitung persentase kehadiran
        double persenHadir = (jumlahHadir / jumlahPertemuan) * 100;

        // Hitung nilai akhir: Tugas 20%, UTS 30%, UAS 30%, Kehadiran 20%
        double nilaiAkhir = (nilaiTugas  * 0.20)
                          + (nilaiUTS    * 0.30)
                          + (nilaiUAS    * 0.30)
                          + (persenHadir * 0.20);

        // Grade
        String grade;
        if      (nilaiAkhir >= 80) grade = "A";
        else if (nilaiAkhir >= 70) grade = "B";
        else if (nilaiAkhir >= 60) grade = "C";
        else if (nilaiAkhir >= 50) grade = "D";
        else                       grade = "E";

        // Status
        String status = (nilaiAkhir >= 60 && persenHadir >= 75)
                      ? "Lulus" : "Tidak Lulus";

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'>");
            out.println("<title>Hitung Nilai (Servlet)</title></head>");
            out.println("<body>");
            out.println("<h3>Menghitung Nilai</h3>");
            out.println("<form>");
            out.println("<table>");
            out.println("<tr><td>Jumlah hadir</td>"
                    + "<td><input type='text' value='" + (int)jumlahHadir + "'/></td></tr>");
            out.println("<tr><td>Jumlah pertemuan</td>"
                    + "<td><input type='text' value='" + (int)jumlahPertemuan + "'/></td></tr>");
            out.println("<tr><td>Nilai tugas</td>"
                    + "<td><input type='text' value='" + (int)nilaiTugas + "'/></td></tr>");
            out.println("<tr><td>Nilai UTS</td>"
                    + "<td><input type='text' value='" + (int)nilaiUTS + "'/></td></tr>");
            out.println("<tr><td>Nilai UAS</td>"
                    + "<td><input type='text' value='" + (int)nilaiUAS + "'/></td></tr>");
            out.println("<tr><td>Nilai Akhir</td>"
                    + "<td><input type='text' value='" + nilaiAkhir + "'/></td></tr>");
            out.println("<tr><td>Grade</td>"
                    + "<td><input type='text' value='" + grade + "'/></td></tr>");
            out.println("<tr><td>Status</td>"
                    + "<td><input type='text' value='" + status + "'/></td></tr>");
            out.println("</table>");
            out.println("<br><input type='button' value='Hitung' onclick='history.back()'/>");
            out.println("</form>");
            out.println("</body></html>");
        }
    }

    private void tampilForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'>");
            out.println("<title>Hitung Nilai (Servlet)</title></head>");
            out.println("<body>");
            out.println("<h3>Menghitung Nilai</h3>");
            out.println("<form method='post' action='HitungNilai'>");
            out.println("<table>");
            out.println("<tr><td>Jumlah hadir</td><td><input type='text' name='jumlahHadir'/></td></tr>");
            out.println("<tr><td>Jumlah pertemuan</td><td><input type='text' name='jumlahPertemuan'/></td></tr>");
            out.println("<tr><td>Nilai tugas</td><td><input type='text' name='nilaiTugas'/></td></tr>");
            out.println("<tr><td>Nilai UTS</td><td><input type='text' name='nilaiUTS'/></td></tr>");
            out.println("<tr><td>Nilai UAS</td><td><input type='text' name='nilaiUAS'/></td></tr>");
            out.println("<tr><td>Nilai Akhir</td><td><input type='text' readonly/></td></tr>");
            out.println("<tr><td>Grade</td><td><input type='text' readonly/></td></tr>");
            out.println("<tr><td>Status</td><td><input type='text' readonly/></td></tr>");
            out.println("</table>");
            out.println("<br><input type='submit' value='Hitung'/>");
            out.println("</form>");
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