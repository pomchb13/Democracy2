package servlet;

import beans.RightEnum;
import user.HashGenerator;
import user.loggedUsers;
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


@WebServlet(urlPatterns = {"/loginSL"})
public class loginSL extends HttpServlet {

    private HashGenerator hashInstance;
    private loggedUsers userInstance;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/loginUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hallo");

        String username = ServletUtil.filter((String) req.getAttribute("username"));
        String password = ServletUtil.filter((String) req.getAttribute("password"));

        //Blockchain request return Boolean loggedIn AND Berechtigung user,admin


        //Generation of MD5 Hash
        hashInstance = HashGenerator.getTheInstance();
        String hash = hashInstance.get_SHA_256_SecurePassword(username + password);
        RightEnum right = RightEnum.ADMIN;

        //log User in List

        userInstance = loggedUsers.getInstance();
        try {
            userInstance.login(hash, right);
        } catch (Exception e) {
            req.setAttribute("error", "User bereits eingeloggt");
        }

        userInstance.outPutUserList();

        if (right == RightEnum.USER) {
            //toDo: Abfrage auf Wahlrecht
            processRequest(req, resp);

        } else if (right == RightEnum.ADMIN) {
            HttpSession session = req.getSession();
            session.setAttribute("hash", hash);
            session.setAttribute("right", right);
            session.setMaxInactiveInterval(15 * 60);
            resp.sendRedirect("adminSettingsUI.jsp");
            System.out.println("forwarded");
        }


        //405 Method not allowed
        //super.doPost(req, resp);
    }
}
