package servlet;

import beans.CandidateData;
import beans.ElectionData;
import beans.RightEnum;
import handler.ElectionHandler;
import org.web3j.crypto.Credentials;
import test.VoteType;
import user.LoggedUsers;
import util.BlockchainUtil;
import util.ServletUtil;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leonhard on 28.11.2017.
 */
@WebServlet(urlPatterns = {"/NewElectionSL"})
public class NewElectionSL extends HttpServlet {
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
        RequestDispatcher rd = request.getRequestDispatcher("/NewElectionUI.jsp");
        rd.forward(request, response);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("actionButton"));

        if (req.getParameter("actionButton").equals("createVote")) {
            try {
                String voteTitle = ServletUtil.filter(req.getParameter("input_Title"));
                String fromDate = ServletUtil.filter((req.getParameter("input_Start")));
                String dueDate = ServletUtil.filter(req.getParameter("input_End"));

                boolean voteDiagrams;
                if (ServletUtil.filter(req.getParameter("input_DiaOption")).equals("1"))
                    voteDiagrams = true;
                else
                    voteDiagrams = false;


                LocalDate vote_fromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate vote_dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                if (LocalDate.now().isBefore(vote_fromDate)
                        || LocalDate.now().isEqual(vote_fromDate)
                        && LocalDate.now().isBefore(vote_dueDate)
                        && vote_fromDate.isBefore(vote_dueDate)) {
                    ElectionData newElectionData = new ElectionData(voteTitle, vote_fromDate, vote_dueDate, voteDiagrams);
                    //Testing Statement --> Delete After not needed
                    System.out.println(newElectionData.toString());
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
        } else if (req.getParameter("actionButton").equals("addPolitician")) {
            System.out.println("Adding Politician");
            try {
                if (this.getServletContext().getAttribute("newElection") != null) {

                    System.out.println("Adding CandidateData");
                    String candTitle = ServletUtil.filter(req.getParameter("input_cand_Title"));
                    String candFirstname = ServletUtil.filter(req.getParameter("input_cand_Firstname"));
                    String candLastname = ServletUtil.filter((req.getParameter("input_cand_Lastname")));
                    LocalDate dateOfBirth = LocalDate.parse(ServletUtil.filter(req.getParameter("input_cand_Birthday")), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    String party = ServletUtil.filter(req.getParameter("input_cand_Party"));
                    String slogan = ServletUtil.filter(req.getParameter("input_cand_Slogan"));
                    String portraitPath = this.getServletContext().getRealPath("/") + "images"
                            + File.separator
                            + ServletUtil.filter(req.getParameter("input_cand_Picture"));
                    if (portraitPath == null) {
                        portraitPath = this.getServletContext().getRealPath("/images/user.png");
                    }
                    CandidateData pot = new CandidateData(candTitle, candFirstname, candLastname, dateOfBirth, party, slogan, portraitPath);
                    System.out.println(pot.toString());

                    ElectionData newElectionData = (ElectionData) this.getServletContext().getAttribute("newElection");
                    LinkedList<CandidateData> liPolit = newElectionData.getLiCandidates();
                    System.out.println(liPolit.toString());
                    liPolit.add(pot);
                    newElectionData.setLiCandidates(liPolit);

                    System.out.println(newElectionData.toString());
                    req.setAttribute("errorPol", "Kandidat erfolgreich erstellt!");
                    this.getServletContext().setAttribute("newElection", newElectionData);
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
                Credentials cr = (Credentials) req.getSession().getAttribute("credentials");
                election = new ElectionHandler(cr);
                ElectionData electionData = (ElectionData) this.getServletContext().getAttribute("newElection");
                System.out.println("Before election");
                String newContractAdress = election.createContract(electionData.getLiCandidates().size(), electionData.getTitle(), electionData.getDate_from(),
                        electionData.getDate_due(), electionData.isShow_diagrams());

                System.out.println("Election saved in Blockchain");
                List<CandidateData> liPolit = electionData.getLiCandidates();
                for (int i = 0; i < liPolit.size(); i++) {
                    election.storeCandidateData(i, liPolit.get(i).getTitle(), liPolit.get(i).getForename(), liPolit.get(i).getSurname(),
                            liPolit.get(i).getBirthday(), liPolit.get(i).getParty(), liPolit.get(i).getSlogan());
                }
                System.out.println("Candidate saved to Blockchainelection");

                this.getServletContext().setAttribute("newContractAdress", newContractAdress);
                this.getServletContext().setAttribute("newTypeOfVote", VoteType.ELECTION);

                resp.sendRedirect("/UploadUserFileSL");
            } catch (Exception e) {
                req.setAttribute("errorComplete", "Fehler beim Speichern der kompletten Wahl auf der Blockchain");
            }
        }

    }
}