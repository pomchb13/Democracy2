package servlet;

import beans.*;
import handler.AdminHandler;
import handler.ElectionHandler;
import logger.Logger;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import user.LoggedUsers;
import util.AdminReader;
import util.BlockchainUtil;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:          Leonhard Gangl
 * Description:     This Servlet java class is responsible for creating a new PollContract and push it to the Blockchain.
 * Before the administrator is able to create a new PollContract it also checks if the administrator is logged
 * in correctly. The creation of the new PollContract could take a while because the PollHandler needs to push
 * it to the Blockchain and create all possible answers the administrator created.
 */

@WebServlet(urlPatterns = {"/NewElectionSL"})
public class NewElectionSL extends HttpServlet {

    //The Instance where all logged users and administrator are saved
    private LoggedUsers userInstance = LoggedUsers.getInstance();
    //The ElectionHandler object is responsible for the communication with the Blockchain
    private ElectionHandler electionHandler;

    /**
     *
     * @param config
     * initialization block
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    /**
     *
     * @param request
     * @param response
     *
     * Gets RequestDispatch-object from request scope and then forwards to the "NewElectionUI.jsp"
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/NewElectionUI.jsp");
        rd.forward(request, response);
    }

    /**
     *
     * @param req
     * @param resp
     *
     * Checks if the account is logged into the webplatform and is allowed to see this site
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

    /**
     *
     * @param req
     * @param resp
     *
     * Responsible for doing the whole election creation process. The first button only creates the election itself.
     * The second adds a candidate to the election, which was created before. The last button only pushes the election
     * and its candidates to the user and then forwards to the JSP-file where the Userkeys are going to be generated
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Checks if contract.txt exists
        if (Files.exists(Paths.get(this.getServletContext().getRealPath("/res/userLists/userlist.xlsx"))))
            Files.delete(Paths.get(this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")));

        //check if value of the clicked button is "createVote"
        if (req.getParameter("actionButton").equals("createVote")) {
            try {
                //read title, start and duedate from the input fields
                String voteTitle = ServletUtil.filter(req.getParameter("input_Title"));
                String fromDate = ServletUtil.filter((req.getParameter("input_Start")));
                String dueDate = ServletUtil.filter(req.getParameter("input_End"));

                //check if showDiagram is true
                boolean voteDiagrams;
                if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
                    voteDiagrams = true;
                else
                    voteDiagrams = false;

                //parse the dates to LocalDate
                LocalDate vote_fromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate vote_dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                //date validation
                if (LocalDate.now().isBefore(vote_fromDate)
                        || LocalDate.now().isEqual(vote_fromDate)
                        && LocalDate.now().isBefore(vote_dueDate)
                        && vote_fromDate.isBefore(vote_dueDate)) {

                    //create new Election-object
                    ElectionData newElection = new ElectionData(voteTitle, vote_fromDate, vote_dueDate, voteDiagrams);

                    //set the new election on request scope
                    req.getSession().setAttribute("newElection", newElection);

                    req.setAttribute("statusVote", "Wahl erfolreich erstellt und zwischengespeichert!");
                } else {
                    req.setAttribute("statusVote", "Wahl nicht erstellt! Bitte Datum überprüfen!");
                }
            } catch (Exception ex) {
                req.setAttribute("statusVote", "Wahl nicht erstellt! Bitte Datumsformat überprüfen \"MM/DD/YYYY\"!");
            } finally {
                processRequest(req, resp);
            }
            //check if value of the button is "addPolitican"
        } else if (req.getParameter("actionButton").equals("addPolitician")) {
            try {
                //check if there is a newElection saved in request scope
                if (req.getSession().getAttribute("newElection") != null) {
                    //read data of the candidate from the fields
                    String candTitle = ServletUtil.filter(req.getParameter("input_cand_Title"));
                    String candFirstname = ServletUtil.filter(req.getParameter("input_cand_Firstname"));
                    String candLastname = ServletUtil.filter((req.getParameter("input_cand_Lastname")));
                    LocalDate dateOfBirth = LocalDate.parse(ServletUtil.filter(req.getParameter("input_cand_Birthday")), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    String party = ServletUtil.filter(req.getParameter("input_cand_Party"));
                    String slogan = ServletUtil.filter(req.getParameter("input_cand_Slogan"));
                    String portraitPath = this.getServletContext().getRealPath("/res/") + "images"
                            + File.separator
                            + ServletUtil.filter(req.getParameter("input_cand_Picture"));

                    //set default photo is photo if no photo is selected from the dropdown
                    if (portraitPath == null) {
                        portraitPath = this.getServletContext().getRealPath("/res/images/user.png");
                    }

                    // check if the candidate is between 18 and 99 years
                    if (dateOfBirth.isBefore(LocalDate.now().minusYears(18)) && dateOfBirth.isAfter(LocalDate.now().minusYears(99))) {

                        //create new candidate
                        CandidateData candidate = new CandidateData(candTitle, candFirstname, candLastname, dateOfBirth, party, slogan, portraitPath);

                        //get new election from request scope
                        ElectionData newElection = (ElectionData) req.getSession().getAttribute("newElection");

                        LinkedList<CandidateData> candidateList = newElection.getLiCandidates();

                        //add candidate to list
                        candidateList.add(candidate);
                        newElection.setLiCandidates(candidateList);

                        req.setAttribute("statusCand", "Kandidat erfolgreich erstellt!");
                        req.getSession().setAttribute("newElection", newElection);
                    } else {
                        req.setAttribute("statusCand", "Bitte überprüfen Sie ihre Eingaben. Das Geburtsdatum muss zwischen heute und heute vor 100 Jahren liegen!");
                    }
                }
            } catch (Exception e) {
                Logger.logError("Error while creating Candidate: "+e.toString(), NewElectionSL.class);
                req.setAttribute("statusCand", "Bitte überprüfen Sie ihre Eingaben!");
            } finally {
                processRequest(req, resp);
            }
        } else {
            try {

                //get credentials from session scope
                Credentials cr = (Credentials) req.getSession().getAttribute("credentials");

                //create new ElectionHandler
                electionHandler = new ElectionHandler(cr);

                //get newElection from request scope
                ElectionData newElection = (ElectionData) req.getSession().getAttribute("newElection");

                //create ContractAddress for the new election and save the new election in Blockchain
                String newContractAdress = electionHandler.createContract(newElection.getLiCandidates().size(), newElection.getTitle(), newElection.getDate_from(),
                        newElection.getDate_due(), newElection.isShow_diagrams());

                //Create adminHandler-object
                AdminHandler adminHandler = new AdminHandler(cr);

                //Load all AdminContract
                adminHandler.loadSmartContract(AdminReader.getAdminContractAddress(this.getServletContext().getRealPath("/res/admin/")));

                //Add the new ContractAddress to the adminContract
                adminHandler.addContractAddress(new Address(newContractAdress), new Address(cr.getAddress()));

                List<CandidateData> candidateList = newElection.getLiCandidates();
                for (int i = 0; i < candidateList.size(); i++) {
                    //add candidate to blockchain
                    electionHandler.storeCandidateData(i, candidateList.get(i).getTitle(), candidateList.get(i).getForename(), candidateList.get(i).getSurname(),
                            candidateList.get(i).getBirthday(), candidateList.get(i).getParty(), candidateList.get(i).getSlogan(), candidateList.get(i).getPortraitPath());
                }
                Logger.logInformation("The Election "+newElection.getTitle()+" was successfully saved on the Blockchain", NewElectionSL.class);

                //After everything ran successfully the new election will be set to session scope
                LinkedList<ElectionData> electionList = (LinkedList<ElectionData>) req.getSession().getAttribute("electionList");
                electionList.add(newElection);
                req.getSession().setAttribute("electionList", electionList);

                //set ContractAdress and TypeofVote
                req.getSession().setAttribute("newContractAdress", newContractAdress);
                req.getSession().setAttribute("newTypeOfVote", VoteType.ELECTION);

                //forward to UploadUserFileSL
                resp.sendRedirect("/UploadUserFileSL");
            } catch (Exception e) {
                Logger.logInformation("Error while saving election and candidates in Blockchain: "+e.toString(), NewElectionSL.class);
                req.setAttribute("errorComplete", "Fehler beim Speichern der kompletten Wahl auf der Blockchain");
            }
        }

    }
}