package servlet;

import beans.ElectionData;
import beans.PollData;
import beans.RightEnum;
import user.LoggedUsers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Ewald on 06.03.2018.
 */
@WebServlet(urlPatterns = {"/AdminSettingsSL"})
public class AdminSettingsSL extends HttpServlet {

    private LoggedUsers lU = LoggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {

        //ToDo: Windi's code bitte hier hin --> Alle Wahlen und Abstimmungen!
        super.init(config);
        LinkedList<PollData> liPollList = new LinkedList<>();
        LinkedList<ElectionData> liElectioData = new LinkedList<>();


        this.getServletContext().setAttribute("PollList", liPollList);
        this.getServletContext().setAttribute("ElectionList", liElectioData);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/AdminSettingsUI.jsp");
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
