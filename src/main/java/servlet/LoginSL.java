package servlet;

import beans.*;
import handler.ElectionHandler;
import handler.PollHandler;
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
import java.io.IOException;


@WebServlet(urlPatterns = {"/LoginSL"})
public class LoginSL extends HttpServlet {

    private HashGenerator hashInstance;
    private LoggedUsers userInstance;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
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
        String username = ServletUtil.filter((String) req.getAttribute("username"));
        String password = ServletUtil.filter((String) req.getAttribute("password"));

        // Blockchain request return Boolean loggedIn AND Berechtigung user,admin
        if ((int) req.getSession().getAttribute("tries") > 0) {
            try {
                System.out.println("Before Login");
                Credentials cr = BlockchainUtil.loginToBlockhain(username, password);
                System.out.println("Dere");
                req.getSession().setAttribute("credentials", cr);

                //Generation of MD5 Hash
                hashInstance = HashGenerator.getTheInstance();
                String hash = hashInstance.get_SHA_256_SecurePassword(username + password);
                RightEnum right = RightEnum.ADMIN;
                TypeOfVote art = TypeOfVote.Election;
                System.out.println("Seas");
                //log User in List
                userInstance = LoggedUsers.getInstance();
                try {
                    userInstance.login(hash, right, username);
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
                    HttpSession session = req.getSession();
                    session.setAttribute("hash", hash);
                    session.setAttribute("right", right);
                    session.setMaxInactiveInterval(15 * 60);
                    resp.sendRedirect("AdminSettingsUI.jsp");
                    System.out.println("forwarded");
                }
            } catch (CipherException e) {
                int tries = (int) req.getSession().getAttribute("tries");
                if (tries > 1) {
                   req.setAttribute("error", "Fehlerhafte Logindaten! Es bleiben noch " + tries-- + " versuche");
                } else {
                    req.setAttribute("error", "Fehlerhafte Logindaten! Es bleiben noch ein " + tries-- + " Versuch");
                }
                req.getSession().setAttribute("tries", tries);
            } catch (Exception e) {
                req.setAttribute("error", "Fehler beim laden der Datein");
            }
        }

        //405 Method not allowed
        //super.doPost(req, resp);
    }
}
