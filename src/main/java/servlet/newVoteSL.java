package servlet;

import beans.Vote;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * Created by Leonhard on 28.11.2017.
 */
@WebServlet(urlPatterns = {"/newVoteSL"})
public class newVoteSL extends HttpServlet {
    public LinkedList<Vote> voteList = new LinkedList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/newVoteUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String voteTitle = ServletUtil.filter(req.getParameter("input_Title"));
        String fromDate = ServletUtil.filter((req.getParameter("input_Start")));
        String dueDate = ServletUtil.filter(req.getParameter("input_End"));

        boolean voteDiagrams;
        if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
            voteDiagrams = true;
        else
            voteDiagrams = false;


        LocalDate vote_fromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate vote_dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Vote newVote = new Vote(voteTitle, vote_fromDate, vote_dueDate, voteDiagrams);
        //Testing Statement --> Delete After not needed
        System.out.println(newVote.toString());
        voteList.add(newVote);
        this.getServletContext().setAttribute("voteList", voteList);
        processRequest(req, resp);
    }
}
