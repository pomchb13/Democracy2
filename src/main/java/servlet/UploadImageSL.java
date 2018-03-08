package servlet;

/**
    Created by Leonhard 23.02.2018

    In this Servlet we define the "Backend" for the "UploadImageUI.jsp". This Servlet grants that the images put from
    the Administrator into the fileinput will be saved to our server environment. In our project structure the server
    environment is the "out" directory. Before the administrator well be redirected to the JSP it checks if the
    administrator is logged in.
 */


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

    //The Instance where all logged users and administrator are saved
    private LoggedUsers lU = LoggedUsers.getInstance();

    /**
     * @param config
     * In the init Method we need to set the path in the BlockchainUtil to the keystore in the server environment.
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    /**
     * @param request
     * @param response
     *
     * In this Method we only get the RequestDispatcher which forwards to the "UploadImageUI.jsp".
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/UploadImageUI.jsp");
        rd.forward(request, response);
    }

    /**
     * @param part --> uploaded image saved in a part object
     * The method takes the filename from the part and deletes useless blanks.
     * @return the filename from the image
     */
    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * @param req
     * @param resp
     *
     * The doPost will be fired if the user presses the upload button in the JSP. Firstly it gets the path for the
     * server environment. Then it reads the Part sent via the encryption type mulipart/form-data from the fileinput.
     * After we get the filename, we add a Timestamp made of date and time to the filename, so we could ensure that
     * no file will be overwritten. Then we add the filename to a list. This list can be seen in the NewVoteUI to add
     * a portrait to the candidate. If everything goes well the status "Bild wurde erfolgreich hochgeladen" will be shown
     * above the button. If not, it will show an other message. If no file is selected it will show a matching status.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = null;
        final String path = this.getServletContext().getRealPath("/res/images");
        final Part filePart = req.getPart("input_Picture");
        if (filePart != null) {
            String[] fileField = getFileName(filePart).split("\\.");
            //Adding the a DateTimestamp to the filename to be sure no files will be overwritten
            final String fileName = fileField[0] + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("_dd_MM_yyyy-hh_mm_ss")) + "." + fileField[1];
            LinkedList<String> liFilenames = (LinkedList<String>) this.getServletContext().getAttribute("liFilenames");
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
        processRequest(req, resp);
    }

    /**
     * @param req
     * @param resp
     * Because the doGet will be fired everytime we load the JSP, we had the method to check, if the person is logged in,
     * in there. It only takes the hash from the session object and checks with the loggedUserInstance if the login is correct.
     * @throws ServletException
     * @throws IOException
     */
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
