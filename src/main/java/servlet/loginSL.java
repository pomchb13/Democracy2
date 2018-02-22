package servlet;

import beans.rightEnum;
import user.hashGenerator;
import user.loggedUsers;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
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

    private hashGenerator hashInstance;
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
       // super.doGet(req, resp);
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hallo");
        String username = ServletUtil.filter((String) req.getAttribute("username"));
        String password = ServletUtil.filter((String) req.getAttribute("password"));

        //Blockchain request return Boolean loggedIn AND Berechtigung user,admin


        //Generation of MD5 Hash
        hashInstance = hashGenerator.getTheInstance();
        String hash = hashInstance.get_SHA_256_SecurePassword(username+password);
        rightEnum right = rightEnum.USER;

        //log User in List

        userInstance = loggedUsers.getInstance();
        try {
            userInstance.login(hash, right);
        } catch (Exception e) {
            req.setAttribute("error", "User bereits eingeloggt");
        }

        userInstance.outPutUserList();

        if (right == rightEnum.USER)
        {
            //toDo: Abfrage auf Wahlrecht
            HttpSession session = req.getSession();
            session.setAttribute("hash", hash);
            session.setAttribute("right", right);
            session.setMaxInactiveInterval(15*60);
            resp.sendRedirect("adminSettingsUI.jsp");
        }
        else if(right == rightEnum.ADMIN)
        {
            HttpSession session = req.getSession();
            session.setAttribute("hash", hash);
            session.setAttribute("right", right);
            session.setMaxInactiveInterval(15*60);
            resp.sendRedirect("adminSettingsUI.jsp");
        }


        //405 Method not allowed
        //super.doPost(req, resp);
    }
}
