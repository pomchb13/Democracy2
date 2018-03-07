package servlet;

/**
 * Created by Leonhard on 05.03.2018
 *
 * This class works as a "Backend" class for the "UploadUserFileUI.jsp". After the administrator filled everything for an election
 * or poll up and presses the last button he will be redirected to the UploadUserFileUI. In this UI he has to upload
 * the electoral register as an excel file so the Blockchain backend can generate the usernames and passwords and add
 * the right to vote to the election or poll the administrator created. After a while an additional button should appear
 * below the upload button. The administrator has to press it to download the electoral register where all usernames and
 * password to the eligible voter are saved.
 *
 */

import beans.RightEnum;
import org.web3j.crypto.Credentials;
import test.VoteType;
import user.LoggedUsers;
import user.UserCreator;
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

@WebServlet(urlPatterns = {"/UploadUserFileSL"})
@MultipartConfig
public class UploadUserFileSL extends HttpServlet {
    private LinkedList<String> liFilenames = new LinkedList<>();
    private LoggedUsers lU = LoggedUsers.getInstance();
    private UserCreator userCreat = new UserCreator();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/UploadUserFileUI.jsp");
        rd.forward(request, response);
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
        final String path = this.getServletContext().getRealPath("/res/files");
        final Part filePart = req.getPart("input_Excel");
        if (filePart != null) {
            String[] fileField = getFileName(filePart).split("\\.");
            final String fileName = path + File.separator + fileField[0] + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy-hh_mm_ss")) + "." + fileField[1];

            OutputStream out = null;
            InputStream filecontent = null;
            System.out.println(fileName);
            try {
                out = new FileOutputStream(new File(fileName));
                filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                File f = new File(fileName);

                userCreat.createNewUsers(fileName, this.getServletContext().getRealPath("/res/userLists"),
                        this.getServletContext().getRealPath("/res/geth_data/keystore/"),
                        (String)this.getServletContext().getAttribute("newContractAdress"),
                        (VoteType) this.getServletContext().getAttribute("newTypeOfVote"), (Credentials) req.getSession().getAttribute("credentials"));
                this.getServletContext().setAttribute("newPath", this.getServletContext().getRealPath("/res/userLists")+File.separator+f.getName());
                status = "File wurde erfolgreich hochgeladen!";
            } catch (FileNotFoundException fne) {
                System.out.println(fne.toString());
                fne.printStackTrace();
                status = "Fehlgeschlagen! Bitte versuchen Sie es erneut, oder verwenden Sie eine andere Datei";
            } catch (Exception e) {
                e.printStackTrace();
                status = "Fehler beim erstellen der User!";
            } finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
        } else {
            status = "Bitte eine Datei auswählen!";
        }

        req.setAttribute("status", status);
        System.out.println(status);
        System.out.println("End doPost");
        processRequest(req, resp);
    }
}
