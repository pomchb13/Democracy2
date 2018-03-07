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
@WebServlet(urlPatterns = {"/EvaluationSL"})
public class EvaluationSL extends HttpServlet {

    private LoggedUsers lU = LoggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // forward to EvaluationPieChartAdminUI
        RequestDispatcher rd = request.getRequestDispatcher("/EvaluationPieChartAdminUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if user has right to be on this page
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
        // get value of clicked button
        String s = req.getParameter("actionbutton").trim();
        System.out.println(s);

        // get both list from the session scope
        LinkedList<PollData> liPollList = (LinkedList<PollData>) req.getSession().getAttribute("PollList");
        LinkedList<ElectionData> liElectionList = (LinkedList<ElectionData>) this.getServletContext().getAttribute("PollList");
        for (ElectionData electionData : liElectionList) {
            // check if value is in this list
            if (electionData.getTitle().equals(s)) {
                // set clickt Data on the session scope
                req.getSession().setAttribute("clicked", electionData);
            }
        }
        for (PollData pollData : liPollList) {
            // check if value is in this list
            if (pollData.getTitle().equals(s)) {
                // set clickt Data on the session scope
                req.getSession().setAttribute("clicked", pollData);
            }
        }
        processRequest(req, resp);
    }
}
