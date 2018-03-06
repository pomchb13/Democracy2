package servlet;

import beans.RightEnum;
import user.HashGenerator;
import user.LoggedUsers;
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

/**
 * Created by Ewald on 22.02.2018.
 */
@WebServlet(urlPatterns = {"/LogoutSL"})
public class LogoutSL extends HttpServlet {
    private LoggedUsers lU = LoggedUsers.getInstance();


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
        String hash = (String) req.getSession().getAttribute("hash");
        req.getSession().setAttribute("hash", "");
        lU.logout(hash);
        lU.outPutUserList();
        resp.sendRedirect("/LoginSL");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
