package servlet;

import beans.*;
import handler.AdminHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import handler.PollHandler;
import beans.VoteType;
import user.LoggedUsers;
import util.AdminReader;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:          Ewald Hartmann
 * Created on:
 * Description:     This Servlet java class is responsible for creating a new PollContract and push it to the Blockchain. Before
 * the administrator is able to create a new PollContract it also checks if the administrator is logged in correctly.
 * The creation of the new PollContract could take a while because the PollHandler needs to push it to the Blockchain and
 * create all possible answers the administrator created.
 */

@WebServlet(urlPatterns = {"/NewPollSL"})
public class NewPollSL extends HttpServlet {
    //The Instance where all logged users and administrator are saved
    private LoggedUsers lU = LoggedUsers.getInstance();
    //The Pollhandler object is responsible for the communication with the blockchain
    private PollHandler pollTester;

    /**
     * @param config In the init Method we need to set the path in the BlockchainUtil to the keystore in the server environment.
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    /**
     * @param request
     * @param response In this Method we only get the RequestDispatcher which forwards to the "NewPollUI.jsp".
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/NewPollUI.jsp");
        rd.forward(request, response);
    }

    /**
     * @param req
     * @param resp In the doPost method we catch a variety of post requests from the "NewPollUI.jsp". Every form has its own if section
     *             where the functionality is implemented. In the first if-section it only reads the main properties for a poll,
     *             creates the PollData-object saves it to the application scope. The CandidateList in the object is initialised but empty.
     *             The second section if responsible for creating an answer and adding it to the PollData-object in the application scope.
     *             In the else-section the doPost Method checks if the administrator is logged in correctly, creates a PollHandler object, to
     *             communicate with the Blockchain, and then let the Blockchain create the PollContract. This may take a while. After creating the
     *             PollContract the Blockchain needs to add the answers to the PollContract. When everything did go will, we will be forwarded to the
     *             "UploadUserFile.jsp" where we have to upload an excel sheet where all eligible voters are saved.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(Files.exists(Paths.get((String) this.getServletContext().getRealPath("/res/userLists/userlist.xlsx"))))
        Files.delete(Paths.get((String) this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")));
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
            } finally {
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
                } catch (Exception ex) {
                    answerStatus = "Bitte die Eingaben überprüfen!";
                }
                req.setAttribute("answerStatus", answerStatus);
                processRequest(req, resp);
            }
        } else {
            //Checks if the administrator is logged in correctly
            Credentials cr = (Credentials) req.getSession().getAttribute("credentials");
            //The PollHandler is the communication tool for communicating with the Blockchain
            pollTester = new PollHandler(cr);

            PollData pollData = (PollData) this.getServletContext().getAttribute("poll");
            try {
                //Method to create the PollContract on the Blockchain
                String contractAdress = pollTester.createContract(pollData.getAnswerList().size(),
                        pollData.getTitle(),
                        pollData.getDate_from(),
                        pollData.getDate_due(),
                        pollData.isDiagramOption());
                try {
                    AdminHandler adminHandler = new AdminHandler(cr);
                    adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin/")));
                    adminHandler.addAdminAddress(new Address(contractAdress), new Address(cr.getAddress()));
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                this.getServletContext().setAttribute("newContractAdress", contractAdress);
                this.getServletContext().setAttribute("newTypeOfVote", VoteType.POLL);

                LinkedList<PollData> dataLinkedList = (LinkedList<PollData>) this.getServletContext().getAttribute("PollList");
                dataLinkedList.add(pollData);
                this.getServletContext().setAttribute("PollList", dataLinkedList);

            } catch (Exception e) {
                req.setAttribute("answerStatus", "Fehler beim Erstellen der Volksabstimmung");
            }
            List<PollAnswer> liAnswers = pollData.getAnswerList();
            //Foreach loop is responsible for adding all answers to the PollContract in the Blockchain
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

    /**
     * @param req
     * @param resp Because the doGet will be fired everytime we load the JSP, we had the method to check, if the person is logged in,
     *             in there. It only takes the hash from the session object and checks with the loggedUserInstance if the login is correct.
     * @throws ServletException
     * @throws IOException
     */
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
