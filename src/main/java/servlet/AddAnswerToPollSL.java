package servlet;

import beans.Poll;
import beans.PollAnswer;
import sun.awt.image.ImageWatched;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(urlPatterns = {"/addAnswerToPollSL"})
public class AddAnswerToPollSL extends HttpServlet {

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
        String error = null;
        try {
            if (!ServletUtil.filter(req.getParameter("pollTitle")).equals("Bitte Abstimmung auswählen")) {
                String pollTitle = ServletUtil.filter(req.getParameter("pollTitle"));
                String answerTitle = ServletUtil.filter(req.getParameter("input_AnswerTitle"));
                String answerDescription = ServletUtil.filter((req.getParameter("input_Answer")));
                PollAnswer pAnswer = new PollAnswer(answerTitle, answerDescription);
                LinkedList<Poll> liListe = (LinkedList<Poll>) this.getServletContext().getAttribute("pollList");
                System.out.println(pAnswer.toString());
                int count = 0;
                for (Poll p : liListe) {
                    if (p.getTitle().equals(pollTitle)) {
                        LinkedList<PollAnswer> answerList = p.getAnswerList();
                        answerList.add(pAnswer);
                        p.setAnswerList(answerList);
                        liListe.set(count, p);
                    }
                    count++;
                }
                this.getServletContext().setAttribute("pollList", liListe);
            }
        }
        catch (Exception ex)
        {
            error = "Bitte überprüfen Sie Ihre Eingaben!";
            req.setAttribute("pollAnswerError", error);
        }
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
