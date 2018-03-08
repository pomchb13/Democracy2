package servlet;

import beans.CandidateData;
import beans.ElectionData;
import beans.RightEnum;
import handler.AdminHandler;
import handler.ElectionHandler;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import beans.VoteType;
import user.LoggedUsers;
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
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:          Leonhard Gangl
 * Created on:
 * Description:     This Servlet java class is responsible for creating a new Poll and push it to the Blockchain.
 *                  Before the administrator is able to create a new Poll it also checks if the administrator is logged
 *                  in correctly. The creation of the new Poll could take a while because the PollHandler needs to push
 *                  it to the Blockchain and create all possible answers the administrator created.
 */

@WebServlet(urlPatterns = {"/NewElectionSL"})
public class NewElectionSL extends HttpServlet {
    //The Instance where all logged users and administrator are saved
    private LoggedUsers lU = LoggedUsers.getInstance();
    private ElectionHandler election;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BlockchainUtil.setPATH(this.getServletContext().getRealPath("/res/geth_data/keystore"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // forward to NewElectionUI
        RequestDispatcher rd = request.getRequestDispatcher("/NewElectionUI.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if user has right to be on this page
        HttpSession session = req.getSession();
        String hash = (String) session.getAttribute("hash");
        if (!lU.compareRights(hash, RightEnum.ADMIN)) {
            resp.sendRedirect("/LoginSL");
        } else {
            processRequest(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Files.delete(Paths.get((String) this.getServletContext().getRealPath("/res/userLists/userlist.xlsx")));
        System.out.println(req.getParameter("actionButton"));
        // check if value of the clicked button is createVote
        if (req.getParameter("actionButton").equals("createVote")) {
            try {
                // get titel, start and end date
                String voteTitle = ServletUtil.filter(req.getParameter("input_Title"));
                String fromDate = ServletUtil.filter((req.getParameter("input_Start")));
                String dueDate = ServletUtil.filter(req.getParameter("input_End"));

                // check if showDiagram is true
                boolean voteDiagrams;
                if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
                    voteDiagrams = true;
                else
                    voteDiagrams = false;

                // pars date
                LocalDate vote_fromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate vote_dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                // date valuation
                if (LocalDate.now().isBefore(vote_fromDate)
                        || LocalDate.now().isEqual(vote_fromDate)
                        && LocalDate.now().isBefore(vote_dueDate)
                        && vote_fromDate.isBefore(vote_dueDate)) {

                    // create ne Election
                    ElectionData newElectionData = new ElectionData(voteTitle, vote_fromDate, vote_dueDate, voteDiagrams);
                    System.out.println(newElectionData.toString());

                    // set election on request scope
                    this.getServletContext().setAttribute("newElection", newElectionData);
                    req.setAttribute("errorVote", "Wahl erfolreich erstellt und zwischengespeichert!");
                } else {
                    req.setAttribute("errorVote", "Wahl nicht erstellt! Bitte Datum überprüfen!");
                }
            } catch (Exception ex) {
                req.setAttribute("errorVote", "Wahl nicht erstellt! Bitte Datumsformat überprüfen \"MM/DD/YYYY\"!");
            } finally {
                processRequest(req, resp);
            }
            // check if value of the button is addPolitican
        } else if (req.getParameter("actionButton").equals("addPolitician")) {
            System.out.println("Adding Politician");
            try {
                // check if there is an newElection set
                if (this.getServletContext().getAttribute("newElection") != null) {

                    System.out.println("Adding CandidateData");

                    // get data of candidate
                    String candTitle = ServletUtil.filter(req.getParameter("input_cand_Title"));
                    String candFirstname = ServletUtil.filter(req.getParameter("input_cand_Firstname"));
                    String candLastname = ServletUtil.filter((req.getParameter("input_cand_Lastname")));
                    LocalDate dateOfBirth = LocalDate.parse(ServletUtil.filter(req.getParameter("input_cand_Birthday")), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                    // check if birthday is between today and today minis 99 years
                    if(dateOfBirth.isBefore(LocalDate.now()) && dateOfBirth.isAfter(LocalDate.now().minusYears(99))) {
                        String party = ServletUtil.filter(req.getParameter("input_cand_Party"));
                        String slogan = ServletUtil.filter(req.getParameter("input_cand_Slogan"));
                        String portraitPath = this.getServletContext().getRealPath("/") + "images"
                                + File.separator
                                + ServletUtil.filter(req.getParameter("input_cand_Picture"));

                        // set default photo is photo is not set
                        if (portraitPath == null) {
                            portraitPath = this.getServletContext().getRealPath("/images/user.png");
                        }

                        // create new candidate
                        CandidateData pot = new CandidateData(candTitle, candFirstname, candLastname, dateOfBirth, party, slogan, portraitPath);
                        System.out.println(pot.toString());

                        // get election
                        ElectionData newElectionData = (ElectionData) this.getServletContext().getAttribute("newElection");
                        LinkedList<CandidateData> liPolit = newElectionData.getLiCandidates();
                        System.out.println(liPolit.toString());

                        // add candidate to list
                        liPolit.add(pot);
                        newElectionData.setLiCandidates(liPolit);

                        System.out.println(newElectionData.toString());
                        req.setAttribute("errorPol", "Kandidat erfolgreich erstellt!");
                        this.getServletContext().setAttribute("newElection", newElectionData);
                    }else{
                        req.setAttribute("errorPol", "Bitte überprüfen Sie ihre Eingaben. Das Geburtsdatum muss zwischen heute und heute vor 100 Jahren liegen!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute("errorPol", "Bitte überprüfen Sie ihre Eingaben!");
            } finally {
                processRequest(req, resp);
            }
        } else {
            System.out.println("Hallo");
            try {
                System.out.println("Create Vote and Forward to Excel Upload");

                // get credentials from session scope
                Credentials cr = (Credentials) req.getSession().getAttribute("credentials");

                // create new ElectionHandler
                election = new ElectionHandler(cr);

                // get election
                ElectionData electionData = (ElectionData) this.getServletContext().getAttribute("newElection");
                System.out.println("Before election");

                // add election to blockchain
                String newContractAdress = election.createContract(electionData.getLiCandidates().size(), electionData.getTitle(), electionData.getDate_from(),
                        electionData.getDate_due(), electionData.isShow_diagrams());
                AdminHandler adminHandler = new AdminHandler(cr);
                adminHandler.addContractAddress(new Address(newContractAdress),new Address(cr.getAddress()));

                System.out.println("Election saved in Blockchain");
                List<CandidateData> liPolit = electionData.getLiCandidates();
                for (int i = 0; i < liPolit.size(); i++) {

                    // add candidates to blockchain
                    election.storeCandidateData(i, liPolit.get(i).getTitle(), liPolit.get(i).getForename(), liPolit.get(i).getSurname(),
                            liPolit.get(i).getBirthday(), liPolit.get(i).getParty(), liPolit.get(i).getSlogan());
                }
                System.out.println("Candidate saved to Blockchainelection");

                // set ContractAdress and TypeofVote
                this.getServletContext().setAttribute("newContractAdress", newContractAdress);
                this.getServletContext().setAttribute("newTypeOfVote", VoteType.ELECTION);

                // forward to UploadUserFileSL
                resp.sendRedirect("/UploadUserFileSL");
            } catch (Exception e) {
                req.setAttribute("errorComplete", "Fehler beim Speichern der kompletten Wahl auf der Blockchain");
            }
        }

    }
}