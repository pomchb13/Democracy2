package servlet;

import beans.*;
import handler.AdminHandler;
import handler.ElectionHandler;
import handler.PollHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import user.HashGenerator;
import user.LoggedUsers;
import util.BlockchainUtil;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;


@WebServlet(urlPatterns = {"/LoginSL"})
public class LoginSL extends HttpServlet {

    private HashGenerator hashInstance;
    private LoggedUsers userInstance;
    private LinkedList<String> liFilenames = new LinkedList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
        System.out.println(BlockchainUtil.getPATH());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/LoginUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("tries") == null) {
            req.getSession().setAttribute("tries", 4);
        }
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = ServletUtil.filter((String) req.getParameter("username"));
        String password = ServletUtil.filter((String) req.getParameter("password"));
        System.out.println(username + " " + password);
        // Blockchain request return Boolean loggedIn AND Berechtigung user,admin
        if ((int) req.getSession().getAttribute("tries") > 0) {
            try {
                Credentials cr = BlockchainUtil.loginToBlockhain(username, password);
                System.out.println(cr.getAddress());

                req.getSession().setAttribute("credentials", cr);
                System.out.println("After setting cr to session");
                //Generation of MD5 Hash
                hashInstance = HashGenerator.getTheInstance();
                String hash = hashInstance.get_SHA_256_SecurePassword(username + password);
                AdminHandler adminHandler = new AdminHandler();
                RightEnum right = RightEnum.USER;
                if(adminHandler.checkIfAdmin(new Address(username))){
                    right = RightEnum.ADMIN;
                }
                TypeOfVote art = TypeOfVote.Election;
                System.out.println("after hashing");
                //log User in List
                userInstance = LoggedUsers.getInstance();
                try {
                    userInstance.login(hash, right, username);
                    System.out.println("after loggin in");
                } catch (Exception e) {
                    req.setAttribute("error", "User bereits eingeloggt");
                }

                if (right == RightEnum.USER) {
                    //ToDo: Abfrage auf Wahlrecht
                    HttpSession session = req.getSession();
                    session.setAttribute("hash", hash);
                    session.setAttribute("right", right);
                    session.setMaxInactiveInterval(15 * 60);
                    //ToDo: Abfrage welche Wahl !!!
                    if (art == TypeOfVote.Election) {
                        ElectionHandler handler = new ElectionHandler(cr);
                        ElectionData ed = handler.getElectionData();
                        HttpSession ses = req.getSession();
                        ses.setAttribute("election", ed);
                        ses.setMaxInactiveInterval(15 * 60);
                        resp.sendRedirect("ElectionUI.jsp");
                    } else {
                        PollHandler handler = new PollHandler(cr);
                        PollData pa = handler.getPollData();
                        HttpSession ses = req.getSession();
                        ses.setAttribute("poll", pa);
                        ses.setMaxInactiveInterval(15 * 60);
                        resp.sendRedirect("PollUI.jsp");
                    }
                } else if (right == RightEnum.ADMIN) {
                    System.out.println("in Admin");
                    HttpSession session = req.getSession();
                    session.setAttribute("hash", hash);
                    session.setAttribute("right", right);
                    session.setMaxInactiveInterval(15 * 60);
                    System.out.println("Before getting all Filenames");
                    this.getAllFiles();
                    System.out.println("After getting all Filenames");
                    this.getServletContext().setAttribute("liFilenames", liFilenames);
                    System.out.println("Before forward");
                    resp.sendRedirect("AdminSettingsUI.jsp");
                    System.out.println("forwarded");
                }
            } catch (Exception e) {
                int tries = (int) req.getSession().getAttribute("tries");
                if (tries > 1) {
                    req.setAttribute("error", "Fehlerhafte Logindaten! Es bleiben noch " + tries-- + " versuche");
                } else {
                    req.setAttribute("error", "Fehlerhafte Logindaten! Es bleiben noch ein " + tries-- + " Versuch");
                }
                req.getSession().setAttribute("tries", tries);
            } /*catch (Exception e) {
                req.setAttribute("error", "Fehler beim laden der Datein");
            }*/
        }

        //405 Method not allowed
        //super.doPost(req, resp);
    }

    private void getAllFiles() {
        File file = new File(this.getServletContext().getRealPath("/res/images"));
        System.out.println("After getting the Directory");
        File[] files = file.listFiles();
        System.out.println("Adter reading all Files from Directory");
        for (int i = 0; i < files.length; i++) {
            if (!files[i].getName().equals("dummy")) {
                if (!files[i].isDirectory()) {
                    System.out.println("Adding File to List");
                    liFilenames.add(files[i].getName());
                }
            }
        }
    }
}
