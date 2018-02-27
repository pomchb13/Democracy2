package servlet;

import beans.Poll;
import beans.PollAnswer;
import beans.RightEnum;
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
import java.util.Enumeration;
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
        if (req.getParameter("actionButton").equals("createReferendum")) {
            try {
                String title = ServletUtil.filter(req.getParameter("input_Title"));
                String date_from = ServletUtil.filter(req.getParameter("input_Start"));
                String date_due = ServletUtil.filter(req.getParameter("input_End"));

                boolean voteDiagrams;
                if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
                    voteDiagrams = true;
                else
                    voteDiagrams = false;

                LocalDate vote_fromDate = LocalDate.parse(date_from, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate vote_dueDate = LocalDate.parse(date_due, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                if (LocalDate.now().isBefore(vote_fromDate)
                        || LocalDate.now().isEqual(vote_fromDate)
                        && LocalDate.now().isBefore(vote_dueDate)
                        && vote_fromDate.isBefore(vote_dueDate)) {

                    Poll poll = new Poll(title, vote_fromDate, vote_dueDate, voteDiagrams);
                    System.out.println(poll.toString());
                    //TODO: Save in Blockchain
                    liPollList.add(poll);
                    this.getServletContext().setAttribute("pollList", liPollList);
                }
            } catch (Exception ex) {
                error = "Bitte überprüfen Sie Ihre Eingabe!";
                req.setAttribute("PollError", error);
            }
        } else if (req.getParameter("actionButton").equals("addAnswer")) {
            System.out.println("AddAnswer");
            System.out.println(req.getParameter("hiddenPoll"));

            Enumeration<String> eNames = req.getParameterNames();
            while (eNames.hasMoreElements() != false) {
                System.out.println(eNames.nextElement().toString());
            }
            System.out.println(eNames.toString());
            System.out.println("Nach der Ausgabe");


            if (!ServletUtil.filter(req.getParameter("hiddenPoll")).isEmpty()) {
                System.out.println("AddAnswer to Poll");
                String pollTitle = ServletUtil.filter(req.getParameter("hiddenPoll"));
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
                        System.out.println(p.toString());
                    }
                    count++;
                }
                this.getServletContext().setAttribute("pollList", liListe);
            }
        }

        processRequest(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");
        if (!lU.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/loginSL");
        } else {
            processRequest(req, resp);
        }

    }
}
