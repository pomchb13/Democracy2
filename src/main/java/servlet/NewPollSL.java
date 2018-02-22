package servlet;

import beans.Poll;
import beans.rightEnum;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Map;


/**
 * Created by Ewald on 17.08.2017.
 */
@WebServlet(urlPatterns = {"/newPollSL"})
public class NewPollSL extends HttpServlet {

    private LinkedList<Poll> liPollList = new LinkedList<>();
    private loggedUsers lU = loggedUsers.getInstance();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/newPollUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Post");
        String error = null;
        try {
        String title = req.getParameter("input_Title");
        String date_from = req.getParameter("input_Start");
        String date_due = req.getParameter("input_End");
        String diaOption = req.getParameter("input_DiaOption");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Poll poll = new Poll(title, LocalDate.parse(date_from, dtf), LocalDate.parse(date_due, dtf),
                diaOption=="1" ? false : true);

        System.out.println(poll.toString());
        liPollList.add(poll);
        }
        catch (Exception ex)
        {
            error = "Bitte überprüfen Sie Ihre Eingabe!";
            req.setAttribute("PollError", error);
        }
        this.getServletContext().setAttribute("pollList", liPollList);

        processRequest(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String hash = (String) session.getAttribute("hash");
        rightEnum right = (rightEnum) session.getAttribute("right");

        Map<String, rightEnum> loggedIn = lU.getTokenList();
        boolean isNotLoggedIn = true;
        for (Map.Entry<String, rightEnum> e : loggedIn.entrySet()) {
            if (e.getKey().equals(hash) && e.getValue().equals(right)) {
                if (right == rightEnum.ADMIN) {
                    isNotLoggedIn = false;
                }
            }
        }
        if (isNotLoggedIn) {
            resp.sendRedirect("/loginSL");
        } else {
            processRequest(req, resp);
        }

    }
}
