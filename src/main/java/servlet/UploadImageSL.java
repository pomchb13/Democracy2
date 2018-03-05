package servlet;

import beans.RightEnum;
import user.LoggedUsers;
import util.BlockchainUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@WebServlet(urlPatterns = {"/UploadImageSL"})
@MultipartConfig
public class UploadImageSL extends HttpServlet {

    private LinkedList<String> liFilenames = new LinkedList<>();
    private LoggedUsers lU = LoggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("processRequest");
        RequestDispatcher rd = request.getRequestDispatcher("/UploadImageUI.jsp");
        System.out.println(request.getRequestURL());
        rd.forward(request, response);
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("doPost");
        String status = null;
        final String path = this.getServletContext().getRealPath("/") + "images";
        final Part filePart = req.getPart("input_Picture");
        if (filePart != null) {
            String[] fileField = getFileName(filePart).split("\\.");
            final String fileName = fileField[0] + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("_dd_MM_yyyy-hh_mm_ss")) + "." + fileField[1];
            System.out.println(fileName);
            System.out.println(path);
            liFilenames.add(fileName);
            this.getServletContext().setAttribute("liFilenames", liFilenames);

            OutputStream out = null;
            InputStream filecontent = null;

            try {
                out = new FileOutputStream(new File(path + File.separator
                        + fileName));
                filecontent = filePart.getInputStream();


                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                status = "Bild wurde erfolgreich hochgeladen!";
                System.out.println("ImageUploaded");
            } catch (FileNotFoundException fne) {
                status = "Fehlgeschlagen! Bitte versuchen Sie es erneut, oder verwenden Sie eine andere Datei";
            } finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
        }else{
            status = "Bitte ein Bild auswählen!";
        }

        req.setAttribute("status", status);
        System.out.println(status);
        System.out.println("End doPost");
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");
        if (!lU.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }
    }
}
