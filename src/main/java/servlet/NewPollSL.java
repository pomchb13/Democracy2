package servlet;

import beans.*;
import org.web3j.crypto.Credentials;
import handler.PollHandler;
import beans.VoteType;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Ewald on 17.08.2017.
 */
@WebServlet(urlPatterns = {"/NewPollSL"})
public class NewPollSL extends HttpServlet {
    private LoggedUsers lU = LoggedUsers.getInstance();
    private PollHandler pollTester;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/NewPollUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pollStatus = null;
        String answerStatus = null;
        if (req.getParameter("actionButton").equals("createReferendum")) {
            try {
                String title = ServletUtil.filter(req.getParameter("input_Title"));
                String date_from = ServletUtil.filter(req.getParameter("input_Start"));
                String date_due = ServletUtil.filter(req.getParameter("input_End"));
                System.out.println("After reading Parameters");
                boolean voteDiagrams;
                if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
                    voteDiagrams = true;
                else
                    voteDiagrams = false;

                LocalDate vote_fromDate = LocalDate.parse(date_from, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate vote_dueDate = LocalDate.parse(date_due, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                System.out.println("After formattting LocalDate");
                if (LocalDate.now().isBefore(vote_fromDate)
                        || LocalDate.now().isEqual(vote_fromDate)
                        && LocalDate.now().isBefore(vote_dueDate)
                        && vote_fromDate.isBefore(vote_dueDate)) {
                    System.out.println("Before creating poll");
                    PollData pollData = new PollData(title, vote_fromDate, vote_dueDate, voteDiagrams);
                    this.getServletContext().setAttribute("poll", pollData);
                    System.out.println("After creating poll");
                    pollStatus = "Abstimmung erfolgreich erstellt und zwischengespeichert";
                }
            } catch (Exception ex) {
                pollStatus = "Bitte überprüfen Sie Ihre Eingabe!";
            }
            finally {
                req.setAttribute("pollStatus", pollStatus);
                processRequest(req, resp);
            }
        } else if (req.getParameter("actionButton").equals("addAnswer")) {
            if (this.getServletContext().getAttribute("poll") != null) {
                try {
                    String answerTitle = ServletUtil.filter(req.getParameter("input_AnswerTitle"));
                    String answerDescription = ServletUtil.filter((req.getParameter("input_Answer")));
                    PollAnswer pAnswer = new PollAnswer(answerTitle, answerDescription);
                    PollData pollData = (PollData) this.getServletContext().getAttribute("poll");
                    LinkedList<PollAnswer> answerList = pollData.getAnswerList();
                    answerList.add(pAnswer);
                    pollData.setAnswerList(answerList);
                    this.getServletContext().setAttribute("poll", pollData);
                    answerStatus = "Antwort erfolgreich hinzugefügt!";
                } catch (Exception ex)
                {
                    answerStatus = "Bitte die Eingaben überprüfen!";
                }
                req.setAttribute("answerStatus", answerStatus);
                processRequest(req, resp);
            }
        } else {
            Credentials cr = (Credentials) req.getSession().getAttribute("credentials");
            pollTester = new PollHandler(cr);
            PollData pollData = (PollData) this.getServletContext().getAttribute("poll");
            try {
                String contractAdress = pollTester.createContract(pollData.getAnswerList().size(),
                        pollData.getTitle(),
                        pollData.getDate_from(),
                        pollData.getDate_due(),
                        pollData.isDiagramOption());

                this.getServletContext().setAttribute("newContractAdress", contractAdress);
                this.getServletContext().setAttribute("newTypeOfVote", VoteType.POLL);
            } catch (Exception e) {
                req.setAttribute("answerStatus", "Fehler beim Erstellen der Volksabstimmung");
            }
            List<PollAnswer> liAnswers = pollData.getAnswerList();
            for (int i = 0; i < liAnswers.size(); i++) {
                try {
                    pollTester.storeAnswerData(i, liAnswers.get(i).getTitle(), liAnswers.get(i).getDescription());
                } catch (Exception e) {
                    req.setAttribute("answerStatus", "Fehler beim Hinzufügen der Antworten");
                }
            }
            resp.sendRedirect("/UploadUserFileSL");
        }

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
}
