package servlet;


import beans.RightEnum;
import logger.Logger;
import org.web3j.crypto.Credentials;
import beans.VoteType;
import user.LoggedUsers;
import user.UserCreator;
import util.BlockchainUtil;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(urlPatterns = {"/UploadUserFileSL"})
@MultipartConfig
/**
 * Author:          Leonhard Gangl
 * Description:     This class works as a "Backend" class for the "UploadUserFileUI.jsp". After the administrator filled everything for an election
 * or poll up and presses the last button he will be redirected to the UploadUserFileUI. In this UI he has to upload
 * the electoral register as an excel file so the Blockchain backend can generate the usernames and passwords and add
 * the right to vote to the election or poll the administrator created. After a while an additional button should appear
 * below the upload button. The administrator has to press it to download the electoral register where all usernames and
 * password to the eligible voter are saved.
 */
public class UploadUserFileSL extends HttpServlet {
    private LoggedUsers userInstance = LoggedUsers.getInstance();
    private UserCreator userCreator = new UserCreator();


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
        if (!userInstance.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = null;
        try {
            //Number of eligible voter
            int count_voter = Integer.parseInt(ServletUtil.filter(req.getParameter("input_Count_Voter").trim()));
            if (count_voter > 0) {
                //Creates Usernames and Passwords and save them to a downloadable xlsx file
                userCreator.createNewUsers(this.getServletContext().getRealPath("/res/userLists/userlist.xlsx"),
                        this.getServletContext().getRealPath("/res/geth_data/keystore/"),
                        (String) req.getSession().getAttribute("newContractAdress"),
                        (VoteType) req.getSession().getAttribute("newTypeOfVote"),
                        (Credentials) req.getSession().getAttribute("credentials"),
                        count_voter, this.getServletContext().getRealPath("/res/admin/"));
                req.setAttribute("newPath", this.getServletContext().getRealPath("/res/userLists/userlist.xlsx"));

                status = "Userkeys wurden erfolgreich generiert!";
                Logger.logInformation("Alle Userkeys wurden erfolgreich erstellt", UploadUserFileSL.class);
            } else {
                status = "Bitte nur Zahlen größer 0 eingeben";
            }
        } catch (Exception ex) {
            status = "Bitte nur Zahlen eingeben";
            Logger.logError("Fehler beim parsen der eingegebenen Zahl: " + ex.toString(), UploadUserFileSL.class);
        }

        req.setAttribute("status", status);
        processRequest(req, resp);
    }
}
