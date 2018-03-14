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
 * Author:          Ewald Hartmann
 * Description:
 */
@WebServlet(urlPatterns = {"/EvaluationSL"})
public class EvaluationSL extends HttpServlet {

    private LoggedUsers userInstance = LoggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/EvaluationPieChartAdminUI.jsp");
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
        // get Title of clicked button
        String voteObjectTitle = req.getParameter("actionbutton").trim();

        // get both list from the session scope
        LinkedList<PollData> pollList = (LinkedList<PollData>) req.getSession().getAttribute("pollList");
        LinkedList<ElectionData> electionList = (LinkedList<ElectionData>) req.getSession().getAttribute("electionList");
        for (ElectionData election : electionList) {
            // check if value is in this list
            if (election.getTitle().equals(voteObjectTitle)) {
                // set cilckt Data on the session scope
                req.getSession().setAttribute("evaluationObject", election);
            }
        }
        for (PollData poll : pollList) {
            // check if value is in this list
            if (poll.getTitle().equals(voteObjectTitle)) {
                // set clickt Data on the session scope
                req.getSession().setAttribute("evaluationObject", poll);
            }
        }
        processRequest(req, resp);
    }
}
