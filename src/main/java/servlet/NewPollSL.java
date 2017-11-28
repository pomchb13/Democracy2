package servlet;

import beans.Poll;
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
 * Created by Ewald on 17.08.2017.
 */
@WebServlet(urlPatterns = {"/NewPollSL"})
public class NewPollSL extends HttpServlet {


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
        System.out.println("POST");
        String action = ServletUtil.filter(req.getParameter("actionButton"));
        System.out.println(action);

        String title = req.getParameter("input_Title");
        String date_from = req.getParameter("input_Start");
        String date_due = req.getParameter("input_End");
        String diaOption = req.getParameter("input_DiaOption");
        String answers = req.getParameter("input_Answers");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        System.out.println(date_from);
        System.out.println(date_due);
        System.out.println(answers);
        String[] sFeld = answers.split(";");
        Poll poll = new Poll(title, LocalDate.parse(date_from, dtf), LocalDate.parse(date_due, dtf), sFeld,
                diaOption=="1" ? false : true);

        System.out.println(poll.toString());


        processRequest(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("hallo");
        //System.out.println(ServletUtil.filter(req.getParameter("createReferendum")));
        processRequest(req, resp);


    }
}
