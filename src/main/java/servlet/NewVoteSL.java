package servlet;

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
import java.util.LinkedList;

/**
 * Created by Ewald on 17.08.2017.
 */
@WebServlet(urlPatterns = {"/NewVoteSL"})
public class NewVoteSL extends HttpServlet {

    private LinkedList<String> liAnswers = new LinkedList<>();
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST");
        String action = ServletUtil.filter(req.getParameter("actionButton"));
        System.out.println(action);

        if (action.equalsIgnoreCase("addAnswer"))
        {
            String answer = req.getParameter("answer");
            System.out.println(answer);
            liAnswers.add(answer);
            this.getServletContext().setAttribute("answers", liAnswers);

        }
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hallo");
        System.out.println(ServletUtil.filter(req.getParameter("createReferendum")));
        processRequest(req, resp);


    }
}