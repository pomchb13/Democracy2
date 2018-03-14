package servlet;

import beans.*;
import handler.AdminHandler;
import logger.Logger;
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
 * Description:     This Servlet java class is responsible for creating a new PollContract and push it to the Blockchain. Before
 * the administrator is able to create a new PollContract it also checks if the administrator is logged in correctly.
 * The creation of the new PollContract could take a while because the PollHandler needs to push it to the Blockchain and
 * create all possible answers the administrator created.
 */

@WebServlet(urlPatterns = {"/NewPollSL"})
public class NewPollSL extends HttpServlet {
    //The Instance where all logged users and administrator are saved
    private LoggedUsers userInstance = LoggedUsers.getInstance();
    //The Pollhandler object is responsible for the communication with the Blockchain
    private PollHandler pollHandler;

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
        //Checks if an old userlist file exists. If yes, it will be deleted.
        if (Files.exists(Paths.get(this.getServletContext().getRealPath("/res/userLists/userlist.xlsx"))))
            Files.delete(Paths.get(this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")));

        String pollStatus = null;
        String answerStatus = null;
        //Checks if the pressed button has the "createReferendum" value
        if (req.getParameter("actionButton").equals("createReferendum")) {
            try {
                //Reads data for a poll from the field
                String title = ServletUtil.filter(req.getParameter("input_Title"));
                String date_from = ServletUtil.filter(req.getParameter("input_Start"));
                String date_due = ServletUtil.filter(req.getParameter("input_End"));
                if (!title.isEmpty() || !date_due.isEmpty() || !date_from.isEmpty()) {


                    //Checks if the administrator allowed seeing diagrams
                    boolean voteDiagrams;
                    if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
                        voteDiagrams = true;
                    else
                        voteDiagrams = false;

                    //Parsing the dates from String to LocalDate
                    LocalDate vote_fromDate = LocalDate.parse(date_from, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    LocalDate vote_dueDate = LocalDate.parse(date_due, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                    //date validation
                    if (LocalDate.now().isBefore(vote_fromDate)
                            || LocalDate.now().isEqual(vote_fromDate)
                            && LocalDate.now().isBefore(vote_dueDate)
                            && vote_fromDate.isBefore(vote_dueDate)) {
                        //Creating the poll and saving it to request scope
                        PollData newPoll = new PollData(title, vote_fromDate, vote_dueDate, voteDiagrams);
                        req.getSession().setAttribute("newPoll", newPoll);

                        pollStatus = "Abstimmung erfolgreich erstellt und zwischengespeichert";
                    }
                } else {
                    pollStatus = "Bitte überprüfen Sie Ihre Eingabe!";
                }
            } catch (Exception ex) {
                pollStatus = "Bitte überprüfen Sie Ihre Eingabe!";
            } finally {
                req.setAttribute("pollStatus", pollStatus);
                processRequest(req, resp);
            }
            //Checks if the pressed button has the "addAnswer" value
        } else if (req.getParameter("actionButton").equals("addAnswer")) {
            if (req.getSession().getAttribute("newPoll") != null) {
                try {
                    //Reads the attribute from the HTML input field to create an Answer
                    String answerTitle = ServletUtil.filter(req.getParameter("input_AnswerTitle"));
                    String answerDescription = ServletUtil.filter((req.getParameter("input_Answer")));

                    //Gets the new Poll from the request scope, adds the answer and saved it back
                    PollAnswer pAnswer = new PollAnswer(answerTitle, answerDescription);
                    PollData newPoll = (PollData) req.getSession().getAttribute("newPoll");
                    LinkedList<PollAnswer> answerList = newPoll.getAnswerList();

                    boolean addAnswer = true;
                    for (PollAnswer pollAnswer : answerList) {

                        if (pollAnswer.getTitle().contains(pAnswer.getTitle())) {
                            addAnswer = false;
                        }
                    }
                    if (newPoll != null) {
                        if (addAnswer) {
                            answerList.add(pAnswer);
                            newPoll.setAnswerList(answerList);
                            req.getSession().setAttribute("newPoll", newPoll);
                            answerStatus = "Antwort erfolgreich hinzugefügt!";
                        } else {
                            answerStatus = "Es können nicht 2 Antworten mit den selben Titeln erstell werden";
                        }
                    } else {
                        answerStatus = "Bitte zuerste eine Wahl erstellen!";
                    }

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
            pollHandler = new PollHandler(cr);
            //Creates the adminHandler-object
            AdminHandler adminHandler = new AdminHandler(cr);

            PollData newPoll = (PollData) req.getSession().getAttribute("newPoll");
            if (newPoll != null) {


                try {
                    //Method to create the PollContract on the Blockchain
                    String contractAdress = pollHandler.createContract(newPoll.getAnswerList().size(),
                            newPoll.getTitle(),
                            newPoll.getDate_from(),
                            newPoll.getDate_due(),
                            newPoll.isDiagramOption());

                    //Load Admincontract in the adminHandler
                    adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin/")));

                    //Save PollContract to Admincontract
                    adminHandler.addContractAddress(new Address(contractAdress), new Address(cr.getAddress()));

                    //save the new ContractAddress and the type to session scope
                    req.getSession().setAttribute("newContractAdress", contractAdress);
                    req.getSession().setAttribute("newTypeOfVote", VoteType.POLL);

                } catch (Exception e) {
                    Logger.logError("Error while creating a new Poll on Blockchain: " + e.toString(), NewPollSL.class);
                    req.setAttribute("answerStatus", "Fehler beim Erstellen der Volksabstimmung");
                }
                List<PollAnswer> answerList = newPoll.getAnswerList();

                //Foreach loop is responsible for adding all answers to the PollContract in the Blockchain
                for (int i = 0; i < answerList.size(); i++) {
                    try {
                        pollHandler.storeAnswerData(i, answerList.get(i).getTitle(), answerList.get(i).getDescription());
                    } catch (Exception e) {
                        Logger.logError("Error while adding a pollAnswer to the poll: " + e.toString(), NewPollSL.class);
                        req.setAttribute("answerStatus", "Fehler beim Hinzufügen der Antworten");
                    }
                }

                //get Polllist from session scope, add the new Poll and save it back to session scope
                LinkedList<PollData> pollList = (LinkedList<PollData>) req.getSession().getAttribute("pollList");
                pollList.add(newPoll);
                req.getSession().setAttribute("pollList", pollList);

                Logger.logInformation("The Poll " + newPoll.getTitle() + " was saved successfully on the Blockchain", NewPollSL.class);

                //Forward to the Userkey Generator
                resp.sendRedirect("/UploadUserFileSL");
            } else {
                answerStatus = "Bitte die Eingaben überprüfen!";
                req.setAttribute("answerStatus", answerStatus);
                processRequest(req, resp);
            }

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
        if (!userInstance.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }

    }
}
